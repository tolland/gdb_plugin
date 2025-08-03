// src/main/kotlin/org/limepepper/gdb/formatting/GdbBlock.kt
package org.limepepper.gdb.formatting

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import org.limepepper.gdb.GdbLanguage
import org.limepepper.gdb.psi.GdbTypes
import org.limepepper.gdb.parser.GdbTokenTypes

/**
 * Formatting block for GDB language elements
 */
class GdbBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder?,
    private val codeStyleSettings: CodeStyleSettings
) : AbstractBlock(node, wrap, alignment) {

    constructor(
        node: ASTNode,
        wrap: Wrap?,
        indent: Indent,
        alignment: Alignment?,
        codeStyleSettings: CodeStyleSettings
    ) : this(node, wrap, alignment, createSpacingBuilder(codeStyleSettings), codeStyleSettings)

    override fun buildChildren(): List<Block> {
        val blocks = mutableListOf<Block>()
        var child = myNode.firstChildNode

        while (child != null) {
            // Skip whitespace tokens but keep meaningful tokens and elements
            if (child.elementType != TokenType.WHITE_SPACE &&
                child.textLength > 0
            ) {

                val childWrap = getChildWrap(child)
                val childAlignment = getChildAlignment(child)

                val childBlock = GdbBlock(
                    child,
                    childWrap,
                    childAlignment,
                    spacingBuilder,
                    codeStyleSettings
                )
                blocks.add(childBlock)
            }
            child = child.treeNext
        }

        return blocks
    }

    override fun getIndent(): Indent? {
        return when (myNode.elementType) {
            GdbTypes.STATEMENT -> {
                // Indent statements inside user-defined commands
                val parent = myNode.treeParent
                if (isInsideUserDefinedCommand(parent)) {
                    Indent.getNormalIndent()
                } else {
                    Indent.getNoneIndent()
                }
            }

            else -> Indent.getNoneIndent()
        }
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder?.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean {
        return myNode.firstChildNode == null
    }

    private fun getChildWrap(child: ASTNode): Wrap? {
        return when (child.elementType) {
            GdbTypes.STATEMENT -> Wrap.createWrap(WrapType.NONE, false)
            else -> null
        }
    }

    private fun getChildAlignment(child: ASTNode): Alignment? {
        return null // No special alignment for now
    }

    private fun isInsideUserDefinedCommand(node: ASTNode?): Boolean {
        // Check if we're inside a define...end block
        var current = node
        while (current != null) {
            val firstChild = current.firstChildNode
            if (firstChild?.elementType == GdbTokenTypes.COMMAND_USER &&
                firstChild.text == "define"
            ) {
                return true
            }
            current = current.treeParent
        }
        return false
    }

    companion object {
        private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
            return SpacingBuilder(settings, GdbLanguage.INSTANCE)
                // Canonical spacing for special commands

                // x/format address (no space between x and /format, space before address)
                .between(GdbTypes.X_CMD, GdbTypes.FORMAT_SPEC).spacing(0, 0, 0, false, 0)
                .after(GdbTypes.FORMAT_SPEC).spacing(1, 1, 0, true, 0)

                // print/format expr (no space between print and /format, space before expr)
                .between(GdbTypes.PRINT_CMD, GdbTypes.FORMAT_SPEC).spacing(0, 0, 0, false, 0)
                .between(GdbTypes.P_CMD, GdbTypes.FORMAT_SPEC).spacing(0, 0, 0, false, 0)

                // break *address (space between break and *, no space between * and address)
                .between(GdbTypes.BREAK_CMD, GdbTypes.ADDRESS_MARKER).spacing(1, 1, 0, true, 0)
                .between(GdbTypes.B_CMD, GdbTypes.ADDRESS_MARKER).spacing(1, 1, 0, true, 0)
                .between(GdbTypes.ADDRESS_MARKER, GdbTypes.HEX_NUMBER).spacing(0, 0, 0, false, 0)
                .between(GdbTypes.ADDRESS_MARKER, GdbTypes.IDENTIFIER).spacing(0, 0, 0, false, 0)

                // Space after all command types
                .after(GdbTypes.COMMAND_EXECUTION).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.COMMAND_BREAKPOINT).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.COMMAND_STACK).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.COMMAND_DATA).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.COMMAND_CONFIG).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.COMMAND_USER).spacing(1, 1, 0, true, 0)

                // Space after special commands (when no format spec follows)
                .after(GdbTypes.X_CMD).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.PRINT_CMD).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.P_CMD).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.BREAK_CMD).spacing(1, 1, 0, true, 0)
                .after(GdbTypes.B_CMD).spacing(1, 1, 0, true, 0)

                // Space around operators (but not in command contexts)
                .around(GdbTypes.OPERATOR).spacing(1, 1, 0, true, 0)
                .around(GdbTypes.ASSIGNMENT).spacing(1, 1, 0, true, 0)

                // No space around punctuation
                .before(GdbTypes.COMMA).spacing(0, 0, 0, false, 0)
                .after(GdbTypes.COMMA).spacing(1, 1, 0, true, 0)
                .around(GdbTypes.LPAREN).spacing(0, 0, 0, false, 0)
                .around(GdbTypes.RPAREN).spacing(0, 0, 0, false, 0)
                .around(GdbTypes.LBRACKET).spacing(0, 0, 0, false, 0)
                .around(GdbTypes.RBRACKET).spacing(0, 0, 0, false, 0)

                // Statement-level spacing - SIMPLIFIED: only one statement type now
                .between(GdbTypes.STATEMENT, GdbTypes.STATEMENT).spacing(0, 0, 1, true, 1)
                .between(GdbTypes.COMMENT_LINE, GdbTypes.STATEMENT).spacing(0, 0, 1, true, 1)
                .between(GdbTypes.STATEMENT, GdbTypes.COMMENT_LINE).spacing(0, 0, 1, true, 1)

                // Comments should have some space before them if on same line
                .before(GdbTypes.COMMENT).spacing(1, Int.MAX_VALUE, 0, true, 0)
        }
    }
}
