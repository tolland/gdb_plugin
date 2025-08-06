package org.limepepper.gdb.psi

import com.intellij.psi.tree.IElementType
import org.limepepper.gdb.lang.GdbLanguage

/**
 * PSI element type for GDB language constructs
 */
class GdbElementType(debugName: String) : IElementType(debugName, GdbLanguage) {
    override fun toString(): String = "GdbElementType.${super.toString()}"
}
