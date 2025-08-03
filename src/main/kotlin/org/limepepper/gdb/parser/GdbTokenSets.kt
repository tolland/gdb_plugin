package org.limepepper.gdb.psi

import com.intellij.psi.tree.TokenSet
import org.limepepper.gdb.parser.GdbTokenTypes

/**
 * Token sets for GDB language
 * Mix of original GdbTokenTypes (for lexer tokens) and generated GdbTypes (for grammar elements)
 */
object GdbTokenSets {

    // Basic lexer tokens - use original GdbTokenTypes
    @JvmField
    val WHITESPACE = TokenSet.create(GdbTokenTypes.WHITESPACE)

    @JvmField
    val COMMENTS = TokenSet.create(GdbTokenTypes.COMMENT)

    @JvmField
    val STRINGS = TokenSet.create(GdbTokenTypes.STRING)

    // Generated command tokens
    @JvmField
    val COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_EXECUTION,
        GdbTypes.COMMAND_BREAKPOINT,
        GdbTypes.COMMAND_STACK,
        GdbTypes.COMMAND_DATA,
        GdbTypes.COMMAND_CONFIG,
        GdbTypes.COMMAND_USER,
        GdbTypes.X_CMD,
        GdbTypes.PRINT_CMD,
        GdbTypes.P_CMD,
        GdbTypes.BREAK_CMD,
        GdbTypes.B_CMD
    )

    // Execution control commands
    @JvmField
    val EXECUTION_COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_EXECUTION
    )

    // Breakpoint-related commands
    @JvmField
    val BREAKPOINT_COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_BREAKPOINT,
        GdbTypes.BREAK_CMD,
        GdbTypes.B_CMD
    )

    // Data examination commands
    @JvmField
    val DATA_COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_DATA,
        GdbTypes.X_CMD,
        GdbTypes.PRINT_CMD,
        GdbTypes.P_CMD
    )

    // Literals - mix of original and generated
    @JvmField
    val LITERALS = TokenSet.create(
        GdbTypes.NUMBER,
        GdbTypes.HEX_NUMBER,
        GdbTypes.STRING,
        GdbTokenTypes.REGISTER // Use original since may not be generated
    )

    // Identifiers
    @JvmField
    val IDENTIFIERS = TokenSet.create(
        GdbTypes.IDENTIFIER
    )

    // Operators - use original since they're basic lexer tokens
    @JvmField
    val OPERATORS = TokenSet.create(
        GdbTokenTypes.OPERATOR,
        GdbTokenTypes.ASSIGNMENT
    )

    // Punctuation - use generated types
    @JvmField
    val PUNCTUATION = TokenSet.create(
        GdbTypes.LPAREN,
        GdbTypes.RPAREN,
        GdbTypes.LBRACKET,
        GdbTypes.RBRACKET,
        GdbTypes.LBRACE,
        GdbTypes.RBRACE,
        GdbTypes.COMMA,
        GdbTypes.SEMICOLON,
        GdbTypes.COLON,
        GdbTypes.DOT,
        GdbTypes.ARROW,
        GdbTypes.SCOPE_RESOLUTION
    )
}
