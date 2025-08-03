package org.limepepper.gdb.parser

import com.intellij.psi.tree.IElementType
import org.limepepper.gdb.GdbLanguage

/**
 * Token types for GDB script lexical analysis
 */
object GdbTokenTypes {

    // Comments
    @JvmField
    val COMMENT = GdbTokenType("COMMENT")

    // Commands - categorized for different highlighting
    // run, continue, step, etc.
    @JvmField
    val COMMAND_EXECUTION = GdbTokenType("COMMAND_EXECUTION")

    // break, watch, catch, etc.
    @JvmField
    val COMMAND_BREAKPOINT = GdbTokenType("COMMAND_BREAKPOINT")

    // backtrace, frame, up, down
    @JvmField
    val COMMAND_STACK = GdbTokenType("COMMAND_STACK")
    // print, x, display, info
    @JvmField
    val COMMAND_DATA = GdbTokenType("COMMAND_DATA")
    // set, show, source, file
    @JvmField
    val COMMAND_CONFIG = GdbTokenType("COMMAND_CONFIG")
    // define, document, end, if
    @JvmField
    val COMMAND_USER = GdbTokenType("COMMAND_USER")
    // other commands
    @JvmField
    val COMMAND_GENERAL = GdbTokenType("COMMAND_GENERAL")

    // Literals
    @JvmField
    val NUMBER = GdbTokenType("NUMBER")
    @JvmField
    val HEX_NUMBER = GdbTokenType("HEX_NUMBER")
    @JvmField
    val STRING = GdbTokenType("STRING")
    @JvmField
    val REGISTER = GdbTokenType("REGISTER")

    // Identifiers and symbols
    @JvmField
    val IDENTIFIER = GdbTokenType("IDENTIFIER")
    @JvmField
    val FUNCTION_NAME = GdbTokenType("FUNCTION_NAME")

    // Operators
    @JvmField
    val OPERATOR = GdbTokenType("OPERATOR")
    @JvmField
    val ASSIGNMENT = GdbTokenType("ASSIGNMENT")

    // Punctuation
    @JvmField
    val LPAREN = GdbTokenType("LPAREN")
    @JvmField
    val RPAREN = GdbTokenType("RPAREN")
    @JvmField
    val LBRACKET = GdbTokenType("LBRACKET")
    @JvmField
    val RBRACKET = GdbTokenType("RBRACKET")
    @JvmField
    val LBRACE = GdbTokenType("LBRACE")
    @JvmField
    val RBRACE = GdbTokenType("RBRACE")
    @JvmField
    val COMMA = GdbTokenType("COMMA")
    @JvmField
    val SEMICOLON = GdbTokenType("SEMICOLON")
    @JvmField
    val COLON = GdbTokenType("COLON")
    @JvmField
    val DOT = GdbTokenType("DOT")
    @JvmField
    val ARROW = GdbTokenType("ARROW")
    @JvmField
    val SCOPE_RESOLUTION = GdbTokenType("SCOPE_RESOLUTION")

    // Special symbols
    @JvmField
    val ADDRESS_MARKER = GdbTokenType("ADDRESS_MARKER")            // * for addresses
    @JvmField
    val CONDITION_IF = GdbTokenType("CONDITION_IF")                // if in breakpoint conditions

    // Whitespace and structure
    @JvmField
    val WHITESPACE = GdbTokenType("WHITESPACE")
    @JvmField
    val NEWLINE = GdbTokenType("NEWLINE")
    @JvmField
    val BAD_CHARACTER = GdbTokenType("BAD_CHARACTER")
}

/**
 * Custom token type for GDB language
 */
class GdbTokenType(debugName: String) : IElementType(debugName, GdbLanguage.Companion.INSTANCE) {
    override fun toString(): String = "GdbTokenType.${super.toString()}"
}
