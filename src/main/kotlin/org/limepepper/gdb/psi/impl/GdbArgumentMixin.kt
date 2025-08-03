// src/main/kotlin/org/limepepper/gdb/psi/impl/GdbArgumentMixin.kt
package org.limepepper.gdb.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

abstract class GdbArgumentMixin(node: ASTNode) : ASTWrapperPsiElement(node) {

    /**
     * Get the argument value (the token inside this argument)
     */
    fun getValue(): PsiElement? {
        return children.firstOrNull()
    }

    /**
     * Get the argument text
     */
    fun getArgumentText(): String {
        return text.trim()
    }
}
