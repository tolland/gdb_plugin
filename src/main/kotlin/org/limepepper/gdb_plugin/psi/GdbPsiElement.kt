package org.limepepper.gdb_plugin.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

/**
 * Base class for GDB PSI elements
 */
open class GdbPsiElement(node: ASTNode) : ASTWrapperPsiElement(node)
