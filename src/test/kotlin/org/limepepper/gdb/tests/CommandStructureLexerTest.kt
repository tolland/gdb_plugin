package org.limepepper.gdb.tests

import com.intellij.psi.TokenType
import org.junit.Test
import org.limepepper.gdb.lexer.GdbLexer
import org.limepepper.gdb.psi.GdbTypes
import org.limepepper.gdb.parser.GdbTokenTypes
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommandStructureLexerTest {

    @Test
    fun simpleCommandPerLine() {
        val content = """
            break foo if x>0
            commands
                silent
                printf "x is %d\n",x
                cont
            end
        """.trimIndent()

        val tokens = LexerTestUtils.tokenize(content, GdbLexer())
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER }, "No BAD_CHARACTER tokens expected")

        // Now break should push args state, so everything after "break" until newline should be ARG tokens
        val nonWs = tokens.filter { it.type != TokenType.WHITE_SPACE }
        
        // Find the break command and verify args are tokenized as ARG
        val breakIndex = nonWs.indexOfFirst { it.type == GdbTypes.COMMAND_BREAKPOINT && it.text == "break" }
        assertTrue(breakIndex >= 0, "Should find break command")
        
        // After break, expect: foo if x>0 (all as ARG tokens)
        val afterBreak = nonWs.drop(breakIndex + 1).takeWhile { it.type != GdbTypes.CRLF }
        assertTrue(afterBreak.isNotEmpty(), "Should have args after break")
        assertTrue(afterBreak.all { it.type == GdbTokenTypes.ARG }, "All args after break should be ARG tokens")
        
        // Verify we see the expected structure
        assertTrue(nonWs.any { it.type == GdbTypes.COMMANDS && it.text == "commands" })
        assertTrue(nonWs.any { it.type == GdbTypes.END && it.text == "end" })
    }

    @Test
    fun commandWithDelimiter() {
        val content = "with print pretty on -- my_complex_command"
        
        val tokens = LexerTestUtils.tokenize(content, GdbLexer())
        assertTrue(tokens.none { it.type == TokenType.BAD_CHARACTER }, "No BAD_CHARACTER tokens expected")
        
        // Debug: print tokens to see what we're actually getting
        println("==== TOKENS for delimiter test ====")
        tokens.forEachIndexed { i, token ->
            println("[$i] ${token.type} | '${token.text}' | ${token.start}..${token.end}")
        }
        
        val nonWs = tokens.filter { it.type != TokenType.WHITE_SPACE }
        
        // Should see: with (COMMAND_GENERIC) + args (ARG) + -- (ARG) + my_complex_command (ARG)
        // The -- delimiter allows additional arguments to be specified
        assertTrue(nonWs.any { it.type == GdbTypes.COMMAND_GENERIC && it.text == "with" })
        assertTrue(nonWs.any { it.type == GdbTokenTypes.ARG && it.text == "--" })
        assertTrue(nonWs.any { it.type == GdbTokenTypes.ARG && it.text == "my_complex_command" })
        
        // Verify the structure: COMMAND + ARGS + DELIMITER + MORE_ARGS
        val commandIndex = nonWs.indexOfFirst { it.type == GdbTypes.COMMAND_GENERIC && it.text == "with" }
        assertTrue(commandIndex >= 0, "Should find 'with' command")
        
        // Everything after 'with' should be ARG tokens (excluding the final ARGS_BLOCK token)
        val afterCommand = nonWs.drop(commandIndex + 1).filter { it.type != GdbTypes.ARGS_BLOCK }
        assertTrue(afterCommand.isNotEmpty(), "Should have args after 'with'")
        assertTrue(afterCommand.all { it.type == GdbTokenTypes.ARG }, "All args after 'with' should be ARG tokens")
    }
}


