package org.limepepper.gdb.documentation

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.limepepper.gdb.parser.GdbTokenTypes

/**
 * Modern documentation target provider for GDB language elements
 */
class GdbPsiDocProvider : PsiDocumentationTargetProvider {

    private val logger = thisLogger()

    init {
        logger.info("GdbPsiDocProvider initialized")
    }

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? {
        logger.info("GdbPsiDocProvider.documentationTarget called for element: ${element.text}, elementType: ${element.node.elementType}, originalElement: ${originalElement?.text}, file: ${element.containingFile?.name}, elementClass: ${element.javaClass.simpleName}")

        // Check if this element is in a GDB file
        val isGdbFile = element.containingFile?.name?.endsWith(".gdb") == true
        if (!isGdbFile) {
            logger.info("Element is not in a GDB file, skipping")
            return null
        }

        // Log the element hierarchy to understand the structure
        logger.info("Element hierarchy:")
        var current = element
        var depth = 0
        while (current != null && depth < 5) {
            logger.info("  Level $depth: ${current.javaClass.simpleName} - '${current.text}' - ${current.node.elementType}")
            current = current.parent
            depth++
        }

        // Try the element itself first
        if (isDocumentableElement(element)) {
            logger.info("Creating documentation target for element: ${element.text}")
            return GdbDocTarget(element)
        }

        // If the element itself isn't documentable, check its children
        // This handles cases where commands are wrapped in other elements
        for (child in element.children) {
            if (isDocumentableElement(child)) {
                logger.info("Creating documentation target for child element: ${child.text}")
                return GdbDocTarget(child)
            }
        }

        // Also check parent elements
        var parent = element.parent
        var parentDepth = 0
        while (parent != null && parentDepth < 3) {
            if (isDocumentableElement(parent)) {
                logger.info("Creating documentation target for parent element: ${parent.text}")
                return GdbDocTarget(parent)
            }
            parent = parent.parent
            parentDepth++
        }

        logger.info("No documentable element found for: ${element.text} (elementType: ${element.node.elementType})")
        return null
    }

    private fun isDocumentableElement(element: PsiElement): Boolean {
        val isDocumentable = when (element.node.elementType) {
            GdbTokenTypes.COMMAND_EXECUTION,
            GdbTokenTypes.COMMAND_BREAKPOINT,
            GdbTokenTypes.COMMAND_STACK,
            GdbTokenTypes.COMMAND_DATA,
            GdbTokenTypes.COMMAND_CONFIG,
            GdbTokenTypes.COMMAND_USER,
            GdbTokenTypes.REGISTER,
            GdbTokenTypes.HEX_NUMBER -> true
            else -> {
                // Fallback: check if the text matches known GDB commands
                val text = element.text.trim()
                GdbCommandDoc.getAllCommands().contains(text) ||
                text.startsWith("$") || // registers
                text.startsWith("0x") // hex numbers
        }
    }

        if (isDocumentable) {
            logger.info("Element ${element.text} is documentable (elementType: ${element.node.elementType})")
}

        return isDocumentable
    }
}
