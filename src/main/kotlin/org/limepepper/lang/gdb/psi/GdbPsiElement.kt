package org.limepepper.lang.gdb.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode

/**
 * Base class for GDB PSI elements
 */
open class GdbPsiElement(node: ASTNode) : ASTWrapperPsiElement(node)
