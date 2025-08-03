package org.limepepper.gdb.parser

import com.intellij.lang.PsiBuilder
import com.intellij.lang.parser.GeneratedParserUtilBase

class GdbParserUtil : GeneratedParserUtilBase() {

    fun backslash(b: PsiBuilder, level: Int): Boolean {
        return consumeTokenFast(b, "\\\n")
    }

}
