package org.limepepper.gdb_plugin.formatting

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import org.limepepper.gdb_plugin.GdbLanguage

/**
 * Formatting model builder for GDB scripts
 */
class GdbFormattingModelBuilder : FormattingModelBuilder {
    
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val element = formattingContext.psiElement
        val settings = formattingContext.codeStyleSettings
        val range = formattingContext.formattingRange
        
        return FormattingModelProvider.createFormattingModelForPsiFile(
            element.containingFile,
            GdbBlock(
                element.node,
                null,
                Indent.getNoneIndent(),
                null,
                settings
            ),
            settings
        )
    }

    override fun getRangeAffectingIndent(file: PsiFile, offset: Int, elementAtOffset: ASTNode): TextRange? {
        return null
    }
}