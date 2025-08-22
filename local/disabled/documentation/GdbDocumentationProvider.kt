package org.limepepper.lang.gdb.documentation

import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import org.limepepper.lang.gdb.lang.GdbLanguage
import org.limepepper.lang.gdb.parser.GdbTokenTypes

/**
 * Traditional DocumentationProvider for GDB language
 * This is needed for the customElement method in DocumentationManager
 */
class GdbDocumentationProvider : DocumentationProvider {

    private val logger = thisLogger()

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int
    ): PsiElement? {
        logger.info("GdbDocumentationProvider.getCustomDocumentationElement called")
        logger.info("  File: ${file.name}")
        logger.info("  Context element: ${contextElement?.text}")
        logger.info("  Target offset: $targetOffset")

        // Only handle GDB files
        if (file.language != GdbLanguage) {
            logger.info("Not a GDB file, returning null")
            return null
        }

        // Find the element at the target offset
        val elementAtOffset = file.findElementAt(targetOffset)
        logger.info("Element at offset: ${elementAtOffset?.text} (${elementAtOffset?.node?.elementType})")

        // Walk up the PSI tree to find a documentable element
        var current = elementAtOffset
        var depth = 0

        while (current != null && depth < 5) {
            logger.info("Checking element at depth $depth: ${current.text} (${current.node.elementType})")

            if (isDocumentableElement(current)) {
                logger.info("Found documentable element: ${current.text}")
                return current
            }

            current = current.parent
            depth++
        }

        logger.info("No documentable element found")
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

    override fun generateDoc(element: PsiElement, originalElement: PsiElement?): String? {
        logger.info("GdbDocumentationProvider.generateDoc called for: ${element.text}")

        val elementText = element.text.trim()

        return when (element.node.elementType) {
            GdbTokenTypes.COMMAND_EXECUTION,
            GdbTokenTypes.COMMAND_BREAKPOINT,
            GdbTokenTypes.COMMAND_STACK,
            GdbTokenTypes.COMMAND_DATA,
            GdbTokenTypes.COMMAND_CONFIG,
            GdbTokenTypes.COMMAND_USER -> {
                generateCommandDocumentation(elementText)
            }

            GdbTokenTypes.REGISTER -> {
                generateRegisterDocumentation(elementText)
            }

            GdbTokenTypes.HEX_NUMBER -> {
                generateHexNumberDocumentation(elementText)
            }

            else -> {
                // Try to match by text content
                if (GdbCommandDoc.getAllCommands().contains(elementText)) {
                    generateCommandDocumentation(elementText)
                } else if (elementText.startsWith("$")) {
                    generateRegisterDocumentation(elementText)
                } else if (elementText.startsWith("0x")) {
                    generateHexNumberDocumentation(elementText)
                } else {
                    null
                }
            }
        }
    }

    private fun generateCommandDocumentation(commandText: String): String? {
        val doc = GdbCommandDoc.getDocumentation(commandText) ?: return null

        return buildString {
            append("<html><body>")
            append("<h3>").append(escapeHtml(doc.syntax)).append("</h3>")
            append("<p><b>").append(escapeHtml(doc.summary)).append("</b></p>")
            append("<hr>")
            append("<div style='margin-top: 10px;'>")
            append(escapeHtml(doc.description).replace("\n", "<br>"))
            append("</div>")
            append("</body></html>")
        }
    }

    private fun generateRegisterDocumentation(registerText: String): String? {
        val registerName = registerText.removePrefix("$")
        val doc = getRegisterInfo(registerName) ?: return null

        return buildString {
            append("<html><body>")
            append("<h3>Register: ").append(escapeHtml(registerText)).append("</h3>")
            append("<p><b>").append(escapeHtml(doc.summary)).append("</b></p>")
            append("<hr>")
            append("<div style='margin-top: 10px;'>")
            append(escapeHtml(doc.description).replace("\n", "<br>"))
            append("</div>")
            append("</body></html>")
        }
    }

    private fun generateHexNumberDocumentation(hexText: String): String? {
        val value = hexText.removePrefix("0x").toLongOrNull(16) ?: return null

        return buildString {
            append("<html><body>")
            append("<h3>Hexadecimal Value</h3>")
            append("<table border='0' cellpadding='4'>")
            append("<tr><td><b>Hex:</b></td><td>").append(hexText).append("</td></tr>")
            append("<tr><td><b>Decimal:</b></td><td>").append(value).append("</td></tr>")
            append("<tr><td><b>Binary:</b></td><td>").append(value.toString(2)).append("</td></tr>")
            if (value in 32..126) {
                append("<tr><td><b>ASCII:</b></td><td>'").append(value.toInt().toChar())
                    .append("'</td></tr>")
            }
            append("</table>")
            append("</body></html>")
        }
    }

    private fun getRegisterInfo(registerName: String): RegisterDoc? {
        return when (registerName.lowercase()) {
            "rax", "eax", "ax", "al", "ah" -> RegisterDoc(
                "Accumulator Register",
                """
                The accumulator register, used for arithmetic operations and function return values.
                - RAX: 64-bit register
                - EAX: 32-bit portion
                - AX: 16-bit portion
                - AL/AH: 8-bit portions (low/high)
                """.trimIndent()
            )

            "rbx", "ebx", "bx", "bl", "bh" -> RegisterDoc(
                "Base Register",
                """
                General purpose base register, often used as a base pointer for memory access.
                - RBX: 64-bit register
                - EBX: 32-bit portion
                - BX: 16-bit portion
                - BL/BH: 8-bit portions (low/high)
                """.trimIndent()
            )

            "rcx", "ecx", "cx", "cl", "ch" -> RegisterDoc(
                "Counter Register",
                """
                Counter register, used for loop counters and string operations.
                - RCX: 64-bit register
                - ECX: 32-bit portion
                - CX: 16-bit portion
                - CL/CH: 8-bit portions (low/high)
                """.trimIndent()
            )

            "rdx", "edx", "dx", "dl", "dh" -> RegisterDoc(
                "Data Register",
                """
                Data register, used in arithmetic operations and I/O operations.
                - RDX: 64-bit register
                - EDX: 32-bit portion
                - DX: 16-bit portion
                - DL/DH: 8-bit portions (low/high)
                """.trimIndent()
            )

            "rsp", "esp", "sp" -> RegisterDoc(
                "Stack Pointer",
                """
                Points to the top of the current stack frame.
                - RSP: 64-bit stack pointer
                - ESP: 32-bit portion
                - SP: 16-bit portion
                """.trimIndent()
            )

            "rbp", "ebp", "bp" -> RegisterDoc(
                "Base Pointer",
                """
                Points to the base of the current stack frame, used to access local variables and parameters.
                - RBP: 64-bit base pointer
                - EBP: 32-bit portion
                - BP: 16-bit portion
                """.trimIndent()
            )

            "rsi", "esi", "si" -> RegisterDoc(
                "Source Index",
                """
                Source index register, used in string operations and as a general-purpose register.
                - RSI: 64-bit register
                - ESI: 32-bit portion
                - SI: 16-bit portion
                """.trimIndent()
            )

            "rdi", "edi", "di" -> RegisterDoc(
                "Destination Index",
                """
                Destination index register, used in string operations and as a general-purpose register.
                - RDI: 64-bit register
                - EDI: 32-bit portion
                - DI: 16-bit portion
                """.trimIndent()
            )

            "rip", "eip", "ip" -> RegisterDoc(
                "Instruction Pointer",
                """
                Points to the next instruction to be executed.
                - RIP: 64-bit instruction pointer
                - EIP: 32-bit portion (32-bit mode)
                - IP: 16-bit portion (16-bit mode)
                """.trimIndent()
            )

            "pc" -> RegisterDoc(
                "Program Counter",
                """
                Alias for the instruction pointer (RIP/EIP). Points to the next instruction to be executed.
                """.trimIndent()
            )

            else -> null
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
