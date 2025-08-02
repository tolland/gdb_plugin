
package org.limepepper.gdb_plugin.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.limepepper.gdb_plugin.GdbTokenTypes

/**
 * PSI element for GDB statements (commands with arguments)
 */
class GdbStatement(node: ASTNode) : GdbPsiElement(node) {

    /**
     * Get the command element (first child that's a command token)
     */
    fun getCommand(): PsiElement? {
        return children.firstOrNull { element ->
            element.node?.elementType in setOf(
                GdbTokenTypes.COMMAND_EXECUTION,
                GdbTokenTypes.COMMAND_BREAKPOINT,
                GdbTokenTypes.COMMAND_STACK,
                GdbTokenTypes.COMMAND_DATA,
                GdbTokenTypes.COMMAND_CONFIG,
                GdbTokenTypes.COMMAND_USER
            )
        }
    }

    /**
     * Get all argument elements
     */
    fun getArguments(): List<GdbArgument> {
        return children.filterIsInstance<GdbArgument>()
    }

    override fun toString(): String = "GdbStatement"
}
