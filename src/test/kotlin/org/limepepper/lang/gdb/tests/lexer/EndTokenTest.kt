package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.psi.GdbTypes
import org.limepepper.lang.gdb.tests.lexer.utils.LexerTestUtils
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EndTokenTest {
    @Test
    fun pythonBlockIsSingleTokenUntilEnd() {
        val content = """
            print "Something here"

            document mycommand
            a
            This is some documentation for a command. end
            b
            this has en "end" in it, and another end
               end - this one is indented and has trailing chars
            end

            define adder
              print "this is another block with an end"
            end

            # another end in a comment for good measure
            document mycommand
            This one finishes with EOF
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
