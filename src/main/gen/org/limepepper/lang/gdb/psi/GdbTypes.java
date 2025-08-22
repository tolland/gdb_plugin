// This is a generated file. Not intended for manual editing.
package org.limepepper.lang.gdb.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.limepepper.lang.gdb.psi.impl.*;

public interface GdbTypes {

  IElementType CODE_BLOCK = new GdbElementType("CODE_BLOCK");
  IElementType CODE_BLOCK_START = new GdbElementType("CODE_BLOCK_START");
  IElementType COMMANDS_BLOCK = new GdbElementType("COMMANDS_BLOCK");
  IElementType COMMANDS_BLOCK_START = new GdbElementType("COMMANDS_BLOCK_START");
  IElementType COMMAND_ARGUMENT = new GdbElementType("COMMAND_ARGUMENT");
  IElementType COMMAND_NAME = new GdbElementType("COMMAND_NAME");
  IElementType COMMAND_STATEMENT = new GdbElementType("COMMAND_STATEMENT");
  IElementType DOC_BLOCK = new GdbElementType("DOC_BLOCK");
  IElementType DOC_BLOCK_BODY = new GdbElementType("DOC_BLOCK_BODY");
  IElementType TEXT_BLOCK_START = new GdbElementType("TEXT_BLOCK_START");

  IElementType ARG = new GdbTokenType("ARG");
  IElementType ARGS_BLOCK = new GdbTokenType("ARGS_BLOCK");
  IElementType ARROW = new GdbTokenType("->");
  IElementType ASSIGNMENT = new GdbTokenType("ASSIGNMENT");
  IElementType COLON = new GdbTokenType(":");
  IElementType COMMA = new GdbTokenType(",");
  IElementType COMMAND_BREAKPOINT = new GdbTokenType("COMMAND_BREAKPOINT");
  IElementType COMMAND_COMMANDS = new GdbTokenType("COMMAND_COMMANDS");
  IElementType COMMAND_CONFIG = new GdbTokenType("COMMAND_CONFIG");
  IElementType COMMAND_CONTROL = new GdbTokenType("COMMAND_CONTROL");
  IElementType COMMAND_DATA = new GdbTokenType("COMMAND_DATA");
  IElementType COMMAND_DOCUMENT = new GdbTokenType("COMMAND_DOCUMENT");
  IElementType COMMAND_EXECUTION = new GdbTokenType("COMMAND_EXECUTION");
  IElementType COMMAND_GENERIC = new GdbTokenType("COMMAND_GENERIC");
  IElementType COMMAND_PRINT = new GdbTokenType("COMMAND_PRINT");
  IElementType COMMAND_SET = new GdbTokenType("COMMAND_SET");
  IElementType COMMAND_SILENT = new GdbTokenType("COMMAND_SILENT");
  IElementType COMMAND_STACK = new GdbTokenType("COMMAND_STACK");
  IElementType COMMAND_USER = new GdbTokenType("COMMAND_USER");
  IElementType COMMENT = new GdbTokenType("comment");
  IElementType CRLF = new GdbTokenType("CRLF");
  IElementType DOC_BLOCK_LINE = new GdbTokenType("DOC_BLOCK_LINE");
  IElementType DOT = new GdbTokenType(".");
  IElementType DOUBLE_QUOTED_STRING = new GdbTokenType("DOUBLE_QUOTED_STRING");
  IElementType END = new GdbTokenType("end");
  IElementType GUILE_BLOCK = new GdbTokenType("GUILE_BLOCK");
  IElementType GUILE_KW = new GdbTokenType("guile");
  IElementType HEX_NUMBER = new GdbTokenType("HEX_NUMBER");
  IElementType IDENTIFIER = new GdbTokenType("IDENTIFIER");
  IElementType LBRACE = new GdbTokenType("{");
  IElementType LBRACKET = new GdbTokenType("[");
  IElementType LINE_CONTINUATION = new GdbTokenType("LINE_CONTINUATION");
  IElementType LPAREN = new GdbTokenType("(");
  IElementType NUMBER = new GdbTokenType("NUMBER");
  IElementType OPERATOR = new GdbTokenType("OPERATOR");
  IElementType OP_AND_AND = new GdbTokenType("&&");
  IElementType OP_ASSIGN = new GdbTokenType("=");
  IElementType OP_DIV = new GdbTokenType("/");
  IElementType OP_ELLIPSIS = new GdbTokenType("...");
  IElementType OP_EQUAL = new GdbTokenType("==");
  IElementType OP_GREATER = new GdbTokenType(">");
  IElementType OP_GREATER_OR_EQUAL = new GdbTokenType(">=");
  IElementType OP_LESS = new GdbTokenType("<");
  IElementType OP_LESS_OR_EQUAL = new GdbTokenType("<=");
  IElementType OP_MINUS = new GdbTokenType("-");
  IElementType OP_MOD = new GdbTokenType("%");
  IElementType OP_MUL = new GdbTokenType("*");
  IElementType OP_OR_OR = new GdbTokenType("||");
  IElementType OP_PIPE = new GdbTokenType("|");
  IElementType OP_PLUS = new GdbTokenType("+");
  IElementType PYTHON_BLOCK = new GdbTokenType("PYTHON_BLOCK");
  IElementType PYTHON_BLOCK_LINE = new GdbTokenType("PYTHON_BLOCK_LINE");
  IElementType PYTHON_INLINE = new GdbTokenType("PYTHON_INLINE");
  IElementType PYTHON_KW = new GdbTokenType("python");
  IElementType RBRACE = new GdbTokenType("}");
  IElementType RBRACKET = new GdbTokenType("]");
  IElementType REGISTER = new GdbTokenType("REGISTER");
  IElementType RPAREN = new GdbTokenType(")");
  IElementType SCOPE_RESOLUTION = new GdbTokenType("::");
  IElementType SEMICOLON = new GdbTokenType(";");
  IElementType SINGLE_QUOTE = new GdbTokenType("'");
  IElementType STRING = new GdbTokenType("STRING");
  IElementType WORD = new GdbTokenType("WORD");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == CODE_BLOCK) {
        return new GdbCodeBlockImpl(node);
      }
      else if (type == CODE_BLOCK_START) {
        return new GdbCodeBlockStartImpl(node);
      }
      else if (type == COMMANDS_BLOCK) {
        return new GdbCommandsBlockImpl(node);
      }
      else if (type == COMMANDS_BLOCK_START) {
        return new GdbCommandsBlockStartImpl(node);
      }
      else if (type == COMMAND_ARGUMENT) {
        return new GdbCommandArgumentImpl(node);
      }
      else if (type == COMMAND_NAME) {
        return new GdbCommandNameImpl(node);
      }
      else if (type == COMMAND_STATEMENT) {
        return new GdbCommandStatementImpl(node);
      }
      else if (type == DOC_BLOCK) {
        return new GdbDocBlockImpl(node);
      }
      else if (type == DOC_BLOCK_BODY) {
        return new GdbDocBlockBodyImpl(node);
      }
      else if (type == TEXT_BLOCK_START) {
        return new GdbTextBlockStartImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
