package org.limepepper.gdb_plugin.psi

import com.intellij.psi.tree.IElementType
import org.limepepper.gdb_plugin.GdbLanguage

/**
 * PSI element type for GDB language constructs
 */
class GdbElementType(debugName: String) : IElementType(debugName, GdbLanguage.INSTANCE) {
    override fun toString(): String = "GdbElementType.${super.toString()}"
}