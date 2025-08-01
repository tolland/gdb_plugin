package org.limepepper.gdb_plugin.lexer

import com.intellij.lexer.FlexAdapter

/**
 * Adapter for the JFlex-generated GDB lexer
 */
class GdbLexerAdapter : FlexAdapter(GdbLexer(null))