package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.psi.GdbTypes
import org.limepepper.lang.gdb.tests.lexer.utils.LexerTestUtils
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommandsTokenTest {
    @Test
    fun pythonBlockIsSingleTokenUntilEnd() {
        val content = """
            # Set up breakpoints for key Epub3Generator methods
            break Epub3Generator::Epub3Generator
            commands 1
                # echo \n=== Epub3Generator Constructor ===\n
                bt
                continue
            end
        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
//        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
//        assertEquals(GdbTypes.PYTHON_BLOCK, tokens[1].type)
        assertEquals(GdbTypes.END, tokens.get(tokens.size - 1).type)
    }
}
