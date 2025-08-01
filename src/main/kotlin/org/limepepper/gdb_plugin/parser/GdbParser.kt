package org.limepepper.gdb_plugin.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType
import org.limepepper.gdb_plugin.GdbTokenTypes

/**
 * Simple parser for GDB language
 * This is a basic implementation for formatting support
 */
class GdbParser : PsiParser {
    
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val rootMarker = builder.mark()
        
        while (!builder.eof()) {
            parseStatement(builder)
        }
        
        rootMarker.done(root)
        return builder.treeBuilt
    }
    
    private fun parseStatement(builder: PsiBuilder) {
        val statementMarker = builder.mark()
        
        when {
            builder.tokenType in COMMAND_TOKENS -> {
                parseCommandStatement(builder)
                statementMarker.done(STATEMENT)
            }
            builder.tokenType == GdbTokenTypes.COMMENT -> {
                builder.advanceLexer()
                statementMarker.done(STATEMENT)
            }
            builder.tokenType == GdbTokenTypes.NEWLINE -> {
                builder.advanceLexer()
                statementMarker.drop()
            }
            builder.tokenType == GdbTokenTypes.WHITESPACE -> {
                builder.advanceLexer()
                statementMarker.drop()
            }
            else -> {
                builder.advanceLexer()
                statementMarker.drop()
            }
        }
    }
    
    private fun parseCommandStatement(builder: PsiBuilder) {
        // Parse command
        if (builder.tokenType in COMMAND_TOKENS) {
            builder.advanceLexer()
        }
        
        // Skip whitespace
        while (builder.tokenType == GdbTokenTypes.WHITESPACE) {
            builder.advanceLexer()
        }
        
        // Parse arguments
        while (!builder.eof() && 
               builder.tokenType != GdbTokenTypes.NEWLINE && 
               builder.tokenType != GdbTokenTypes.COMMENT) {
            
            if (builder.tokenType == GdbTokenTypes.WHITESPACE) {
                builder.advanceLexer()
                continue
            }
            
            parseArgument(builder)
        }
        
        // Parse comment if present
        if (builder.tokenType == GdbTokenTypes.COMMENT) {
            builder.advanceLexer()
        }
        
        // Parse newline if present
        if (builder.tokenType == GdbTokenTypes.NEWLINE) {
            builder.advanceLexer()
        }
    }
    
    private fun parseArgument(builder: PsiBuilder) {
        val argMarker = builder.mark()
        
        when (builder.tokenType) {
            GdbTokenTypes.STRING,
            GdbTokenTypes.NUMBER,
            GdbTokenTypes.HEX_NUMBER,
            GdbTokenTypes.REGISTER,
            GdbTokenTypes.IDENTIFIER -> {
                builder.advanceLexer()
                argMarker.done(ARGUMENT)
            }
            else -> {
                builder.advanceLexer()
                argMarker.drop()
            }
        }
    }
    
    companion object {
        val STATEMENT = GdbElementType("STATEMENT")
        val ARGUMENT = GdbElementType("ARGUMENT")
        
        val COMMAND_TOKENS = setOf(
            GdbTokenTypes.COMMAND_EXECUTION,
            GdbTokenTypes.COMMAND_BREAKPOINT,
            GdbTokenTypes.COMMAND_STACK,
            GdbTokenTypes.COMMAND_DATA,
            GdbTokenTypes.COMMAND_CONFIG,
            GdbTokenTypes.COMMAND_USER
        )
    }
}

// Import the element type
import org.limepepper.gdb_plugin.psi.GdbElementType