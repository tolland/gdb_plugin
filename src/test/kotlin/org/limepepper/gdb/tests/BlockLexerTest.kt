package org.limepepper.gdb.tests

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.psi.GdbTypes
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BlockLexerTest {
    @Test
    fun pythonBlockIsSingleTokenUntilEnd() {
        val content = """
            python
            def do_something_on_exit(x):
                pass
            gdb.events.exited.connect(do_something_on_exit)
            end
        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
        assertEquals(GdbTypes.PYTHON_BLOCK, tokens[1].type)
        assertEquals(GdbTypes.END, tokens[2].type)
    }

    @Test
    fun guileBlockIsSingleTokenUntilEnd() {
        val content = """
            guile
            (display "hello")
            end
        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.GUILE_KW, tokens[0].type)
        assertEquals(GdbTypes.GUILE_BLOCK, tokens[1].type)
        assertEquals(GdbTypes.END, tokens[2].type)
    }
}


