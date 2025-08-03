// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.limepepper.gdb.psi.GdbTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.limepepper.gdb.psi.*;

public class GdbExpressionImpl extends ASTWrapperPsiElement implements GdbExpression {

  public GdbExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitExpression(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public GdbValue getValue() {
    return findNotNullChildByClass(GdbValue.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
