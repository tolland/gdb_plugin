package org.limepepper.gdb_plugin.formatting

import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import org.limepepper.gdb_plugin.GdbTokenTypes
import org.limepepper.gdb_plugin.parser.GdbParser

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
            if (child.elementType != TokenType.WHITE_SPACE && 
                child.textLength > 0) {
                
                val childWrap = getChildWrap(child)
                val childAlignment = getChildAlignment(child)
                val childIndent = getChildIndent(child)
                
                blocks.add(
                    GdbBlock(
                        child,
                        childWrap,
                        childAlignment,
                        spacingBuilder,
                        codeStyleSettings
                    ).apply {
                        indent = childIndent
                    }
                )
            }
            child = child.treeNext
        }
        
        return blocks
    }

    override fun getIndent(): Indent? {
        return when (myNode.elementType) {
            GdbParser.STATEMENT -> {
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
            GdbParser.STATEMENT -> Wrap.createWrap(WrapType.NONE, false)
            else -> null
        }
    }
    
    private fun getChildAlignment(child: ASTNode): Alignment? {
        return null // No special alignment for now
    }
    
    private fun getChildIndent(child: ASTNode): Indent {
        return when (child.elementType) {
            GdbParser.STATEMENT -> {
                if (isInsideUserDefinedCommand(myNode)) {
                    Indent.getNormalIndent()
                } else {
                    Indent.getNoneIndent()
                }
            }
            else -> Indent.getNoneIndent()
        }
    }
    
    private fun isInsideUserDefinedCommand(node: ASTNode?): Boolean {
        // Check if we're inside a define...end block
        var current = node
        while (current != null) {
            val firstChild = current.firstChildNode
            if (firstChild?.elementType == GdbTokenTypes.COMMAND_USER && 
                firstChild.text == "define") {
                return true
            }
            current = current.treeParent
        }
        return false
    }

    companion object {
        private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
            return SpacingBuilder(settings, GdbLanguage.INSTANCE)
                // Space after commands
                .after(GdbTokenTypes.COMMAND_EXECUTION).spacing(1, 1, 0, true, 0)
                .after(GdbTokenTypes.COMMAND_BREAKPOINT).spacing(1, 1, 0, true, 0)
                .after(GdbTokenTypes.COMMAND_STACK).spacing(1, 1, 0, true, 0)
                .after(GdbTokenTypes.COMMAND_DATA).spacing(1, 1, 0, true, 0)
                .after(GdbTokenTypes.COMMAND_CONFIG).spacing(1, 1, 0, true, 0)
                .after(GdbTokenTypes.COMMAND_USER).spacing(1, 1, 0, true, 0)
                
                // Space around operators
                .around(GdbTokenTypes.OPERATOR).spacing(1, 1, 0, true, 0)
                .around(GdbTokenTypes.ASSIGNMENT).spacing(1, 1, 0, true, 0)
                
                // No space around punctuation
                .before(GdbTokenTypes.COMMA).spacing(0, 0, 0, false, 0)
                .after(GdbTokenTypes.COMMA).spacing(1, 1, 0, true, 0)
                .around(GdbTokenTypes.LPAREN).spacing(0, 0, 0, false, 0)
                .around(GdbTokenTypes.RPAREN).spacing(0, 0, 0, false, 0)
                .around(GdbTokenTypes.LBRACKET).spacing(0, 0, 0, false, 0)
                .around(GdbTokenTypes.RBRACKET).spacing(0, 0, 0, false, 0)
                
                // Newline handling
                .before(GdbTokenTypes.COMMENT).spacing(1, Int.MAX_VALUE, 0, true, 0)
                .after(GdbTokenTypes.NEWLINE).spacing(0, 0, 1, true, 0)
        }
    }
}

// Import required for SpacingBuilder
import org.limepepper.gdb_plugin.GdbLanguage