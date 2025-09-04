package org.limepepper.lang.gdb.psi.impl

import org.limepepper.lang.gdb.psi.GdbCommandStatement


/**
 * Utility methods for generated PSI elements
 */
object GdbPsiImplUtil {

    /**
     * Get the command name from a statement
     */
    @JvmStatic
    fun getKey(statement: GdbCommandStatement): String? {
        return "test2";
    }

//    @JvmStatic
//    fun getName(element: MakefileVariable): String {
//        return element.text
//    }
//
//    @JvmStatic
//    fun setName(element: MakefileVariable, newName: String): PsiElement {
//        val identifierNode = element.node.firstChildNode
//        if (identifierNode != null) {
//            val variable = MakefileElementFactory.createVariable(element.project, newName)
//            val newIdentifierNode = variable.firstChild.node
//            element.node.replaceChild(identifierNode, newIdentifierNode)
//        }
//        return element
//    }

//    /**
//     * Get the command name from a statement
//     */
//    @JvmStatic
//    fun getCommandName(statement: GdbStatement): String? {
//        val commandWithFormat = statement.commandWithFormat
//
//        // Look for command tokens in the command_with_format element
//        return commandWithFormat.children.firstOrNull { element ->
//            when (element.node?.elementType) {
//                GdbTypes.COMMAND_EXECUTION,
//                GdbTypes.COMMAND_BREAKPOINT,
//                GdbTypes.COMMAND_STACK,
//                GdbTypes.COMMAND_DATA,
//                GdbTypes.COMMAND_CONFIG,
//                GdbTypes.COMMAND_USER,
//                GdbTypes.X_CMD,
//                GdbTypes.PRINT_CMD,
//                GdbTypes.P_CMD,
//                GdbTypes.BREAK_CMD,
//                GdbTypes.B_CMD -> true
//                else -> false
//            }
//        }?.text
//    }
//
//    /**
//     * Get the format specification from a statement (e.g., "/x", "/10i")
//     */
//    @JvmStatic
//    fun getFormatSpec(statement: GdbStatement): String? {
//        val commandWithFormat = statement.commandWithFormat
//
//        // Look for FORMAT_SPEC token
//        return commandWithFormat.children.firstOrNull { element ->
//            element.node?.elementType == GdbTypes.FORMAT_SPEC
//        }?.text
//    }
}
