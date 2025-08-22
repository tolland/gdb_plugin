package org.limepepper.gdb.tests.utils

import com.intellij.testFramework.ParsingTestCase.toParseTreeText
import java.io.IOException

object ParserTestUtils {
    private val DEBUG: Boolean =
        (System.getProperty("LEXER_DEBUG")?.equals("true", ignoreCase = true) == true) ||
            (System.getenv("LEXER_DEBUG")?.equals("true", ignoreCase = true) == true)

//    fun debugParse(text: String) {
//        val psiFile = // ... parse the text
//        val visitor = object : PsiRecursiveElementVisitor() {
//            override fun visitElement(element: PsiElement) {
//                if (element is PsiErrorElement) {
//                    println("ERROR at ${element.textRange}: ${element.errorDescription}")
//                    println("  Text: '${element.text}'")
//                }
//                super.visitElement(element)
//            }
//        }
//        psiFile.accept(visitor)
//    }
}
