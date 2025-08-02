package org.limepepper.gdb_plugin.documentation

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.limepepper.gdb_plugin.GdbTokenTypes

/**
 * Modern documentation target provider for GDB language elements
 */
class GdbDocumentationProvider : PsiDocumentationTargetProvider {

    private val logger = thisLogger()

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? {
        logger.info("GdbDocumentationProvider.documentationTarget called for element: ${element.text}, elementType: ${element.elementType}, originalElement: ${originalElement?.text}")
        
        return if (isDocumentableElement(element)) {
            logger.info("Creating documentation target for element: ${element.text}")
            GdbDocumentationTarget(element)
        } else {
            logger.info("Element ${element.text} is not documentable (elementType: ${element.elementType})")
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

}
