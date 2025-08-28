package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.lexer.GdbLexerAdapter
import org.limepepper.lang.gdb.psi.GdbTypes
import kotlin.test.assertEquals
import kotlin.test.assertTrue
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
        assertEquals(GdbTypes.PYTHON_INLINE, tokens[1].type)
        assertEquals(GdbTypes.CRLF, tokens[2].type)
    }
    @Test
    fun pythonBlockIsSingleLineSimple2() {
        val content = """
            python print("hello"); print("sometrhing");
                         python                 \
            print("this is inline")
            python                 \

            print("this is body")
            end

            python print("hello"); print("sometrhing");
            define my_func
                print "some stuff"
                python print("hello"); print("sometrhing");
                bre main
                commands
                python print("hello"); print("sometrhing");
                end
                end
                python print("hello");\
                    print("sometrhing");
        """.trimIndent()
        val tokens = LexerTestUtils.tokenize(content, GdbLexer()).filter { it.type != TokenType.WHITE_SPACE }
        // LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })
        assertEquals(GdbTypes.PYTHON_KW, tokens[0].type)
        assertEquals(GdbTypes.PYTHON_INLINE, tokens[1].type)
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
        assertEquals(GdbTypes.PYTHON_INLINE, tokens[1].type)
        assertEquals(GdbTypes.CRLF, tokens[2].type)
    }
    @Test
    fun pythonBlockTestInjection() {
        val content = """
            python
            def exit_handler():
                print("do something on exit")
            gdb.events.exited.connect(exit_handler)
            end

            define my_func
            print "this is block to check that the lexer isn't greedy"
            end

        """.trimIndent()
        val tokens = LexerTestUtils.tokenizeWithAdapter(content, GdbLexerAdapter()).filter { it.type != TokenType.WHITE_SPACE }
        LexerTestUtils.printTokens(tokens)
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER })

        // Check that we have a single merged PYTHON_BLOCK_LINE token
        val pythonBlockTokens = tokens.filter { it.type == GdbTypes.PYTHON_BLOCK }
        assertEquals(1, pythonBlockTokens.size, "Expected exactly one merged PYTHON_BLOCK_LINE token")

        // The merged token should contain all the Python code
        val mergedToken = pythonBlockTokens[0]
        assertTrue(mergedToken.text.contains("def exit_handler():"))
        assertTrue(mergedToken.text.contains("print(\"do something on exit\")"))
        assertTrue(mergedToken.text.contains("gdb.events.exited.connect(exit_handler)"))
    }


}
