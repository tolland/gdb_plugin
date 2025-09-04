// src/main/kotlin/org/limepepper/gdb/psi/GdbTokenType.kt

package org.limepepper.lang.gdb.psi

import com.intellij.psi.tree.IElementType
import org.limepepper.lang.gdb.GdbLanguage

/**
 * Token type for GDB language
 */
class GdbTokenType(debugName: String) : IElementType(debugName, GdbLanguage) {
    override fun toString(): String = "GdbTokenType.${super.toString()}"
}
