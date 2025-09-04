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

public class GdbCommandNameImpl extends GdbPsiElement implements GdbCommandName {

  public GdbCommandNameImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitCommandName(this);
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
  public PsiElement getCommandGeneric() {
    return findChildByType(COMMAND_GENERIC);
  }

  @Override
  @Nullable
  public PsiElement getCommandPrint() {
    return findChildByType(COMMAND_PRINT);
  }

  @Override
  @Nullable
  public PsiElement getCommandSet() {
    return findChildByType(COMMAND_SET);
  }

  @Override
  @Nullable
  public PsiElement getCommandSilent() {
    return findChildByType(COMMAND_SILENT);
  }

  @Override
  @Nullable
  public PsiElement getCommandStack() {
    return findChildByType(COMMAND_STACK);
  }

}
