package org.limepepper.lang.gdb.injection

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTFactory
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType
import org.limepepper.lang.gdb.psi.GdbTypes
import org.limepepper.lang.gdb.injection.GdbPyBlockPsi

class GdbAstFactory : ASTFactory() {
    override fun createLeaf(type: IElementType, text: CharSequence): LeafElement? {
        println("type " + type)
        return if (type == GdbTypes.PYTHON_BLOCK || type == GdbTypes.PYTHON_BLOCK_LINE) {
            println("replacing python block node")
            // Use PYTHON_BLOCK as the type for the PSI element regardless of input type
            return GdbPyBlockPsi(GdbTypes.PYTHON_BLOCK, text)
        } else {
            super.createLeaf(type, text)
        }
    }
}
