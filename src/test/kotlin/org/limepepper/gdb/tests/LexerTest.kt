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
    fun testBasicCommandTokens() {
        val content = "set pagination off"
        val tokens = tokenize(content)

        assertEquals(5, tokens.size, "Expected 5 tokens (including whitespace)")
        assertToken(tokens, 0, GdbTypes.SET_KW, "set")
        assertToken(tokens, 1, TokenType.WHITE_SPACE, " ")
        assertToken(tokens, 2, GdbTypes.IDENTIFIER, "pagination")
        assertToken(tokens, 3, TokenType.WHITE_SPACE, " ")
        assertToken(tokens, 4, GdbTypes.IDENTIFIER, "off")
    }

    @Test
    fun testCommentTokens() {
        val content = "# This is a comment\nset var x = 5"
        val tokens = tokenize(content)

        assertEquals(11, tokens.size, "Expected 11 tokens (including whitespace)")
        assertToken(tokens, 0, GdbTypes.COMMENT, "# This is a comment")
        assertToken(tokens, 1, GdbTypes.CRLF, "\n")
        assertToken(tokens, 2, GdbTypes.SET_KW, "set")
        assertToken(tokens, 3, TokenType.WHITE_SPACE, " ")
        assertToken(tokens, 4, GdbTypes.IDENTIFIER, "var")
        assertToken(tokens, 5, TokenType.WHITE_SPACE, " ")
        assertToken(tokens, 6, GdbTypes.IDENTIFIER, "x")
        assertToken(tokens, 7, TokenType.WHITE_SPACE, " ")
        assertToken(tokens, 8, GdbTypes.OP_ASSIGN, "=")
        assertToken(tokens, 9, TokenType.WHITE_SPACE, " ")
        assertToken(tokens, 10, GdbTypes.NUMBER, "5")
    }

    @Test
    fun testBreakpointCommand() {
        val content = "break main.c:42"
        val tokens = tokenize(content)

        println("=== DEBUG: Breakpoint Command ===")
        printTokens(content)

        // Let's see what we actually get first
        assertTrue(tokens.isNotEmpty(), "Should have at least one token")
    }

    @Test
    fun testNumberTokens() {
        val content = "print 42"
        val tokens = tokenize(content)

        println("=== DEBUG: Number Tokens ===")
        printTokens(content)

        // Filter out whitespace tokens for easier testing
        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE }

        assertEquals(2, nonWhitespaceTokens.size, "Expected 2 non-whitespace tokens")
        assertToken(nonWhitespaceTokens, 0, GdbTypes.PRINT_KW, "print")
        assertToken(nonWhitespaceTokens, 1, GdbTypes.NUMBER, "42")
    }

    @Test
    fun testRegisterTokens() {
        val content = "print \$eax \$rsp"
        val tokens = tokenize(content)

        println("=== DEBUG: Register tokens ===")
        printTokens(content)

        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE }

        assertEquals(3, nonWhitespaceTokens.size, "Expected 3 non-whitespace tokens")
        assertToken(nonWhitespaceTokens, 0, GdbTypes.PRINT_KW, "print")
        assertToken(nonWhitespaceTokens, 1, GdbTypes.REGISTER, "\$eax")
        assertToken(nonWhitespaceTokens, 2, GdbTypes.REGISTER, "\$rsp")
    }

    @Test
    fun testOperatorTokens() {
        val content = "if x == 5 && y >= 10"
        val tokens = tokenize(content)

        println("=== DEBUG: Operator Tokens ===")
        printTokens(content)

        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE }

        assertEquals(8, nonWhitespaceTokens.size, "Expected 8 non-whitespace tokens")
        assertToken(nonWhitespaceTokens, 0, GdbTypes.IDENTIFIER, "if")
        assertToken(nonWhitespaceTokens, 1, GdbTypes.IDENTIFIER, "x")
        assertToken(nonWhitespaceTokens, 2, GdbTypes.OP_EQUAL, "==")
        assertToken(nonWhitespaceTokens, 3, GdbTypes.NUMBER, "5")
        assertToken(nonWhitespaceTokens, 4, GdbTypes.OP_AND_AND, "&&")
        assertToken(nonWhitespaceTokens, 5, GdbTypes.IDENTIFIER, "y")
        assertToken(nonWhitespaceTokens, 6, GdbTypes.OP_GREATER_OR_EQUAL, ">=")
        assertToken(nonWhitespaceTokens, 7, GdbTypes.NUMBER, "10")
    }

    @Test
    fun testStringTokens() {
        val content = """print "Hello World" 'single quoted'"""
        val tokens = tokenize(content)

        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE }

        assertEquals(4, nonWhitespaceTokens.size, "Expected 4 non-whitespace tokens")
        assertToken(nonWhitespaceTokens, 0, GdbTypes.PRINT_KW, "print")
        assertToken(nonWhitespaceTokens, 1, GdbTypes.DOUBLE_QUOTED_STRING, "\"Hello World\"")
        assertToken(nonWhitespaceTokens, 2, GdbTypes.WORD, "'single")
        assertToken(nonWhitespaceTokens, 3, GdbTypes.WORD, "quoted'")
    }

    @Test
    fun testPunctuationTokens() {
        val content = "print array[0], ptr->field, obj.member"
        val tokens = tokenize(content)

        println("=== DEBUG: Punctuation Tokens ===")
        printTokens(content)

        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE }

        // Check for specific punctuation tokens
        assertTrue(nonWhitespaceTokens.any { it.text == "[" && it.type == GdbTypes.LBRACKET }, "Should contain LBRACKET")
        assertTrue(nonWhitespaceTokens.any { it.text == "]" && it.type == GdbTypes.RBRACKET }, "Should contain RBRACKET")
        assertTrue(nonWhitespaceTokens.any { it.text == "," && it.type == GdbTypes.COMMA }, "Should contain COMMA")
        assertTrue(nonWhitespaceTokens.any { it.text == "->" && it.type == GdbTypes.ARROW }, "Should contain ARROW")
        assertTrue(nonWhitespaceTokens.any { it.text == "." && it.type == GdbTypes.DOT }, "Should contain DOT")
    }

    @Test
    fun testDefineBlock() {
        val content = """
            define mycommand
                print "executing mycommand"
                continue
            end
        """.trimIndent()

        val tokens = tokenize(content)
        val nonWhitespaceTokens = tokens.filter { it.type != TokenType.WHITE_SPACE && it.type != GdbTypes.CRLF }

        assertTrue(nonWhitespaceTokens.any { it.text == "define" && it.type == GdbTypes.DEFINE }, "Should contain DEFINE 'define'")
        assertTrue(nonWhitespaceTokens.any { it.text == "mycommand" && it.type == GdbTypes.IDENTIFIER }, "Should contain identifier 'mycommand'")
        assertTrue(nonWhitespaceTokens.any { it.text == "end" && it.type == GdbTypes.END }, "Should contain END 'end'")
    }

}
