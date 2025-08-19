// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.limepepper.gdb.psi.GdbTypes.*;
import org.limepepper.gdb.psi.GdbPsiElement;
import org.limepepper.gdb.psi.*;

public class GdbCommandImpl extends GdbPsiElement implements GdbCommand {

  public GdbCommandImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitCommand(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<GdbCommandStatement> getCommandStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbCommandStatement.class);
  }

  @Override
  @NotNull
  public List<GdbDocCommentBlock> getDocCommentBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbDocCommentBlock.class);
  }

  @Override
  @NotNull
  public List<GdbDocumentedCommand> getDocumentedCommandList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbDocumentedCommand.class);
  }

  @Override
  @NotNull
  public List<GdbFloatingComment> getFloatingCommentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbFloatingComment.class);
  }

  @Override
  @NotNull
  public List<GdbSimpleCommand> getSimpleCommandList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, GdbSimpleCommand.class);
  }

  @Override
  @Nullable
  public GdbValueStatement getValueStatement() {
    return findChildByClass(GdbValueStatement.class);
  }

}
