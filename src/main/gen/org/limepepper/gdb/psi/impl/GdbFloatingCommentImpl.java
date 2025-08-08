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

public class GdbFloatingCommentImpl extends GdbPsiElement implements GdbFloatingComment {

  public GdbFloatingCommentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GdbVisitor visitor) {
    visitor.visitFloatingComment(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GdbVisitor) accept((GdbVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public GdbDocCommentBlock getDocCommentBlock() {
    return findNotNullChildByClass(GdbDocCommentBlock.class);
  }

  @Override
  @NotNull
  public PsiElement getCrlf() {
    return findNotNullChildByType(CRLF);
  }

}
