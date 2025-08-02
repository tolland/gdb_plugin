// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb_plugin.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbArgument extends PsiElement {

  @Nullable
  GdbAddress getAddress();

  @Nullable
  GdbExpression getExpression();

  @Nullable
  GdbRegisterRef getRegisterRef();

  @Nullable
  PsiElement getIdentifier();

  @Nullable
  PsiElement getString();

}
