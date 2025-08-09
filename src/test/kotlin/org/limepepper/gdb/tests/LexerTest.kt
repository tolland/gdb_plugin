package org.limepepper.gdb.tests

import com.intellij.lexer.FlexLexer
import com.intellij.psi.tree.IElementType
import com.intellij.psi.TokenType
import org.junit.Before
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.parser.GdbTokenTypes
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
     * Helper method to assert token at a specific index
     */
    private fun assertToken(tokens: List<TokenInfo>, index: Int, expectedType: IElementType, expectedText: String) {
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
    }

    @Test
    fun testLineContinuations() {
        val content = """
        # This is a comment at the start of the file

        # comment can have line \
        continuations in them

        # command aregs can be split over lines
        set var \
        ${'$'}myvar2 \
        = \
        7

        # command aregs can be split over lines
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

        println("=== Generated Lexer (_GdbLexer) ===")
        printTokens(content)
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
        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE && it.type != GdbTypes.CRLF }

        assertTrue(nonWhitespaceTokens.any { it.text == "define" && it.type == GdbTypes.DEFINE }, "Should contain DEFINE 'define'")
        assertTrue(nonWhitespaceTokens.any { it.text == "mycommand" && it.type == GdbTypes.IDENTIFIER }, "Should contain identifier 'mycommand'")
        assertTrue(nonWhitespaceTokens.any { it.text == "end" && it.type == GdbTypes.END }, "Should contain END 'end'")
    }

}
