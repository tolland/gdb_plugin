package org.limepepper.lang.gdb.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import org.limepepper.lang.gdb.psi.GdbCommandStatement
import org.limepepper.lang.gdb.psi.GdbFile
import org.limepepper.lang.gdb.GdbFileType


class GdbUtil {

    /**
     * Searches the entire project for Simple language files with instances of the Simple property with the given key.
     *
     * @param project current project
     * @param key     to check
     * @return matching properties
     */
    fun findProperties(project: Project, key: String): List<GdbCommandStatement> {
        val result = mutableListOf<GdbCommandStatement>()
        val virtualFiles = FileTypeIndex.getFiles(GdbFileType, GlobalSearchScope.allScope(project))
        for (virtualFile in virtualFiles) {
            val gdbFile = PsiManager.getInstance(project).findFile(virtualFile) as? GdbFile
            if (gdbFile != null) {
                val statements =
                    PsiTreeUtil.getChildrenOfType(gdbFile, GdbCommandStatement::class.java)
                if (statements != null) {
                    for (statement in statements) {
                        if (statement.text.startsWith(key)) {
                            result.add(statement)
                        }
                    }
                }
            }
        }
        return result
    }


    fun findSimpleProperties(project: Project): List<GdbCommandStatement> {
        val result = mutableListOf<GdbCommandStatement>()
        val virtualFiles = FileTypeIndex.getFiles(GdbFileType, GlobalSearchScope.allScope(project))
        for (virtualFile in virtualFiles) {
            val simpleFile = PsiManager.getInstance(project).findFile(virtualFile) as? GdbFile
            if (simpleFile != null) {
                val properties =
                    PsiTreeUtil.getChildrenOfType(simpleFile, GdbCommandStatement::class.java)
                if (properties != null) {
                    for (property in properties) {
                        if (property != null) {
                            result.add(property)
                        }
                    }
                }
            }
        }
        return result
    }


    /**
     * Attempts to collect any comment elements
     */
    fun findDocumentationComment(statement: GdbCommandStatement): String {
        val result = mutableListOf<String>()
        var element = statement.prevSibling
        while (element is PsiComment || element is PsiWhiteSpace) {
            if (element is PsiComment) {
                val commentText = element.text.replaceFirst("[!# ]+".toRegex(), "")
                result.add(commentText)
            }
            element = element.prevSibling
        }
        return result.asReversed().joinToString("\n ")
    }

}
