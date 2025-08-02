package org.limepepper.gdb_plugin.documentation

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.DocumentationTargetProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.util.elementType
import org.limepepper.gdb_plugin.GdbTokenTypes

/**
 * Documentation target provider that works with file offsets
 */
class GdbDocProvider : DocumentationTargetProvider {

    private val logger = thisLogger()

    override fun documentationTargets(file: PsiFile, offset: Int): List<DocumentationTarget> {
        logger.info("GdbDocumentationTargetProvider.documentationTargets called for file: ${file.name}, offset: $offset")
        
        // Check if this is a GDB file
        val isGdbFile = file.name.endsWith(".gdb") || file.fileType.name == "GDB Script"
        if (!isGdbFile) {
            logger.info("File ${file.name} is not a GDB file, skipping")
            return emptyList()
        }
        
        // Find element at offset
        val element = file.findElementAt(offset)
        if (element == null) {
            logger.info("No element found at offset $offset")
            return emptyList()
        }
        
        logger.info("Found element at offset: ${element.text}, elementType: ${element.elementType}")
        
        // Check if this element or any of its ancestors/children are documentable
        val documentableElement = findDocumentableElement(element)
        return if (documentableElement != null) {
            logger.info("Creating documentation target for element: ${documentableElement.text}")
            listOf(GdbDocTarget(documentableElement))
        } else {
            logger.info("No documentable element found for offset $offset")
            emptyList()
        }
    }

    private fun findDocumentableElement(element: com.intellij.psi.PsiElement): com.intellij.psi.PsiElement? {
        // Check the element itself
        if (isDocumentableElement(element)) {
            return element
        }
        
        // Check children
        for (child in element.children) {
            if (isDocumentableElement(child)) {
                return child
            }
        }
        
        // Check parent and siblings
        val parent = element.parent
        if (parent != null) {
            if (isDocumentableElement(parent)) {
                return parent
            }
            
            // Check siblings
            for (child in parent.children) {
                if (isDocumentableElement(child)) {
                    return child
                }
            }
        }
        
        return null
    }

    private fun isDocumentableElement(element: com.intellij.psi.PsiElement): Boolean {
        val isDocumentable = when (element.elementType) {
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
            logger.info("Element ${element.text} is documentable (elementType: ${element.elementType})")
        }
        
        return isDocumentable
    }
}
