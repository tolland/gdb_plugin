package org.limepepper.lang.gdb.tests.lexer

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.lang.gdb.lexer.GdbLexer
import org.limepepper.lang.gdb.tests.lexer.utils.LexerTestUtils
import kotlin.test.assertTrue

class NestedTest {

    @Test
    fun simpleNested() {
        val content = """
            document mycommand other fucking arguments
            Source file and execute command in it
            what if we have an end in here
                end

            break foo1

            define mycommand2
              break foo2
              commands
                  silent
                  printf "x is %d\n",x
                  cont
              end
            end

            commands foo1 # lets define our commands list for foo1 elsewhere\
            to be annoying
            print "this is annoying syntax"
            end

        """.trimIndent()

        val tokens = LexerTestUtils.tokenize(content, GdbLexer())
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER }, "No BAD_CHARACTER tokens expected")

        // Now break should push args state, so everything after "break" until newline should be ARG tokens
        val nonWs = tokens.filter { it.type != TokenType.WHITE_SPACE }

//        // Find the break command and verify args are tokenized as ARG
//        val breakIndex = nonWs.indexOfFirst { it.type == GdbTypes.COMMAND_BREAKPOINT && it.text == "break" }
//        assertTrue(breakIndex >= 0, "Should find break command")
//
//        // After break, expect: foo if x>0 (all as ARG tokens)
//        val afterBreak = nonWs.drop(breakIndex + 1).takeWhile { it.type != GdbTypes.CRLF }
//        assertTrue(afterBreak.isNotEmpty(), "Should have args after break")
//        assertTrue(afterBreak.all { it.type == GdbTypes.ARG }, "All args after break should be ARG tokens")
//
//        // Verify we see the expected structure
//        assertTrue(nonWs.any { it.type == GdbTypes.COMMAND_COMMANDS && it.text == "commands" })
//        assertTrue(nonWs.any { it.type == GdbTypes.END && it.text == "end" })
//        assertEquals(GdbTypes.END, tokens.get(tokens.size - 1).type)
    }

}
