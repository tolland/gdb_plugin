package org.limepepper.lang.gdb.docs

import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement

/**
 * Modern documentation target provider for GDB language elements
 */
internal class GdbPsiDocProvider : BaseGdbDocProvider(), PsiDocumentationTargetProvider {

    override fun documentationTarget(
        element: PsiElement,
        originalElement: PsiElement?
    ): DocumentationTarget? {
        return computeDocumentationTarget(element)
    }
}
