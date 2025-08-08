package org.limepepper.gdb.documentation

import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.psi.PsiElement
import org.limepepper.gdb.psi.GdbPsiElement

internal abstract class BaseGdbDocProvider {

    protected fun computeDocumentationTarget(element: PsiElement): DocumentationTarget? {
        if (element !is GdbPsiElement) return null
        return GdbDocTarget(element)
    }

}
