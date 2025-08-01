package org.limepepper.gdb_plugin.documentation

import com.intellij.openapi.project.Project
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.DocumentationTargetProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.limepepper.gdb_plugin.GdbTokenTypes

/**
 * Modern documentation target provider for GDB language elements
 */
class GdbDocumentationProvider : DocumentationTargetProvider {

    override fun documentationTarget(project: Project, element: PsiElement): DocumentationTarget? {
        return if (isDocumentableElement(element)) {
            GdbDocumentationTarget(element)
        } else {
            null
        }
    }

    private fun isDocumentableElement(element: PsiElement): Boolean {
        return when (element.elementType) {
            GdbTokenTypes.COMMAND_EXECUTION,
            GdbTokenTypes.COMMAND_BREAKPOINT,
            GdbTokenTypes.COMMAND_STACK,
            GdbTokenTypes.COMMAND_DATA,
            GdbTokenTypes.COMMAND_CONFIG,
            GdbTokenTypes.COMMAND_USER,
            GdbTokenTypes.REGISTER,
            GdbTokenTypes.HEX_NUMBER -> true
            else -> false
        }
    }

    private fun escapeHtml(text: String): String {
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#x27;")
    }
}

/**
 * Documentation for CPU registers
 */
data class RegisterDoc(
    val summary: String,
    val description: String
)