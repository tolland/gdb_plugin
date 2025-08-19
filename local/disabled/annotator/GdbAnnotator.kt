package org.limepepper.lang.gdb.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import org.limepepper.gdb.psi.GdbCommandStatement

class GdbAnnotator : Annotator {
    override fun annotate(
        element: PsiElement,
        holder: AnnotationHolder
    ) {

        // Ensure the PSI Element is an expression
        if (element !is GdbCommandStatement) {
            return
        }


        // Define the text ranges (start is inclusive, end is exclusive)
        // "simple:key"
        //  01234567890
        val prefixRange = TextRange.from(
            element.textRange.startOffset,
            element.textRange.length
        )


        // highlight "simple" prefix and ":" separator
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()

    }


}
