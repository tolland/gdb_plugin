package org.limepepper.gdb_plugin.parser

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.limepepper.gdb_plugin.GdbLanguage
import org.limepepper.gdb_plugin.GdbTokenTypes
import org.limepepper.gdb_plugin.lexer.GdbLexerAdapter
import org.limepepper.gdb_plugin.psi.GdbFile
import org.limepepper.gdb_plugin.psi.GdbStatement
import org.limepepper.gdb_plugin.psi.GdbArgument

/**
 * Parser definition for GDB language
 */
class GdbParserDefinition : ParserDefinition {
    
    companion object {
        val FILE = IFileElementType(GdbLanguage.INSTANCE)
        
        val WHITESPACE_TOKENS = TokenSet.create(
            GdbTokenTypes.WHITESPACE
        )
        
        val COMMENT_TOKENS = TokenSet.create(
            GdbTokenTypes.COMMENT
        )
        
        val STRING_LITERAL_TOKENS = TokenSet.create(
            GdbTokenTypes.STRING
        )
    }

    override fun createLexer(project: Project?): Lexer = GdbLexerAdapter()

    override fun createParser(project: Project?): PsiParser {
        // For now, we'll implement a simple parser
        // In a full implementation, this would be generated from the BNF
        return GdbParser()
    }

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = COMMENT_TOKENS

    override fun getStringLiteralElements(): TokenSet = STRING_LITERAL_TOKENS

    override fun getWhitespaceTokens(): TokenSet = WHITESPACE_TOKENS

    override fun createElement(node: ASTNode): PsiElement {
        // Create specific PSI elements based on the element type
        return when (node.elementType) {
            GdbParser.STATEMENT -> GdbStatement(node)
            GdbParser.ARGUMENT -> GdbArgument(node)
            else -> ASTWrapperPsiElement(node)
        }
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return GdbFile(viewProvider)
    }
}
