// src/main/kotlin/org/limepepper/gdb_plugin/psi/GdbNamedElement.kt
package org.limepepper.gdb_plugin.psi

import com.intellij.psi.PsiElement

interface GdbNamedElement : PsiElement {
    fun getName(): String?
    fun setName(name: String): PsiElement
}
