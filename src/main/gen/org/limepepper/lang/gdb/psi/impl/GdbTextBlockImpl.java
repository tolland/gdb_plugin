// This is a generated file. Not intended for manual editing.
package org.limepepper.lang.gdb.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.limepepper.lang.gdb.psi.GdbTypes.*;
import org.limepepper.lang.gdb.psi.GdbPsiElement;
import org.limepepper.lang.gdb.psi.*;

public class GdbTextBlockImpl extends GdbPsiElement implements GdbTextBlock {

  public GdbTextBlockImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitTextBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<GdbCommandArgument> getCommandArgumentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbCommandArgument.class);
  }

  @Override
  @NotNull
  public GdbTextBlockStart getTextBlockStart() {
    return findNotNullChildByClass(GdbTextBlockStart.class);
  }

}
