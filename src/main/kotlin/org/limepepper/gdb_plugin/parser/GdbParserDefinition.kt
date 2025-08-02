package org.limepepper.gdb_plugin.parser

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
import org.limepepper.gdb_plugin.psi.GdbTypes
import org.limepepper.gdb_plugin.psi.GdbTokenSets
import org.limepepper.gdb_plugin.lexer.GdbLexerAdapter
import org.limepepper.gdb_plugin.psi.GdbFile

/**
 * Parser definition for GDB language
 */
class GdbParserDefinition : ParserDefinition {
    
    companion object {
        val FILE = IFileElementType(GdbLanguage.INSTANCE)
    }

    override fun createLexer(project: Project?): Lexer = GdbLexerAdapter()

    override fun createParser(project: Project?): PsiParser {
        return GdbParser() // Generated parser
    }

    override fun getFileNodeType(): IFileElementType = FILE

    override fun getCommentTokens(): TokenSet = GdbTokenSets.COMMENTS

    override fun getStringLiteralElements(): TokenSet = GdbTokenSets.STRINGS

    override fun getWhitespaceTokens(): TokenSet = GdbTokenSets.WHITESPACE

    override fun createElement(node: ASTNode): PsiElement {
        // Use the generated factory method
        return GdbTypes.Factory.createElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return GdbFile(viewProvider)
    }
}
