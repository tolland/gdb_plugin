package org.limepepper.gdb.lang

import com.intellij.lang.Language

/**
 * GDB language definition for IntelliJ Platform
 */
object GdbLanguage : Language("GDBScript") {
    private fun readResolve(): Any = GdbLanguage
}
