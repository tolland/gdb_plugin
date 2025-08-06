package org.limepepper.gdb.debug

import com.intellij.codeInsight.documentation.DocumentationManager
import com.intellij.lang.LanguageDocumentation
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.platform.backend.documentation.PsiDocumentationTargetProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.util.PsiTreeUtil
import org.limepepper.gdb.lang.GdbLanguage

/**
 * Debug action to trace documentation system behavior
 */
class DebugDocumentationAction : AnAction() {
    
    private val logger = thisLogger()
    
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        logger.info("=== GDB Documentation Debug Session ===")
        
        // 1. Check if GDB language has a documentation provider
        debugLanguageDocumentation(project)
        
        // 2. Check all registered PsiDocumentationTargetProviders
        debugPsiDocumentationTargetProviders(project)
        
        // 3. Check current editor state
        debugCurrentEditorState(project)
        
        logger.info("=== End Debug Session ===")
    }
    
    private fun debugLanguageDocumentation(project: Project) {
        logger.info("--- Language Documentation Check ---")
        
        val gdbLanguage = GdbLanguage
        logger.info("GDB Language: $gdbLanguage")
        logger.info("GDB Language ID: ${gdbLanguage.id}")
        logger.info("GDB Language Display Name: ${gdbLanguage.displayName}")
        
        val docProvider = LanguageDocumentation.INSTANCE.forLanguage(gdbLanguage)
        logger.info("Documentation Provider for GDB Language: $docProvider")
        
        if (docProvider != null) {
            logger.info("Documentation Provider Class: ${docProvider.javaClass.name}")
            logger.info("Documentation Provider toString: $docProvider")
        } else {
            logger.warn("NO DOCUMENTATION PROVIDER FOUND FOR GDB LANGUAGE!")
        }
    }
    
    private fun debugPsiDocumentationTargetProviders(project: Project) {
        logger.info("--- PsiDocumentationTargetProviders Check ---")
        
        val providers = PsiDocumentationTargetProvider.EP_NAME.extensionList
        logger.info("Total PsiDocumentationTargetProviders: ${providers.size}")
        
        providers.forEachIndexed { index, provider ->
            logger.info("Provider $index: ${provider.javaClass.name}")
            logger.info("  - toString: $provider")
        }
        
        // Check if our provider is registered
        val gdbProvider = providers.find { it.javaClass.name.contains("GdbPsiDocProvider") }
        if (gdbProvider != null) {
            logger.info("Found GDB PsiDocumentationTargetProvider: ${gdbProvider.javaClass.name}")
        } else {
            logger.warn("GDB PsiDocumentationTargetProvider NOT FOUND in registered providers!")
        }
    }
    
    private fun debugCurrentEditorState(project: Project) {
        logger.info("--- Current Editor State Check ---")
        
        val editor = com.intellij.openapi.editor.EditorFactory.getInstance().allEditors.firstOrNull()
        if (editor == null) {
            logger.info("No active editor found")
            return
        }
        
        logger.info("Active Editor: ${editor.javaClass.name}")
        logger.info("Editor File: ${editor.virtualFile?.name}")
        logger.info("Editor Language: ${editor.virtualFile?.fileType?.name}")
        
        val psiFile = PsiManager.getInstance(project).findFile(editor.virtualFile)
        if (psiFile != null) {
            logger.info("PSI File: ${psiFile.javaClass.name}")
            logger.info("PSI File Language: ${psiFile.language}")
            logger.info("PSI File Name: ${psiFile.name}")
            
            // Check if it's a GDB file
            if (psiFile.language == GdbLanguage) {
                logger.info("This IS a GDB file!")
                debugGdbFileElements(psiFile, editor, project)
            } else {
                logger.info("This is NOT a GDB file (language: ${psiFile.language})")
            }
        } else {
            logger.info("No PSI file found for editor")
        }
    }
    
    private fun debugGdbFileElements(psiFile: PsiFile, editor: com.intellij.openapi.editor.Editor, project: Project) {
        logger.info("--- GDB File Elements Debug ---")
        
        val offset = editor.caretModel.offset
        logger.info("Current caret offset: $offset")
        
        val elementAtOffset = psiFile.findElementAt(offset)
        logger.info("Element at offset: ${elementAtOffset?.javaClass?.simpleName}")
        logger.info("Element text: '${elementAtOffset?.text}'")
        logger.info("Element type: ${elementAtOffset?.node?.elementType}")
        
        // Find the top-level element containing the caret
        val contextElement = DocumentationManager.getContextElement(editor, psiFile)
        logger.info("Context element: ${contextElement?.javaClass?.simpleName}")
        logger.info("Context element text: '${contextElement?.text}'")
        logger.info("Context element type: ${contextElement?.node?.elementType}")
        
        // Try to find target element using DocumentationManager
        val targetElement = DocumentationManager.getInstance(project).findTargetElement(editor, psiFile)
        logger.info("Target element: ${targetElement?.javaClass?.simpleName}")
        logger.info("Target element text: '${targetElement?.text}'")
        logger.info("Target element type: ${targetElement?.node?.elementType}")
        
        // Walk up the PSI tree to find documentable elements
        debugPsiTreeWalk(elementAtOffset ?: contextElement)
    }
    
    private fun debugPsiTreeWalk(element: PsiElement?) {
        logger.info("--- PSI Tree Walk ---")
        
        var current = element
        var depth = 0
        
        while (current != null && depth < 10) {
            logger.info("Level $depth:")
            logger.info("  Class: ${current.javaClass.simpleName}")
            logger.info("  Type: ${current.node.elementType}")
            logger.info("  Text: '${current.text}'")
            logger.info("  Language: ${current.language}")
            logger.info("  Children count: ${current.children.size}")
            
            // Check if this element would be documentable by our provider
            val isDocumentable = isElementDocumentable(current)
            logger.info("  Would be documentable: $isDocumentable")
            
            current = current.parent
            depth++
        }
    }
    
    private fun isElementDocumentable(element: PsiElement): Boolean {
        val text = element.text.trim()
        
        // Check if it matches known GDB commands
        val knownCommands = setOf(
            "set", "print", "break", "run", "continue", "step", "next", "info",
            "watch", "catch", "delete", "enable", "disable", "backtrace", "frame",
            "up", "down", "finish", "until", "kill", "quit", "start", "disassemble"
        )
        
        return knownCommands.contains(text) ||
               text.startsWith("$") || // registers
               text.startsWith("0x") || // hex numbers
               text.matches(Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")) // identifiers
    }
}
