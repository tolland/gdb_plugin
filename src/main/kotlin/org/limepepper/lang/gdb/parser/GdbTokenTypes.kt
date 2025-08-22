package org.limepepper.lang.gdb.parser

import org.limepepper.lang.gdb.psi.GdbTokenType
import org.limepepper.lang.gdb.psi.GdbTypes

/**
 * Token types for GDB script lexical analysis
 */
object GdbTokenTypes : GdbTypes {
    @JvmField
    val PYTHON_INLINE = GdbTokenType("PYTHON_INLINE")

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

    // Operators
    @JvmField
    val OPERATOR = GdbTokenType("OPERATOR")

    @JvmField
    val ASSIGNMENT = GdbTokenType("ASSIGNMENT")

    // Whitespace and structure
    @JvmField
    val WHITESPACE = GdbTokenType("WHITESPACE")

    @JvmField
    val NEWLINE = GdbTokenType("NEWLINE")

    @JvmField
    val BAD_CHARACTER = GdbTokenType("BAD_CHARACTER")
}
