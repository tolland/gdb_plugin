// src/main/kotlin/org/limepepper/gdb_plugin/psi/GdbTokenType.kt

package org.limepepper.gdb_plugin.psi

import com.intellij.psi.tree.IElementType
import org.limepepper.gdb_plugin.GdbLanguage

/**
 * Token type for GDB language
 */
class GdbTokenType(debugName: String) : IElementType(debugName, GdbLanguage.INSTANCE) {
    override fun toString(): String = "GdbTokenType.${super.toString()}"
}
