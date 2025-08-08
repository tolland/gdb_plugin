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

public class GdbCommandStatementImpl extends GdbPsiElement implements GdbCommandStatement {

  public GdbCommandStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitCommandStatement(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public GdbCommand getCommand() {
    return findChildByClass(GdbCommand.class);
  }

  @Override
  @Nullable
  public GdbDefineBlock getDefineBlock() {
    return findChildByClass(GdbDefineBlock.class);
  }

  @Override
  public @Nullable String getKey() {
    return GdbPsiImplUtil.getKey(this);
  }

}
