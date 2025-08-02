package org.limepepper.gdb_plugin.documentation

import com.intellij.model.Pointer
import com.intellij.platform.backend.documentation.DocumentationResult
import com.intellij.platform.backend.documentation.DocumentationTarget
import com.intellij.platform.backend.presentation.TargetPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.createSmartPointer
import com.intellij.psi.util.elementType
import org.limepepper.gdb_plugin.GdbTokenTypes

/**
 * Documentation target for GDB language elements using the modern API
 */
@Suppress("UnstableApiUsage")
class GdbDocTarget(private val element: PsiElement) : DocumentationTarget {

    override fun createPointer(): Pointer<out DocumentationTarget> {
        // A Pointer allows the IDE to restore this target later if needed.
        return Pointer.hardPointer(this)
    }

    private val elementPointer: Pointer<PsiElement> = element.createSmartPointer()

    override fun computePresentation(): TargetPresentation {
        val elementText = element.text
        return when (element.elementType) {
            GdbTokenTypes.COMMAND_EXECUTION,
            GdbTokenTypes.COMMAND_BREAKPOINT,
            GdbTokenTypes.COMMAND_STACK,
            GdbTokenTypes.COMMAND_DATA,
            GdbTokenTypes.COMMAND_CONFIG,
            GdbTokenTypes.COMMAND_USER -> {
                val doc = GdbCommandDoc.getDocumentation(elementText)
                TargetPresentation.builder("GDB Command: $elementText")
                    .presentation()
            }
            
            GdbTokenTypes.REGISTER -> {
                TargetPresentation.builder("Register: $elementText")
                    .presentation()
            }
            
            GdbTokenTypes.HEX_NUMBER -> {
                TargetPresentation.builder("Hex: $elementText")
                    .presentation()
            }
            
            else -> {
                TargetPresentation.builder(elementText)
                    .presentation()
            }
        }
    }

    override fun computeDocumentation(): DocumentationResult? {
        val currentElement = elementPointer.dereference() ?: return null
        val elementText = currentElement.text
        
        val htmlContent = when (currentElement.elementType) {
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
            
            else -> null
        } ?: return null
        
        return DocumentationResult.documentation(htmlContent)
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
                append("<tr><td><b>ASCII:</b></td><td>'").append(value.toInt().toChar()).append("'</td></tr>")
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GdbDocTarget) return false
        
        val thisElement = elementPointer.dereference()
        val otherElement = other.elementPointer.dereference()
        
        return thisElement == otherElement
    }

    override fun hashCode(): Int {
        return elementPointer.dereference()?.hashCode() ?: 0
    }
}

/**
 * Documentation for CPU registers
 */
data class RegisterDoc(
    val summary: String,
    val description: String
)
