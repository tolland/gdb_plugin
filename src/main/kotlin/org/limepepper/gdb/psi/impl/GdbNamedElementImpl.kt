package org.limepepper.gdb.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.limepepper.gdb.psi.GdbNamedElement

class GdbNamedElementImpl(private val node: ASTNode) : ASTWrapperPsiElement(node),
    GdbNamedElement {
    override fun toString() = "GdbElement(IDENTIFIER)"
    override fun getNode(): ASTNode = node

    override fun getName(): String {
        return "IDENT"
    }

    override fun setName(newName: String): PsiElement {
//        return setName(this, newName)
        TODO("return somethign")
    }

    override fun getNameIdentifier(): PsiElement? {
        TODO("Not yet implemented")
    }
}
