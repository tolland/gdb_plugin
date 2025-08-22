package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.psi.GdbTypes
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.limepepper.lang.gdb.parser.GdbTokenTypes;
import org.limepepper.lang.gdb.tests.lexer.utils.LexerTestUtils

class PythonLexerTest {
    @Test
    fun pythonBlockIsSingleLineSimple() {
        val content = """
            python print("test")

        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
        assertEquals(GdbTokenTypes.PYTHON_INLINE, tokens[1].type)
        assertEquals(GdbTypes.CRLF, tokens[2].type)
    }
    @Test
    fun pythonBlockIsSingleLineTrailingComment() {
        val content = """
            python print("test") # this is a trailing comment

        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
        assertEquals(GdbTokenTypes.PYTHON_INLINE, tokens[1].type)
        assertEquals(GdbTypes.CRLF, tokens[2].type)
    }


}
