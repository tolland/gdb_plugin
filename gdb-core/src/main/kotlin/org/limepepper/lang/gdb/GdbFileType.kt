package org.limepepper.lang.gdb

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * File type definition for GDB script files
 */
@Suppress("CanBeSingleton")
object GdbFileType : LanguageFileType(GdbLanguage) {

    override fun getName() = "GDBScript"

    override fun getDescription() = "GDB debugger script file"

    override fun getDefaultExtension() = "gdb"

    override fun getIcon(): Icon? = AllIcons.Actions.StartDebugger
}
