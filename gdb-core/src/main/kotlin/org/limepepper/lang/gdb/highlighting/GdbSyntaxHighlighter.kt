package org.limepepper.lang.gdb.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.limepepper.lang.gdb.lexer.GdbLexerAdapter
import org.limepepper.lang.gdb.parser.GdbTokenSets
import org.limepepper.lang.gdb.psi.GdbTypes

/**
 * Syntax highlighter for GDB script files
 */
class GdbSyntaxHighlighter : SyntaxHighlighterBase() {

    companion object {
        // Define text attribute keys for different syntax elements
        @JvmField val COMMENT = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )

        @JvmField val COMMAND_EXECUTION = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_EXECUTION",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        @JvmField val COMMAND_BREAKPOINT = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_BREAKPOINT",
            DefaultLanguageHighlighterColors.INSTANCE_METHOD
        )

        @JvmField val COMMAND_STACK = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_STACK",
            DefaultLanguageHighlighterColors.STATIC_METHOD
        )

        @JvmField val COMMAND_DATA = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_DATA",
            DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL
        )

        @JvmField val COMMAND_CONFIG = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_CONFIG",
            DefaultLanguageHighlighterColors.METADATA
        )

        @JvmField val COMMAND_USER = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_USER",
            DefaultLanguageHighlighterColors.FUNCTION_DECLARATION
        )

        @JvmField val COMMAND_PRINT = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_PRINT",
            DefaultLanguageHighlighterColors.STATIC_METHOD
        )

        @JvmField val COMMAND_GENERAL = TextAttributesKey.createTextAttributesKey(
            "GDB_COMMAND_GENERAL",
            DefaultLanguageHighlighterColors.IDENTIFIER
        )

        @JvmField val NUMBER = TextAttributesKey.createTextAttributesKey(
            "GDB_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )

        @JvmField val HEX_NUMBER = TextAttributesKey.createTextAttributesKey(
            "GDB_HEX_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )

        @JvmField val STRING = TextAttributesKey.createTextAttributesKey(
            "GDB_STRING",
            DefaultLanguageHighlighterColors.STRING
        )

        @JvmField val TEXT = TextAttributesKey.createTextAttributesKey(
            "GDB_TEXT",
            DefaultLanguageHighlighterColors.STRING
        )

        @JvmField val REGISTER = TextAttributesKey.createTextAttributesKey(
            "GDB_REGISTER",
            DefaultLanguageHighlighterColors.INSTANCE_FIELD
        )

        @JvmField val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "GDB_IDENTIFIER",
            DefaultLanguageHighlighterColors.IDENTIFIER
        )

        @JvmField val OPERATOR = TextAttributesKey.createTextAttributesKey(
            "GDB_OPERATOR",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )

        @JvmField val PUNCTUATION = TextAttributesKey.createTextAttributesKey(
            "GDB_PUNCTUATION",
            DefaultLanguageHighlighterColors.PARENTHESES
        )

        @JvmField val CONDITION_IF = TextAttributesKey.createTextAttributesKey(
            "GDB_CONDITION_IF",
            DefaultLanguageHighlighterColors.KEYWORD
        )

        @JvmField val BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
            "GDB_BAD_CHARACTER",
            HighlighterColors.BAD_CHARACTER
        )

        @JvmField val GDB_ARGS = TextAttributesKey.createTextAttributesKey(
            "GDB_ARGS",
            DefaultLanguageHighlighterColors.PARAMETER
        )
    }

    override fun getHighlightingLexer(): Lexer = GdbLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when {
            // Use TokenSets for cleaner code - reference the correct attributes
            tokenType in GdbTokenSets.EXECUTION_COMMANDS -> arrayOf(COMMAND_EXECUTION)
            tokenType in GdbTokenSets.BREAKPOINT_COMMANDS -> arrayOf(COMMAND_BREAKPOINT)
            tokenType in GdbTokenSets.DATA_COMMANDS -> arrayOf(COMMAND_DATA)
            tokenType in GdbTokenSets.COMMENTS -> arrayOf(COMMENT)
            tokenType in GdbTokenSets.STRINGS -> arrayOf(STRING)
            tokenType in GdbTokenSets.LITERALS -> arrayOf(NUMBER)
            tokenType in GdbTokenSets.OPERATORS -> arrayOf(OPERATOR)

            // Specific token highlighting
            tokenType == GdbTypes.COMMAND_STACK -> arrayOf(COMMAND_STACK)
            tokenType == GdbTypes.COMMAND_CONFIG -> arrayOf(COMMAND_CONFIG)
            tokenType == GdbTypes.COMMAND_USER -> arrayOf(COMMAND_USER)
            tokenType == GdbTypes.COMMAND_PRINT -> arrayOf(COMMAND_PRINT)
//            tokenType == GdbTypes.FORMAT_SPEC -> arrayOf(COMMAND_GENERAL) // or create a FORMAT_SPEC attribute
            tokenType == GdbTypes.REGISTER -> arrayOf(REGISTER)
            tokenType == GdbTypes.HEX_NUMBER -> arrayOf(HEX_NUMBER)
            tokenType == GdbTypes.ARG -> arrayOf(HEX_NUMBER)
            tokenType == GdbTypes.DOC_BLOCK_LINE -> arrayOf(STRING)

            else -> emptyArray()
        }
    }
}
