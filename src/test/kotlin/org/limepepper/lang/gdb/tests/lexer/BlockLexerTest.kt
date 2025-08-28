package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.lexer.GdbLexerAdapter
import org.limepepper.lang.gdb.psi.GdbTypes
import org.limepepper.lang.gdb.tests.lexer.utils.LexerTestUtils
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
        val tokens = LexerTestUtils.tokenizeWithAdapter(content, GdbLexerAdapter()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
        assertEquals(GdbTypes.PYTHON_BLOCK, tokens[2].type)
        assertEquals(1, tokens.count { it.type == GdbTypes.PYTHON_BLOCK })
        assertEquals(GdbTypes.END, tokens.get(tokens.size - 1).type)
    }
    @Test
    fun breakpointSimpleTest() {
        val content = """
            break main
        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.COMMAND_BREAKPOINT, tokens[0].type)
        assertEquals(GdbTypes.ARG, tokens[1].type)
    }
    @Test
    fun nestedDefineBreakpointTest() {
        val content = """
            define my_func
                break main
                commands
                  silent
                  print "print print something"
                end
            end
        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.COMMAND_USER, tokens[0].type)
        assertEquals(GdbTypes.ARG, tokens[1].type)
        assertEquals(GdbTypes.DOUBLE_QUOTED_STRING, tokens[11].type)
        assertEquals(GdbTypes.END, tokens.get(tokens.size - 1).type)
        assertEquals(GdbTypes.END, tokens.get(tokens.size - 3).type)
    }
}
