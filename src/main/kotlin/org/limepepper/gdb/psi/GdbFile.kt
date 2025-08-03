package org.limepepper.gdb.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.limepepper.gdb.GdbFileType
import org.limepepper.gdb.GdbLanguage

/**
 * PSI file representation for GDB scripts
 */
class GdbFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, GdbLanguage.INSTANCE) {
    
    override fun getFileType(): FileType = GdbFileType
    
    override fun toString(): String = "GDB File"
}
