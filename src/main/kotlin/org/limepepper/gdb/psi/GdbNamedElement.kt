// src/main/kotlin/org/limepepper/gdb/psi/GdbNamedElement.kt
package org.limepepper.gdb.psi

import com.intellij.psi.PsiNameIdentifierOwner
import com.intellij.psi.PsiNamedElement

interface GdbNamedElement : PsiNamedElement, PsiNameIdentifierOwner
