package org.limepepper.gdb.psi.impl


/**
 * Utility methods for generated PSI elements
 */
object GdbPsiUtil {

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
