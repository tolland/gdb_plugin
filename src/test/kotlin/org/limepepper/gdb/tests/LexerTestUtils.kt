package org.limepepper.gdb.tests

import com.intellij.psi.tree.IElementType
import org.limepepper.gdb.lexer.GdbLexer

data class TokenInfo(
    val type: IElementType,
    val text: String,
    val start: Int,
    val end: Int
)

object LexerTestUtils {
    fun tokenize(content: String, lexer: GdbLexer = GdbLexer()): List<TokenInfo> {
        val tokens = mutableListOf<TokenInfo>()
        lexer.reset(content, 0, content.length, 0)
        var token: IElementType? = lexer.advance()
        while (token != null) {
            val text = content.substring(lexer.tokenStart, lexer.tokenEnd)
            tokens.add(TokenInfo(token, text, lexer.tokenStart, lexer.tokenEnd))
            token = lexer.advance()
        }
        return tokens
    }
}


