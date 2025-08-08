package org.limepepper.gdb.tests

import com.intellij.lexer.FlexLexer
import com.intellij.psi.tree.IElementType
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.lexer._GdbLexer
import java.io.File

class LexerTest {

    /**
     * Prints all tokens and their text values from a given FlexLexer instance.
     *
     * @param lexer The FlexLexer instance to tokenize input from
     * @param content The input string to be tokenized
     */
    private fun printTokens(lexer: FlexLexer, content: String) {
        println("=== DEBUG START ===")
        lexer.reset(content, 0, content.length, 0)
        var token: IElementType? = lexer.advance()
        while (token != null) {
            val text = content.substring(lexer.tokenStart, lexer.tokenEnd).replace("\n", "\\n")
                .replace("\r", "\\r")
            val tokenName = token.toString()
            println("${tokenName.padEnd(30)}: '$text'")
            token = lexer.advance()
        }
        println("=== DEBUG END ===")
    }

    /**
     * Test the plugin Lexer
     */
    @Test
    fun testGdbLexer() {

        val content = File("src/test/testData/ParsingDataTest.gdb").readText()
        val lexer = GdbLexer()

        printTokens(lexer, content)

    }

    @Test
    fun compareLexers() {
        val input = """
        # This is at the start of the file

        # split over lines using line continuation
        set var \
        ${'$'}myvar2 \
        = \
        7

        # A multi line comment block associated
        # with a command
        set pagination off


        # This is floating

        # This is associated with a command
        break main
    """.trimIndent()

        println("=== Generated Lexer (_GdbLexer) ===")
        testLexer(GdbLexer(), input)

    }

}
