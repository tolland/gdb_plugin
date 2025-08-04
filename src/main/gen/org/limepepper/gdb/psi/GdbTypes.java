// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.limepepper.gdb.psi.impl.*;

public interface GdbTypes {

  IElementType ARGUMENTS = new GdbElementType("ARGUMENTS");
  IElementType ASSIGNMENT = new GdbElementType("ASSIGNMENT");
  IElementType ASSIGNMENT_TARGET = new GdbElementType("ASSIGNMENT_TARGET");
  IElementType ASSIGNMENT_VALUE = new GdbElementType("ASSIGNMENT_VALUE");
  IElementType BLANK = new GdbElementType("BLANK");
  IElementType BREAKPOINT = new GdbElementType("BREAKPOINT");
  IElementType COMMAND_BLOCK = new GdbElementType("COMMAND_BLOCK");
  IElementType COMMAND_LINE = new GdbElementType("COMMAND_LINE");
  IElementType COMMAND_NAME = new GdbElementType("COMMAND_NAME");
  IElementType CONDITION = new GdbElementType("CONDITION");
  IElementType EXPRESSION = new GdbElementType("EXPRESSION");
  IElementType MEMORY_ASSIGNMENT_TARGET = new GdbElementType("MEMORY_ASSIGNMENT_TARGET");
  IElementType SIMPLE_ASSIGNMENT_TARGET = new GdbElementType("SIMPLE_ASSIGNMENT_TARGET");
  IElementType STATEMENT = new GdbElementType("STATEMENT");
  IElementType SUBCOMMAND_ASSIGNMENT_TARGET = new GdbElementType("SUBCOMMAND_ASSIGNMENT_TARGET");
  IElementType VALUE = new GdbElementType("VALUE");
  IElementType VARIABLE_ASSIGNMENT_TARGET = new GdbElementType("VARIABLE_ASSIGNMENT_TARGET");

  IElementType ADDRESS_MARKER = new GdbTokenType("\\*");
  IElementType ARROW = new GdbTokenType("->");
  IElementType ASSIGNMENT_OP = new GdbTokenType("ASSIGNMENT_OP");
  IElementType ASTERISK = new GdbTokenType("ASTERISK");
  IElementType BAD_CHARACTER = new GdbTokenType("BAD_CHARACTER");
  IElementType COLON = new GdbTokenType(":");
  IElementType COMMA = new GdbTokenType(",");
  IElementType COMMAND_BREAKPOINT = new GdbTokenType("COMMAND_BREAKPOINT");
  IElementType COMMAND_CONFIG = new GdbTokenType("COMMAND_CONFIG");
  IElementType COMMAND_DATA = new GdbTokenType("COMMAND_DATA");
  IElementType COMMAND_EXECUTION = new GdbTokenType("COMMAND_EXECUTION");
  IElementType COMMAND_STACK = new GdbTokenType("COMMAND_STACK");
  IElementType COMMAND_USER = new GdbTokenType("COMMAND_USER");
  IElementType COMMENT = new GdbTokenType("COMMENT");
  IElementType CONDITION_IF = new GdbTokenType("if");
  IElementType DOT = new GdbTokenType("\\.");
  IElementType EQUALS = new GdbTokenType("=");
  IElementType FORMAT_SPEC = new GdbTokenType("FORMAT_SPEC");
  IElementType GREATER = new GdbTokenType(">");
  IElementType HEX_NUMBER = new GdbTokenType("HEX_NUMBER");
  IElementType IDENTIFIER = new GdbTokenType("IDENTIFIER");
  IElementType LBRACE = new GdbTokenType("\\{");
  IElementType LBRACKET = new GdbTokenType("\\[");
  IElementType LESS = new GdbTokenType("<");
  IElementType LPAREN = new GdbTokenType("\\(");
  IElementType NEWLINE = new GdbTokenType("NEWLINE");
  IElementType NUMBER = new GdbTokenType("NUMBER");
  IElementType OPERATOR = new GdbTokenType("OPERATOR");
  IElementType RBRACE = new GdbTokenType("\\}");
  IElementType RBRACKET = new GdbTokenType("\\]");
  IElementType REGISTER = new GdbTokenType("REGISTER");
  IElementType RPAREN = new GdbTokenType("\\)");
  IElementType SCOPE_RESOLUTION = new GdbTokenType("::");
  IElementType SEMICOLON = new GdbTokenType(";");
  IElementType SET_CMD = new GdbTokenType("set");
  IElementType STRING = new GdbTokenType("STRING");
  IElementType VARIABLE = new GdbTokenType("VARIABLE");
  IElementType WHITESPACE = new GdbTokenType("WHITESPACE");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ARGUMENTS) {
        return new GdbArgumentsImpl(node);
      }
      else if (type == ASSIGNMENT) {
        return new GdbAssignmentImpl(node);
      }
      else if (type == ASSIGNMENT_TARGET) {
        return new GdbAssignmentTargetImpl(node);
      }
      else if (type == ASSIGNMENT_VALUE) {
        return new GdbAssignmentValueImpl(node);
      }
      else if (type == BLANK) {
        return new GdbBlankImpl(node);
      }
      else if (type == BREAKPOINT) {
        return new GdbBreakpointImpl(node);
      }
      else if (type == COMMAND_BLOCK) {
        return new GdbCommandBlockImpl(node);
      }
      else if (type == COMMAND_LINE) {
        return new GdbCommandLineImpl(node);
      }
      else if (type == COMMAND_NAME) {
        return new GdbCommandNameImpl(node);
      }
      else if (type == CONDITION) {
        return new GdbConditionImpl(node);
      }
      else if (type == EXPRESSION) {
        return new GdbExpressionImpl(node);
      }
      else if (type == MEMORY_ASSIGNMENT_TARGET) {
        return new GdbMemoryAssignmentTargetImpl(node);
      }
      else if (type == SIMPLE_ASSIGNMENT_TARGET) {
        return new GdbSimpleAssignmentTargetImpl(node);
      }
      else if (type == STATEMENT) {
        return new GdbStatementImpl(node);
      }
      else if (type == SUBCOMMAND_ASSIGNMENT_TARGET) {
        return new GdbSubcommandAssignmentTargetImpl(node);
      }
      else if (type == VALUE) {
        return new GdbValueImpl(node);
      }
      else if (type == VARIABLE_ASSIGNMENT_TARGET) {
        return new GdbVariableAssignmentTargetImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
