// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface GdbAssignmentTarget extends PsiElement {

  @Nullable
  GdbMemoryAssignmentTarget getMemoryAssignmentTarget();

  @Nullable
  GdbSimpleAssignmentTarget getSimpleAssignmentTarget();

  @Nullable
  GdbSubcommandAssignmentTarget getSubcommandAssignmentTarget();

  @Nullable
  GdbVariableAssignmentTarget getVariableAssignmentTarget();

}
