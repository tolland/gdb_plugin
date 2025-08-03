package org.limepepper.gdb

import com.intellij.lang.Language

/**
 * GDB language definition for IntelliJ Platform
 */
class GdbLanguage private constructor() : Language("GDBScript") {
    companion object {
        @JvmStatic
        val INSTANCE = GdbLanguage()
    }

    override fun getDisplayName() = "GDB Script"
}
