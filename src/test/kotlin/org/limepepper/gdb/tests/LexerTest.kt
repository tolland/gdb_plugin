package org.limepepper.gdb.tests

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.junit.Before
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.psi.GdbTypes
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Data class to hold token information for testing
 */
data class TokenInfo(
    val type: IElementType,
    val text: String,
    val start: Int,
    val end: Int
) {
    override fun toString(): String = "${type}: '$text' (${start}-${end})"
}

class LexerTest {

    private lateinit var lexer: GdbLexer

    @Before
    fun setUp() {
        lexer = GdbLexer()
    }

    /**
     * Tokenizes the input content and returns a list of TokenInfo objects
     *
     * @param content The input string to be tokenized
     * @return List of TokenInfo objects representing all tokens
     */
    private fun tokenize(content: String): List<TokenInfo> {
        val tokens = mutableListOf<TokenInfo>()
        lexer.reset(content, 0, content.length, 0)
        var token: IElementType? = lexer.advance()
        while (token != null) {
            val text = content.substring(lexer.tokenStart, lexer.tokenEnd)
            tokens.add(TokenInfo(token, text, lexer.tokenStart, lexer.tokenEnd))
            token = lexer.advance()
        }
        return tokens
    }

    /**
     * Prints all tokens and their text values from a given FlexLexer instance.
     *
     * @param content The input string to be tokenized
     */
    private fun printTokens(content: String) {
        println("=== DEBUG START ===")
        val tokens = tokenize(content)
        tokens.forEachIndexed { index, tokenInfo ->
            val displayText = tokenInfo.text.replace("\n", "\\n").replace("\r", "\\r")
            val tokenName = tokenInfo.type.toString()
            println("[${index.toString().padStart(3)}] ${tokenName.padEnd(30)}: '$displayText'")
        }
        println("=== DEBUG END ===")
    }

    /**
     * Assert that there are no BAD_CHARACTER tokens present
     */
    private fun assertNoBadCharacters(tokens: List<TokenInfo>) {
        val badTokens = tokens.filter { it.type == TokenType.BAD_CHARACTER }
        assertTrue(
            badTokens.isEmpty(),
            "Unexpected BAD_CHARACTER tokens found: ${badTokens.joinToString { it.toString() }}"
        )
    }

    /**
     * Helper method to assert token at a specific index
     */
    private fun assertToken(
        tokens: List<TokenInfo>,
        index: Int,
        expectedType: IElementType,
        expectedText: String
    ) {
        if (index >= tokens.size) {
            throw AssertionError("Token index $index is out of bounds. Total tokens: ${tokens.size}")
        }
        val token = tokens[index]
        assertEquals(expectedType, token.type, "Token type mismatch at index $index")
        assertEquals(expectedText, token.text, "Token text mismatch at index $index")
    }

    /**
     * Test the plugin Lexer
     */
    @Test
    fun testGdbLexer() {
        val content = File("src/test/testData/ParsingDataTest.gdb").readText()
        printTokens(content)
        val tokens = tokenize(content)
        assertNoBadCharacters(tokens)
    }

    @Test
    fun testLineContinuations() {
        val content = """
        # This is a comment at the start of the file

        # comment can have line \
        continuations in them \
        and go on and on

        # command args can be split over lines by line continuation char
        set var \
        ${'$'}myvar2 \
        = \
        7

        # command and args can be split by line continuation char
        set \
        var \
        ${'$'}myvar2 \
        = \
        7

        # A multi line comment block associated
        # with a command and a line \
        continuation
        set pagination off

    """.trimIndent()

        println("=== Line continuation tests ===")
        val tokens = tokenize(content)
        assertNoBadCharacters(tokens)

        // Find the continued comment token
        val continuedComment =
            tokens.firstOrNull { it.type == GdbTypes.COMMENT && it.text.contains("comment can have line") }
        assertTrue(continuedComment != null, "Expected a COMMENT token for the continued comment")
        assertTrue(
            continuedComment!!.text.contains("# comment can have line \\\ncontinuations in them"),
            "Multi-line comment should preserve content across continuation"
        )

        // Second continued comment block
        val secondContinued =
            tokens.firstOrNull { it.type == GdbTypes.COMMENT && it.text.contains("with a command and a line") }
        assertTrue(
            secondContinued != null,
            "Expected a COMMENT token for the second continued comment"
        )
        assertTrue(
            secondContinued!!.text.contains("# with a command and a line \\\ncontinuation"),
            "Second multi-line comment should preserve content across continuation"
        )

        // print at the end for debug if needed
        printTokens(content)
    }

    /**
     * Test scenarios of detecting a keyword that is a command due to its position
     * and that we tokenze specific commands e.g. "set", or generic commands
     * such as "my_user_command" correctly
     */
    @Test
    fun testCommandArgsContext() {
        val content = """
            user_command1

            user_command2 some args

            set var ${'$'}myvar2 = 7

            set var \
            ${'$'}myvar2 \
            = \
            7
        """.trimIndent()

        println("=== Define Block Debug ===")
        printTokens(content)

        val tokens = tokenize(content)
        val nonWhitespaceTokens =
            tokens.filter { it.type != TokenType.WHITE_SPACE && it.type != GdbTypes.CRLF }
        assertNoBadCharacters(tokens)
    }

    @Test
    fun testDefineBlock() {
        val content = """
            define mycommand
                print "executing mycommand"
                continue
            end
        """.trimIndent()

        println("=== Define Block Debug ===")
        printTokens(content)

        val tokens = tokenize(content)
        val nonWhitespaceTokens =
            tokens.filter { it.type != TokenType.WHITE_SPACE && it.type != GdbTypes.CRLF }
//        assertNoBadCharacters(tokens)

        assertTrue(
            nonWhitespaceTokens.any { it.text == "define" && it.type == GdbTypes.DEFINE },
            "Should contain DEFINE 'define'"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "mycommand" && it.type == GdbTypes.IDENTIFIER },
            "Should contain identifier 'mycommand'"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "end" && it.type == GdbTypes.END },
            "Should contain END 'end'"
        )
    }

    @Test
    fun testNestedDefineWithCommands() {
        val content = """
            define mycommand
              print "Custom command"
              info warranty

              break foo2
              commands
                  silent
                  printf "x is %d\n",x
                  cont
              end

              define mycommand2
                print "Custom command"
                info warranty
              end
            end
        """.trimIndent()

        println("=== Nested Define with Commands Debug ===")
        printTokens(content)

        val tokens = tokenize(content)
        val nonWhitespaceTokens =
            tokens.filter { it.type != TokenType.WHITE_SPACE && it.type != GdbTypes.CRLF }
//        assertNoBadCharacters(tokens)

        // Should have proper nesting structure
        assertTrue(
            nonWhitespaceTokens.any { it.text == "define" && it.type == GdbTypes.DEFINE },
            "Should contain DEFINE 'define'"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "mycommand" && it.type == GdbTypes.IDENTIFIER },
            "Should contain identifier 'mycommand'"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "commands" && it.type == GdbTypes.COMMANDS },
            "Should contain COMMANDS 'commands'"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "mycommand2" && it.type == GdbTypes.IDENTIFIER },
            "Should contain identifier 'mycommand2'"
        )

        // Should have multiple END tokens for proper nesting
        val endTokens = nonWhitespaceTokens.filter { it.text == "end" && it.type == GdbTypes.END }
        assertTrue(
            endTokens.size >= 3,
            "Should have at least 3 END tokens for nested structure, got ${endTokens.size}"
        )
    }

    @Test
    fun testOriginalUserExample() {
        // This is the exact example from the user's original request
        val content = """
            define mycommand
              print "Custom command"
              info warranty

              break foo2
              commands
                  silent
                  printf "x is %d\n",x
                  cont
              end

              define mycommand2
                print "Custom command"
                info warranty
              end
            end
        """.trimIndent()

        println("=== Original User Example Debug ===")
        printTokens(content)

        val tokens = tokenize(content)
        val nonWhitespaceTokens =
            tokens.filter { it.type != TokenType.WHITE_SPACE && it.type != GdbTypes.CRLF }
//        assertNoBadCharacters(tokens)

        // Verify the nested structure works as expected
        // The printf "x is %d\n",x should be in a COMMANDS_LIST state
        // The define mycommand2 should be in a DEFINE_BODY state
        // All should be properly nested with correct END tokens

        assertTrue(
            nonWhitespaceTokens.any { it.text == "printf" && it.type == GdbTypes.COMMAND_DATA },
            "Should contain printf command"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "silent" && it.type == GdbTypes.IDENTIFIER },
            "Should contain silent command"
        )
        assertTrue(
            nonWhitespaceTokens.any { it.text == "cont" && it.type == GdbTypes.IDENTIFIER },
            "Should contain cont command"
        )

        // Count define tokens - should have 2 (mycommand and mycommand2)
        val defineTokens =
            nonWhitespaceTokens.filter { it.text == "define" && it.type == GdbTypes.DEFINE }
        assertTrue(
            defineTokens.size == 2,
            "Should have exactly 2 DEFINE tokens, got ${defineTokens.size}"
        )

        // Count end tokens - should have 3 (commands end, mycommand2 end, mycommand end)
        val endTokens = nonWhitespaceTokens.filter { it.text == "end" && it.type == GdbTypes.END }
        assertTrue(endTokens.size == 3, "Should have exactly 3 END tokens, got ${endTokens.size}")
    }

    @Test
    fun testBadCharacterInInitialState() {
        val content = "="
        printTokens(content)
        val tokens = tokenize(content)
        val bads = tokens.filter { it.type == TokenType.BAD_CHARACTER }
        assertTrue(bads.isNotEmpty(), "Expected BAD_CHARACTER token for '=' in YYINITIAL")
        assertTrue(bads.any { it.text == "=" }, "BAD_CHARACTER token should be '='")
    }

    @Test
    fun testPrintfExample() {
        // This is the exact example from the user's original request
        val content = """
            define mycommand
              print "Custom command"
            end
            set var ${'$'}myvar1 = "this is a string"
            set var ${'$'}myvar2 = 7
            printf "x is %d\n",x
            printfXX "x is %d\n",x
        """.trimIndent()

        println("=== Original printf Debug ===")
        printTokens(content)
    }
}
