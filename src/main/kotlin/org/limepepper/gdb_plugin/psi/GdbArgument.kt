package org.limepepper.gdb_plugin.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

/**
 * PSI element for GDB command arguments
 */
class GdbArgument(node: ASTNode) : GdbPsiElement(node) {

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

    override fun toString(): String = "GdbArgument"
}
