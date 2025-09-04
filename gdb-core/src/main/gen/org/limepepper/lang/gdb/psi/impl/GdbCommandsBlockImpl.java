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

public class GdbCommandsBlockImpl extends GdbPsiElement implements GdbCommandsBlock {

  public GdbCommandsBlockImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitCommandsBlock(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<GdbCodeBlock> getCodeBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbCodeBlock.class);
  }

  @Override
  @NotNull
  public List<GdbCommandArgument> getCommandArgumentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbCommandArgument.class);
  }

  @Override
  @NotNull
  public List<GdbCommandStatement> getCommandStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbCommandStatement.class);
  }

  @Override
  @NotNull
  public List<GdbCommandsBlock> getCommandsBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbCommandsBlock.class);
  }

  @Override
  @NotNull
  public GdbCommandsBlockStart getCommandsBlockStart() {
    return findNotNullChildByClass(GdbCommandsBlockStart.class);
  }

  @Override
  @NotNull
  public List<GdbDocBlock> getDocBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbDocBlock.class);
  }

}
