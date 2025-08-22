package org.limepepper.lang.gdb.tests.lexer.utils

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import java.io.File
import kotlin.test.assertTrue

class VariousLexingsTest {
    @Test
    fun testParsingTestData() {
        val content = File("src/test/testData/ParsingTestData.gdb").readText()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
//        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
//        assertEquals(GdbTypes.PYTHON_BLOCK, tokens[1].type)
        //assertEquals(GdbTypes.END, tokens.get(tokens.size - 1).type)
    }

}
