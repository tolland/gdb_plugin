package org.limepepper.lang.gdb.injection

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.ElementManipulators
import com.intellij.psi.LiteralTextEscaper
import com.intellij.psi.PsiLanguageInjectionHost
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType


class GdbPyBlockPsi(type: IElementType, text: CharSequence) :
    LeafPsiElement(type, text),
    PsiLanguageInjectionHost {

    init {
        println("GdbPyBlockPsi constructed for node: ${node.elementType}")
    }

    override fun isValidHost() = true

    override fun createLiteralTextEscaper(): LiteralTextEscaper<out PsiLanguageInjectionHost> {
        println("createLiteralTextEscaper called for: $this")
        return object : LiteralTextEscaper<GdbPyBlockPsi>(this) {
            override fun decode(rangeInsideHost: TextRange, outChars: StringBuilder): Boolean {
                val text = rangeInsideHost.substring(myHost.text)
                outChars.append(text)
                return true
            }

            override fun getOffsetInHost(offsetInDecoded: Int, rangeInsideHost: TextRange): Int {
                return rangeInsideHost.startOffset + offsetInDecoded
            }

            override fun getRelevantTextRange(): TextRange {
                return TextRange.from(0, myHost.textLength)
            }

            override fun isOneLine(): Boolean = false
        }
    }

    override fun updateText(text: String): PsiLanguageInjectionHost {
        println("calling updateText")
        return ElementManipulators.handleContentChange(this, text)
    }
}
