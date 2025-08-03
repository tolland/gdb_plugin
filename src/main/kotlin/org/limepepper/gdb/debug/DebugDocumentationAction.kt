package org.limepepper.gdb.debug

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider

/**
 * Debug action to list all loaded PsiDocumentationTargetProviders
 */
class DebugDocumentationAction : AnAction() {
    
    private val logger = thisLogger()

    override fun update(e: AnActionEvent) {
        e.presentation.text = "Debug Documentation Providers"
        e.presentation.description = "this is a description"
        e.presentation.icon = null // or your icon if needed
    }

    override fun actionPerformed(e: AnActionEvent) {
        logger.info("=== Debug Documentation Providers ===")
        
        try {
            val extensionPoint = ApplicationManager.getApplication()
                .extensionArea
                .getExtensionPoint(ExtensionPointName.create<PsiDocumentationTargetProvider>("com.intellij.platform.backend.documentation.psiTargetProvider"))
            
            logger.info("Found ${extensionPoint.extensions.size} PsiDocumentationTargetProvider extensions:")
            
            extensionPoint.extensions.forEachIndexed { index, provider ->
                logger.info("[$index] ${provider::class.qualifiedName}")
                logger.info("    Class: ${provider::class.java}")
                logger.info("    Plugin: ${provider::class.java.classLoader}")
                
                if (provider::class.qualifiedName?.contains("gdb", ignoreCase = true) == true) {
                    logger.info("    *** GDB PROVIDER FOUND! ***")
                }
            }
            
        } catch (e: Exception) {
            logger.error("Error listing documentation providers", e)
        }
        
        logger.info("=== End Debug Documentation Providers ===")
    }
}
