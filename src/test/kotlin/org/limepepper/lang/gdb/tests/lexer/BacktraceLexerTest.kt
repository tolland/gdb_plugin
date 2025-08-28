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
    fun pythonBlockIsSingleTokenUntilEnd() {
        val content = """
            # Stack navigation commands
            backtrace "123"
        """.trimIndent()
        val tokens = LexerTestUtils.tokenizeWithAdapter(content, GdbLexerAdapter()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
        assertEquals(GdbTypes.PYTHON_BLOCK, tokens[2].type)
        assertEquals(1, tokens.count { it.type == GdbTypes.PYTHON_BLOCK })
        assertEquals(GdbTypes.END, tokens.get(tokens.size - 1).type)
    }
}
