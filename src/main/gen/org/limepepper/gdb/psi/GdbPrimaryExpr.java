// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbPrimaryExpr extends PsiElement {

  @Nullable
  GdbArrayAccess getArrayAccess();

  @Nullable
  GdbExpression getExpression();

  @Nullable
  GdbFunctionCall getFunctionCall();

  @Nullable
  GdbMemberAccess getMemberAccess();

  @Nullable
  GdbRegisterRef getRegisterRef();

  @Nullable
  PsiElement getHexNumber();

  @Nullable
  PsiElement getIdentifier();

  @Nullable
  PsiElement getNumber();

  @Nullable
  PsiElement getString();

}
