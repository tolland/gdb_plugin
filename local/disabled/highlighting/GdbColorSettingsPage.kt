package org.limepepper.gdb.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.limepepper.gdb.lang.GdbFileType
import javax.swing.Icon

/**
 * Color settings page for configuring GDB syntax highlighting colors
 */
class GdbColorSettingsPage : ColorSettingsPage {

    companion object {
        private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("Comment", GdbSyntaxHighlighter.COMMENT),
            AttributesDescriptor("Execution Command", GdbSyntaxHighlighter.COMMAND_EXECUTION),
            AttributesDescriptor("Breakpoint Command", GdbSyntaxHighlighter.COMMAND_BREAKPOINT),
            AttributesDescriptor("Stack Command", GdbSyntaxHighlighter.COMMAND_STACK),
            AttributesDescriptor("Data Command", GdbSyntaxHighlighter.COMMAND_DATA),
            AttributesDescriptor("Configuration Command", GdbSyntaxHighlighter.COMMAND_CONFIG),
            AttributesDescriptor("User Command", GdbSyntaxHighlighter.COMMAND_USER),
            AttributesDescriptor("General Command", GdbSyntaxHighlighter.COMMAND_GENERAL),
            AttributesDescriptor("Number", GdbSyntaxHighlighter.NUMBER),
            AttributesDescriptor("Hex Number", GdbSyntaxHighlighter.HEX_NUMBER),
            AttributesDescriptor("String", GdbSyntaxHighlighter.STRING),
            AttributesDescriptor("Register", GdbSyntaxHighlighter.REGISTER),
            AttributesDescriptor("Identifier", GdbSyntaxHighlighter.IDENTIFIER),
            AttributesDescriptor("Operator", GdbSyntaxHighlighter.OPERATOR),
            AttributesDescriptor("Punctuation", GdbSyntaxHighlighter.PUNCTUATION),
            AttributesDescriptor("Condition Keyword", GdbSyntaxHighlighter.CONDITION_IF),
            AttributesDescriptor("Bad Character", GdbSyntaxHighlighter.BAD_CHARACTER)
        )
    }

    override fun getIcon(): Icon? = GdbFileType.icon

    override fun getHighlighter(): SyntaxHighlighter = GdbSyntaxHighlighter()

    override fun getDemoText(): String = """
        # GDB Script Example - Syntax Highlighting Demo
        # This demonstrates various GDB command categories and syntax elements

        # Configuration commands
        set confirm off
        set pagination off
        set print pretty on

        # Execution control commands
        run arg1 "argument with spaces"
        start
        continue

        # Breakpoint commands
        break main
        break function_name if argc > 1
        break *0x400000
        watch variable_name
        catch syscall

        # Stack navigation commands
        backtrace
        frame 0
        up
        down

        # Data examination commands
        print variable_name
        print ${'$'}rax
        x/10i ${'$'}pc
        x/10x ${'$'}sp
        info registers
        info breakpoints
        disassemble main

        # User-defined commands
        define mycommand
          print "Custom command executed"
          info stack
        end

        # Complex expressions and conditions
        if argc == 2
          set var counter = 42
          print *pointer->member
        end

        # Memory operations
        x/10x 0x401000
        set *(int*)0x400000 = 0xdeadbeef

        quit
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? = null

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = DESCRIPTORS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = "GDB Script"
}
