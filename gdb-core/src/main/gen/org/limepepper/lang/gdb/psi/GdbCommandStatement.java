// This is a generated file. Not intended for manual editing.
package org.limepepper.lang.gdb.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbCommandStatement extends PsiElement {

  @NotNull
  List<GdbCommandArgument> getCommandArgumentList();

  @NotNull
  GdbCommandName getCommandName();

  @Nullable
  PsiElement getCrlf();

}
