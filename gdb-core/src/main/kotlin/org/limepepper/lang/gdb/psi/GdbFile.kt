package org.limepepper.lang.gdb.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.PsiTreeUtil
import org.limepepper.lang.gdb.GdbFileType
import org.limepepper.lang.gdb.GdbLanguage

/**
 * PSI file representation for GDB scripts
 */
class GdbFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, GdbLanguage) {

    val commands: Collection<GdbCommandStatement>
        get() = PsiTreeUtil.findChildrenOfType(this, GdbCommandStatement::class.java)

    override fun getFileType(): FileType = GdbFileType

    override fun toString(): String = "GDB Commands File"
}
