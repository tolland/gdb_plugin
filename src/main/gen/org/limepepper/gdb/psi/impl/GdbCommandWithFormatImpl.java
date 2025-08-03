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

public class GdbCommandWithFormatImpl extends ASTWrapperPsiElement implements GdbCommandWithFormat {

  public GdbCommandWithFormatImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitCommandWithFormat(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public GdbFormattedCommand getFormattedCommand() {
    return findChildByClass(GdbFormattedCommand.class);
  }

  @Override
  @Nullable
  public GdbSimpleCommand getSimpleCommand() {
    return findChildByClass(GdbSimpleCommand.class);
  }

}
