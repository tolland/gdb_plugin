// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb_plugin.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.limepepper.gdb_plugin.psi.impl.*;

public interface GdbTypes {

  IElementType ADDITIVE_EXPR = new GdbElementType("ADDITIVE_EXPR");
  IElementType ADDRESS = new GdbElementType("ADDRESS");
  IElementType ARGUMENT = new GdbElementType("ARGUMENT");
  IElementType ARRAY_ACCESS = new GdbElementType("ARRAY_ACCESS");
  IElementType COMMAND_WITH_FORMAT = new GdbElementType("COMMAND_WITH_FORMAT");
  IElementType COMMENT_LINE = new GdbElementType("COMMENT_LINE");
  IElementType CONDITION = new GdbElementType("CONDITION");
  IElementType EQUALITY_EXPR = new GdbElementType("EQUALITY_EXPR");
  IElementType EXPRESSION = new GdbElementType("EXPRESSION");
  IElementType FORMATTED_COMMAND = new GdbElementType("FORMATTED_COMMAND");
  IElementType FUNCTION_CALL = new GdbElementType("FUNCTION_CALL");
  IElementType LOGICAL_AND_EXPR = new GdbElementType("LOGICAL_AND_EXPR");
  IElementType LOGICAL_OR_EXPR = new GdbElementType("LOGICAL_OR_EXPR");
  IElementType MEMBER_ACCESS = new GdbElementType("MEMBER_ACCESS");
  IElementType MULTIPLICATIVE_EXPR = new GdbElementType("MULTIPLICATIVE_EXPR");
  IElementType PRIMARY_EXPR = new GdbElementType("PRIMARY_EXPR");
  IElementType REGISTER_REF = new GdbElementType("REGISTER_REF");
  IElementType RELATIONAL_EXPR = new GdbElementType("RELATIONAL_EXPR");
  IElementType SIMPLE_COMMAND = new GdbElementType("SIMPLE_COMMAND");
  IElementType STATEMENT = new GdbElementType("STATEMENT");
  IElementType UNARY_EXPR = new GdbElementType("UNARY_EXPR");

  IElementType ADDRESS_MARKER = new GdbTokenType("*");
  IElementType ARROW = new GdbTokenType("->");
  IElementType ASSIGNMENT = new GdbTokenType("ASSIGNMENT");
  IElementType BREAK_CMD = new GdbTokenType("break");
  IElementType B_CMD = new GdbTokenType("b");
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
  IElementType DOT = new GdbTokenType(".");
  IElementType FORMAT_SPEC = new GdbTokenType("FORMAT_SPEC");
  IElementType HEX_NUMBER = new GdbTokenType("HEX_NUMBER");
  IElementType IDENTIFIER = new GdbTokenType("IDENTIFIER");
  IElementType LBRACE = new GdbTokenType("{");
  IElementType LBRACKET = new GdbTokenType("[");
  IElementType LPAREN = new GdbTokenType("(");
  IElementType NEWLINE = new GdbTokenType("NEWLINE");
  IElementType NUMBER = new GdbTokenType("NUMBER");
  IElementType OPERATOR = new GdbTokenType("OPERATOR");
  IElementType PRINT_CMD = new GdbTokenType("print");
  IElementType P_CMD = new GdbTokenType("p");
  IElementType RBRACE = new GdbTokenType("}");
  IElementType RBRACKET = new GdbTokenType("]");
  IElementType REGISTER = new GdbTokenType("REGISTER");
  IElementType RPAREN = new GdbTokenType(")");
  IElementType SCOPE_RESOLUTION = new GdbTokenType("::");
  IElementType SEMICOLON = new GdbTokenType(";");
  IElementType STRING = new GdbTokenType("STRING");
  IElementType X_CMD = new GdbTokenType("x");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ADDITIVE_EXPR) {
        return new GdbAdditiveExprImpl(node);
      }
      else if (type == ADDRESS) {
        return new GdbAddressImpl(node);
      }
      else if (type == ARGUMENT) {
        return new GdbArgumentImpl(node);
      }
      else if (type == ARRAY_ACCESS) {
        return new GdbArrayAccessImpl(node);
      }
      else if (type == COMMAND_WITH_FORMAT) {
        return new GdbCommandWithFormatImpl(node);
      }
      else if (type == COMMENT_LINE) {
        return new GdbCommentLineImpl(node);
      }
      else if (type == CONDITION) {
        return new GdbConditionImpl(node);
      }
      else if (type == EQUALITY_EXPR) {
        return new GdbEqualityExprImpl(node);
      }
      else if (type == EXPRESSION) {
        return new GdbExpressionImpl(node);
      }
      else if (type == FORMATTED_COMMAND) {
        return new GdbFormattedCommandImpl(node);
      }
      else if (type == FUNCTION_CALL) {
        return new GdbFunctionCallImpl(node);
      }
      else if (type == LOGICAL_AND_EXPR) {
        return new GdbLogicalAndExprImpl(node);
      }
      else if (type == LOGICAL_OR_EXPR) {
        return new GdbLogicalOrExprImpl(node);
      }
      else if (type == MEMBER_ACCESS) {
        return new GdbMemberAccessImpl(node);
      }
      else if (type == MULTIPLICATIVE_EXPR) {
        return new GdbMultiplicativeExprImpl(node);
      }
      else if (type == PRIMARY_EXPR) {
        return new GdbPrimaryExprImpl(node);
      }
      else if (type == REGISTER_REF) {
        return new GdbRegisterRefImpl(node);
      }
      else if (type == RELATIONAL_EXPR) {
        return new GdbRelationalExprImpl(node);
      }
      else if (type == SIMPLE_COMMAND) {
        return new GdbSimpleCommandImpl(node);
      }
      else if (type == STATEMENT) {
        return new GdbStatementImpl(node);
      }
      else if (type == UNARY_EXPR) {
        return new GdbUnaryExprImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
