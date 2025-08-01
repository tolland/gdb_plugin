package org.limepepper.gdb_plugin

import com.intellij.lang.Language

/**
 * GDB language definition for IntelliJ Platform
 */
class GdbLanguage private constructor() : Language("GDB") {
    companion object {
        @JvmStatic
        val INSTANCE = GdbLanguage()
    }

    override fun getDisplayName() = "GDB Script"
}