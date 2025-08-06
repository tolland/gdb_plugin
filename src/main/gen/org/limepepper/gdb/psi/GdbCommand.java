// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbCommand extends PsiElement {

  @NotNull
  List<GdbCommandStatement> getCommandStatementList();

  @NotNull
  List<GdbDocCommentBlock> getDocCommentBlockList();

  @NotNull
  List<GdbDocumentedCommand> getDocumentedCommandList();

  @NotNull
  List<GdbFloatingComment> getFloatingCommentList();

  @NotNull
  List<GdbSimpleCommand> getSimpleCommandList();

  @Nullable
  GdbValueStatement getValueStatement();

}
