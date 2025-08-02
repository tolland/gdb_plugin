package org.limepepper.gdb_plugin.documentation

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.util.elementType
import org.limepepper.gdb_plugin.GdbTokenTypes
import org.limepepper.gdb_plugin.psi.GdbArgument
import org.limepepper.gdb_plugin.psi.GdbStatement

/**
 * Modern documentation target provider for GDB language elements
 */
class GdbPsiDocProvider : PsiDocumentationTargetProvider {

    private val logger = thisLogger()

    override fun documentationTarget(element: PsiElement, originalElement: PsiElement?): DocumentationTarget? {
        logger.info("GdbDocumentationProvider.documentationTarget called for element: ${element.text}, elementType: ${element.elementType}, originalElement: ${originalElement?.text}, file: ${element.containingFile?.name}, elementClass: ${element.javaClass.simpleName}")
        
        // Check if this element is in a GDB file
        val isGdbFile = element.containingFile?.name?.endsWith(".gdb") == true
        if (!isGdbFile) {
            logger.info("Element is not in a GDB file, skipping")
            return null
        }
        
        // Handle GDB-specific PSI elements
        when (element) {
            is GdbStatement -> {
                logger.info("Found GdbStatement, checking command")
                val command = element.getCommand()
                if (command != null && isDocumentableElement(command)) {
                    logger.info("Creating documentation target for GdbStatement command: ${command.text}")
                    return GdbDocTarget(command)
                }
            }
            is GdbArgument -> {
                logger.info("Found GdbArgument: ${element.text}")
                val value = element.getValue()
                if (value != null && isDocumentableElement(value)) {
                    logger.info("Creating documentation target for GdbArgument value: ${value.text}")
                    return GdbDocTarget(value)
                }
            }
        }

        // Try the element itself first
        if (isDocumentableElement(element)) {
            logger.info("Creating documentation target for element: ${element.text}")
            return GdbDocTarget(element)
        }

        // If the element itself isn't documentable, check its children
        // This handles cases where commands are wrapped in ASTWrapperPsiElement
        for (child in element.children) {
            if (isDocumentableElement(child)) {
                logger.info("Creating documentation target for child element: ${child.text}")
                return GdbDocTarget(child)
            }
        }

        // Also check the parent element in case we're hovering over a nested element
        element.parent?.let { parent ->
            // Handle parent GDB elements
            when (parent) {
                is GdbStatement -> {
                    logger.info("Parent is GdbStatement, checking command")
                    val command = parent.getCommand()
                    if (command != null && isDocumentableElement(command)) {
                        logger.info("Creating documentation target for parent GdbStatement command: ${command.text}")
                        return GdbDocTarget(command)
                    }
        }
                is GdbArgument -> {
                    logger.info("Parent is GdbArgument: ${parent.text}")
                    val value = parent.getValue()
                    if (value != null && isDocumentableElement(value)) {
                        logger.info("Creating documentation target for parent GdbArgument value: ${value.text}")
                        return GdbDocTarget(value)
                    }
                }
            }

            if (isDocumentableElement(parent)) {
                logger.info("Creating documentation target for parent element: ${parent.text}")
                return GdbDocTarget(parent)
            }

            // Check parent's children too
            for (child in parent.children) {
                if (isDocumentableElement(child)) {
                    logger.info("Creating documentation target for parent's child element: ${child.text}")
                    return GdbDocTarget(child)
                }
            }
        }

        logger.info("No documentable element found for: ${element.text} (elementType: ${element.elementType})")
        return null
    }

    private fun isDocumentableElement(element: PsiElement): Boolean {
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
