// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.limepepper.gdb.psi.GdbTypes.*;
import org.limepepper.gdb.psi.*;

public class GdbStatementImpl extends GdbStatementMixin implements GdbStatement {

  public GdbStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<GdbArgument> getArgumentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbArgument.class);
  }

  @Override
  @NotNull
  public GdbCommandWithFormat getCommandWithFormat() {
    return findNotNullChildByClass(GdbCommandWithFormat.class);
  }

  @Override
  @Nullable
  public GdbCondition getCondition() {
    return findChildByClass(GdbCondition.class);
  }

  @Override
  public @Nullable String getCommandName() {
    return GdbPsiUtil.getCommandName(this);
  }

  @Override
  public @Nullable String getFormatSpec() {
    return GdbPsiUtil.getFormatSpec(this);
  }

}
