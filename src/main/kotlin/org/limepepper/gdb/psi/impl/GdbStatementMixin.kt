// src/main/kotlin/org/limepepper/gdb/psi/impl/GdbStatementMixin.kt
package org.limepepper.gdb.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.limepepper.gdb.psi.GdbNamedElement

abstract class GdbStatementMixin(node: ASTNode) : ASTWrapperPsiElement(node), GdbNamedElement {

    override fun getName(): String? {
        // Use the generated getCommandName() method
        return (this as? org.limepepper.gdb.psi.GdbStatement)?.commandName
    }

    override fun setName(name: String): PsiElement {
        // Implementation for renaming - not typically used for GDB commands
        return this
    }
}
