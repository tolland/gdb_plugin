package org.limepepper.lang.gdb.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet
import org.limepepper.lang.gdb.psi.GdbTypes

/**
 * Lexer adapter that merges consecutive PYTHON_BLOCK_LINE and DOC_BLOCK_LINE tokens
 */
class GdbLexerAdapter : MergingLexerAdapter(
    FlexAdapter(GdbLexer(null)),
    TokenSet.create(
        GdbTypes.DOC_BLOCK_LINE,
        GdbTypes.PYTHON_BLOCK_LINE
    )
)
