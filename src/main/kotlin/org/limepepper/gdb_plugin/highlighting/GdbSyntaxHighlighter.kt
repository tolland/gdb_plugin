package org.limepepper.gdb_plugin.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.limepepper.gdb_plugin.GdbTokenTypes
import org.limepepper.gdb_plugin.lexer.GdbLexerAdapter

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
    }

    override fun getHighlightingLexer(): Lexer = GdbLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            GdbTokenTypes.COMMENT -> arrayOf(COMMENT)
            
            // Command categories
            GdbTokenTypes.COMMAND_EXECUTION -> arrayOf(COMMAND_EXECUTION)
            GdbTokenTypes.COMMAND_BREAKPOINT -> arrayOf(COMMAND_BREAKPOINT)
            GdbTokenTypes.COMMAND_STACK -> arrayOf(COMMAND_STACK)
            GdbTokenTypes.COMMAND_DATA -> arrayOf(COMMAND_DATA)
            GdbTokenTypes.COMMAND_CONFIG -> arrayOf(COMMAND_CONFIG)
            GdbTokenTypes.COMMAND_USER -> arrayOf(COMMAND_USER)
            GdbTokenTypes.COMMAND_GENERAL -> arrayOf(COMMAND_GENERAL)
            
            // Literals
            GdbTokenTypes.NUMBER -> arrayOf(NUMBER)
            GdbTokenTypes.HEX_NUMBER -> arrayOf(HEX_NUMBER)
            GdbTokenTypes.STRING -> arrayOf(STRING)
            GdbTokenTypes.REGISTER -> arrayOf(REGISTER)
            
            // Identifiers
            GdbTokenTypes.IDENTIFIER -> arrayOf(IDENTIFIER)
            GdbTokenTypes.FUNCTION_NAME -> arrayOf(IDENTIFIER)
            
            // Operators and punctuation
            GdbTokenTypes.OPERATOR -> arrayOf(OPERATOR)
            GdbTokenTypes.ASSIGNMENT -> arrayOf(OPERATOR)
            GdbTokenTypes.ADDRESS_MARKER -> arrayOf(OPERATOR)
            
            GdbTokenTypes.LPAREN, GdbTokenTypes.RPAREN,
            GdbTokenTypes.LBRACKET, GdbTokenTypes.RBRACKET,
            GdbTokenTypes.LBRACE, GdbTokenTypes.RBRACE,
            GdbTokenTypes.COMMA, GdbTokenTypes.SEMICOLON,
            GdbTokenTypes.COLON, GdbTokenTypes.DOT,
            GdbTokenTypes.ARROW, GdbTokenTypes.SCOPE_RESOLUTION -> arrayOf(PUNCTUATION)
            
            // Special keywords
            GdbTokenTypes.CONDITION_IF -> arrayOf(CONDITION_IF)
            
            // Error handling
            GdbTokenTypes.BAD_CHARACTER -> arrayOf(BAD_CHARACTER)
            
            else -> emptyArray()
        }
    }
}