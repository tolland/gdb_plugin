package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.lexer.GdbLexerAdapter
import org.limepepper.lang.gdb.psi.GdbTypes
import org.limepepper.lang.gdb.tests.lexer.utils.LexerTestUtils
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BacktraceLexerTest {
    @Test
    fun backTraceargVariations() {
        val content = """
            # Stack navigation commands
            backtrace "123"
        """.trimIndent()
        val tokens = LexerTestUtils.tokenizeWithAdapter(content, GdbLexerAdapter()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.COMMAND_STACK, tokens[2].type)
        assertEquals(GdbTypes.DOUBLE_QUOTED_STRING, tokens[3].type)
        assertEquals(1, tokens.count { it.type == GdbTypes.COMMAND_STACK })
        assertEquals(1, tokens.count { it.type == GdbTypes.DOUBLE_QUOTED_STRING })
    }
}
