package org.limepepper.gdb.tests

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.psi.GdbTypes
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommandStructureLexerTest {

    @Test
    fun simpleCommandPerLine() {
        val content = """
            break foo if x>0
            commands
                silent
                printf "x is %d\n",x
                cont
            end
        """.trimIndent()

        val tokens = LexerTestUtils.tokenize(content, GdbLexer())
        // Developer-friendly print (guarded by LEXER_DEBUG env/sysprop)
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER }, "No BAD_CHARACTER tokens expected")

        // sanity: should see DEFINE/END/COMMANDS or generic COMMAND tokens in order
        val nonWs = tokens.filter { it.type != TokenType.WHITE_SPACE }
        assertTrue(nonWs.any { it.type == GdbTypes.COMMAND_BREAKPOINT && it.text == "break" })
        assertTrue(nonWs.any { it.type == GdbTypes.COMMANDS && it.text == "commands" })
        assertTrue(nonWs.any { it.type == GdbTypes.END && it.text == "end" })
    }
}


