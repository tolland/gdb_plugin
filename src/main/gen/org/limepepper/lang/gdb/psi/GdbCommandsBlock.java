// This is a generated file. Not intended for manual editing.
package org.limepepper.lang.gdb.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbCommandsBlock extends PsiElement {

  @NotNull
  List<GdbCodeBlock> getCodeBlockList();

  @NotNull
  List<GdbCommandArgument> getCommandArgumentList();

  @NotNull
  List<GdbCommandStatement> getCommandStatementList();

  @NotNull
  List<GdbCommandsBlock> getCommandsBlockList();

  @NotNull
  GdbCommandsBlockStart getCommandsBlockStart();

  @NotNull
  List<GdbTextBlock> getTextBlockList();

}
