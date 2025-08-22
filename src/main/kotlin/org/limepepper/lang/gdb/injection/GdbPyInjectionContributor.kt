package org.limepepper.lang.gdb.injection

import com.intellij.lang.Language
import com.intellij.lang.injection.general.Injection
import com.intellij.lang.injection.general.LanguageInjectionContributor
import com.intellij.lang.injection.general.SimpleInjection
import com.intellij.psi.PsiElement

class GdbPyInjectionContributor : LanguageInjectionContributor {
    override fun getInjection(context: PsiElement): Injection? {
        if (context is GdbPyBlockPsi) {
            println("got here in injector")
            val lang = Language.findLanguageByID("Python")
            if (lang == null) {
                println("python is null")
                return null
            }
            // Add import gdb to make the module available
            val prefix = "import gdb\n"
            return SimpleInjection(lang, prefix, "", null)
        }
        return null
    }
}
