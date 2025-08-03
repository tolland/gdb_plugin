// src/main/kotlin/org/limepepper/gdb/psi/GdbNamedElement.kt
package org.limepepper.gdb.psi

import com.intellij.psi.PsiElement

interface GdbNamedElement : PsiElement {
    fun getName(): String?
    fun setName(name: String): PsiElement
}
