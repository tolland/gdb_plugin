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

public class GdbSimpleCommandImpl extends ASTWrapperPsiElement implements GdbSimpleCommand {

  public GdbSimpleCommandImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitSimpleCommand(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getCommandBreakpoint() {
    return findChildByType(COMMAND_BREAKPOINT);
  }

  @Override
  @Nullable
  public PsiElement getCommandConfig() {
    return findChildByType(COMMAND_CONFIG);
  }

  @Override
  @Nullable
  public PsiElement getCommandData() {
    return findChildByType(COMMAND_DATA);
  }

  @Override
  @Nullable
  public PsiElement getCommandExecution() {
    return findChildByType(COMMAND_EXECUTION);
  }

  @Override
  @Nullable
  public PsiElement getCommandStack() {
    return findChildByType(COMMAND_STACK);
  }

  @Override
  @Nullable
  public PsiElement getCommandUser() {
    return findChildByType(COMMAND_USER);
  }

}
