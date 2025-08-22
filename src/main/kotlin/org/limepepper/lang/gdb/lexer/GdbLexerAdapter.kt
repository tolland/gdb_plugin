package org.limepepper.lang.gdb.lexer

import com.intellij.lexer.FlexAdapter
import com.intellij.lexer.MergingLexerAdapter
import com.intellij.psi.tree.TokenSet
import org.limepepper.lang.gdb.psi.GdbTypes

/**
 * Adapter for the JFlex-generated GDB lexer
 */
class GdbLexerAdapter : MergingLexerAdapter(
    FlexAdapter(GdbLexer(null)),                 // your generated JFlex lexer
    TokenSet.create(GdbTypes.DOC_BLOCK_LINE)
)
