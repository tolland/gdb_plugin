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

public class GdbArgumentImpl extends GdbArgumentMixin implements GdbArgument {

  public GdbArgumentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitArgument(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public GdbAddress getAddress() {
    return findChildByClass(GdbAddress.class);
  }

  @Override
  @Nullable
  public GdbExpression getExpression() {
    return findChildByClass(GdbExpression.class);
  }

  @Override
  @Nullable
  public GdbRegisterRef getRegisterRef() {
    return findChildByClass(GdbRegisterRef.class);
  }

  @Override
  @Nullable
  public PsiElement getIdentifier() {
    return findChildByType(IDENTIFIER);
  }

  @Override
  @Nullable
  public PsiElement getString() {
    return findChildByType(STRING);
  }

}
