// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb_plugin.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.limepepper.gdb_plugin.psi.GdbTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.limepepper.gdb_plugin.psi.*;

public class GdbEqualityExprImpl extends ASTWrapperPsiElement implements GdbEqualityExpr {

  public GdbEqualityExprImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitEqualityExpr(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<GdbRelationalExpr> getRelationalExprList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbRelationalExpr.class);
  }

}
