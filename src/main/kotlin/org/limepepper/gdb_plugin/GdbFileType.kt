package org.limepepper.gdb_plugin

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

/**
 * File type definition for GDB script files
 */
object GdbFileType : LanguageFileType(GdbLanguage.INSTANCE) {
    
    override fun getName() = "GDB Script"
    
    override fun getDescription() = "GDB debugger script file"
    
    override fun getDefaultExtension() = "gdb"
    
    override fun getIcon(): Icon? = null // TODO: Add custom icon in future phases
}