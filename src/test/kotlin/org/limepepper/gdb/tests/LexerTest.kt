package org.limepepper.gdb.tests

import com.intellij.lexer.FlexLexer
import com.intellij.psi.tree.IElementType
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.lexer.GdbLexer2
import org.limepepper.gdb.lexer._GdbLexer
import java.io.File

class LexerTest {

    private fun printTokens(lexer: FlexLexer, content: String) {
        lexer.reset(content, 0, content.length, 0)
        var token: IElementType? = lexer.advance()
        while (token != null) {
            val text = content.substring(lexer.tokenStart, lexer.tokenEnd).replace("\n", "\\n")
                .replace("\r", "\\r")
            val tokenName = token.toString()
            println("${tokenName.padEnd(30)}: '$text'")
            token = lexer.advance()
        }
    }

    @Test
    fun testGdbLexer() {
        println("=== DEBUG START ===")

        val content = File("src/test/testData/ParsingDataTest.gdb").readText()
        val lexer = GdbLexer()

        printTokens(lexer, content)

        println("=== DEBUG END ===")
    }

    @Test
    fun test_GdbLexer() {
        println("=== DEBUG START ===")

        val content = File("src/test/testData/ParsingDataTest.gdb").readText()
        val lexer = _GdbLexer()

        printTokens(lexer, content)

        println("=== DEBUG END ===")
    }

    @Test
    fun testGdbLexer2() {
        println("=== DEBUG START ===")

        val content = File("src/test/testData/ParsingDataTest.gdb").readText()
        val lexer = GdbLexer2()
        printTokens(lexer, content)
        println("=== DEBUG END ===")
    }

    @Test
    fun compareLexers() {
        val input = """
        # split over lines
        set var \
        ${'$'}myvar2 \
        = \
        7

        # assignment to simple value
        set pagination off

        # This is at the start of the file

        # This is floating

        # This is associated with a command
        break main
    """.trimIndent()

        println("=== Generated Lexer (_GdbLexer) ===")
        testLexer(_GdbLexer(), input)

        println("=== Production Lexer (GdbLexer) ===")
        testLexer(GdbLexer(), input)
    }

    private fun testLexer(lexer: FlexLexer, input: String) {
        lexer.reset(input, 0, input.length, 0)
        var token: IElementType? = lexer.advance()
        while (token != null) {
            val text = when (lexer) {
                is _GdbLexer -> lexer.yytext().toString()
                is GdbLexer -> lexer.yytext().toString()  // If GdbLexer extends generated class
                else -> "<?>"  // Fallback
            }.replace("\n", "\\n").replace("\r", "\\r")

            val tokenName = token.toString()
            println("${tokenName.padEnd(30)}: '$text'")
            token = lexer.advance()
        }
    }
}
