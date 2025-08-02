// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb_plugin.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbStatement extends GdbNamedElement {

  @NotNull
  List<GdbArgument> getArgumentList();

  @NotNull
  GdbCommandWithFormat getCommandWithFormat();

  @Nullable
  GdbCondition getCondition();

  @Nullable String getCommandName();

  @Nullable String getFormatSpec();

}
