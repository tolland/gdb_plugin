package org.limepepper.lang.gdb.injection

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.impl.source.tree.LeafElement
import com.intellij.util.IncorrectOperationException

class GdbPyBlockElementManipulator : AbstractElementManipulator<GdbPyBlockPsi>() {
    
    override fun handleContentChange(element: GdbPyBlockPsi, range: TextRange, newContent: String): GdbPyBlockPsi {
        val oldText = element.text
        val newText = oldText.substring(0, range.startOffset) + newContent + oldText.substring(range.endOffset)
        
        return try {
            (element.node as LeafElement).replaceWithText(newText)
            element
        } catch (e: Exception) {
            throw IncorrectOperationException("Cannot replace text", e)
        }
    }

    override fun getRangeInElement(element: GdbPyBlockPsi): TextRange {
        return TextRange.from(0, element.textLength)
    }
}