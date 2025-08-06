// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class GdbVisitor extends PsiElementVisitor {

  public void visitCommand(@NotNull GdbCommand o) {
    visitPsiElement(o);
  }

  public void visitCommandStatement(@NotNull GdbCommandStatement o) {
    visitPsiElement(o);
  }

  public void visitDefineBlock(@NotNull GdbDefineBlock o) {
    visitPsiElement(o);
  }

  public void visitDocCommentBlock(@NotNull GdbDocCommentBlock o) {
    visitPsiElement(o);
  }

  public void visitDocumentedCommand(@NotNull GdbDocumentedCommand o) {
    visitPsiElement(o);
  }

  public void visitFloatingComment(@NotNull GdbFloatingComment o) {
    visitPsiElement(o);
  }

  public void visitSimpleCommand(@NotNull GdbSimpleCommand o) {
    visitPsiElement(o);
  }

  public void visitValueStatement(@NotNull GdbValueStatement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
