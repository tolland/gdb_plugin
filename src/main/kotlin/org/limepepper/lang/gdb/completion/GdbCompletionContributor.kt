package org.limepepper.lang.gdb.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import org.limepepper.gdb.parser.GdbTokenTypes
import org.limepepper.gdb.psi.GdbTypes

class GdbCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(GdbTokenTypes.COMMAND_BREAKPOINT),
            KeywordCompletionProvider
        )
    }

    private object KeywordCompletionProvider : CompletionProvider<CompletionParameters>() {
        private val keywords = listOf(
            "include",
            "define",
            "undefine",
            "override",
            "export",
            "private",
            "vpath",
            "ifeq",
            "ifneq",
            "ifdef",
            "ifndef",
            "break"
        )

        override fun addCompletions(
            parameters: CompletionParameters,
            context: ProcessingContext,
            resultSet: CompletionResultSet
        ) {
            resultSet.addAllElements(keywords.map { LookupElementBuilder.create(it) })
        }
    }
}
