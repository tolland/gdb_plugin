// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbCommandBlock extends PsiElement {

  @NotNull
  List<GdbCommandLine> getCommandLineList();

  @NotNull
  PsiElement getNewline();

}
