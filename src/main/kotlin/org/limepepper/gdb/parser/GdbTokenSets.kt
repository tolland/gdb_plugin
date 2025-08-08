package org.limepepper.gdb.psi

import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import org.limepepper.gdb.parser.GdbTokenTypes

/**
 * Token sets for GDB language
 * Mix of original GdbTokenTypes (for lexer tokens) and generated GdbTypes (for grammar elements)
 */
object GdbTokenSets {

    // common types
    val WHITESPACE: IElementType = TokenType.WHITE_SPACE

    // Basic lexer tokens - use original GdbTokenTypes
    @JvmField
    val whitespaceTokens = TokenSet.create(WHITESPACE, GdbTypes.LINE_CONTINUATION, GdbTypes.CRLF)

    @JvmField
    val COMMENTS = TokenSet.create(GdbTypes.COMMENT)

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
        GdbTypes.COMMAND_USER
    )

    // Execution control commands
    @JvmField
    val EXECUTION_COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_EXECUTION
    )

    // Breakpoint-related commands
    @JvmField
    val BREAKPOINT_COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_BREAKPOINT
    )

    // Data examination commands
    @JvmField
    val DATA_COMMANDS = TokenSet.create(
        GdbTypes.COMMAND_DATA
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
