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

public class GdbBreakpointImpl extends ASTWrapperPsiElement implements GdbBreakpoint {

  public GdbBreakpointImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitBreakpoint(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public GdbCondition getCondition() {
    return findChildByClass(GdbCondition.class);
  }

  @Override
  @NotNull
  public PsiElement getIdentifier() {
    return findNotNullChildByType(IDENTIFIER);
  }

}
