// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.limepepper.gdb.psi.impl.*;

public interface GdbTypes {

  IElementType COMMAND = new GdbElementType("COMMAND");
  IElementType COMMAND_STATEMENT = new GdbElementType("COMMAND_STATEMENT");
  IElementType DEFINE_BLOCK = new GdbElementType("DEFINE_BLOCK");
  IElementType DOCUMENTED_COMMAND = new GdbElementType("DOCUMENTED_COMMAND");
  IElementType DOC_COMMENT_BLOCK = new GdbElementType("DOC_COMMENT_BLOCK");
  IElementType FLOATING_COMMENT = new GdbElementType("FLOATING_COMMENT");
  IElementType SIMPLE_COMMAND = new GdbElementType("SIMPLE_COMMAND");
  IElementType VALUE_STATEMENT = new GdbElementType("VALUE_STATEMENT");

  IElementType ARGS_BLOCK = new GdbTokenType("ARGS_BLOCK");
  IElementType ARROW = new GdbTokenType("->");
  IElementType COLON = new GdbTokenType(":");
  IElementType COMMA = new GdbTokenType(",");
  IElementType COMMANDS = new GdbTokenType("commands");
  IElementType COMMAND_BREAKPOINT = new GdbTokenType("COMMAND_BREAKPOINT");
  IElementType COMMAND_CONFIG = new GdbTokenType("COMMAND_CONFIG");
  IElementType COMMAND_DATA = new GdbTokenType("COMMAND_DATA");
  IElementType COMMAND_EXECUTION = new GdbTokenType("COMMAND_EXECUTION");
  IElementType COMMAND_GENERIC = new GdbTokenType("COMMAND_GENERIC");
  IElementType COMMAND_STACK = new GdbTokenType("COMMAND_STACK");
  IElementType COMMAND_USER = new GdbTokenType("COMMAND_USER");
  IElementType COMMENT = new GdbTokenType("COMMENT");
  IElementType CRLF = new GdbTokenType("CRLF");
  IElementType DEFINE = new GdbTokenType("define");
  IElementType DOC_BLOCK = new GdbTokenType("DOC_BLOCK");
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
  IElementType PRINT_KW = new GdbTokenType("print");
  IElementType PYTHON_BLOCK = new GdbTokenType("PYTHON_BLOCK");
  IElementType PYTHON_KW = new GdbTokenType("python");
  IElementType RBRACE = new GdbTokenType("}");
  IElementType RBRACKET = new GdbTokenType("]");
  IElementType REGISTER = new GdbTokenType("REGISTER");
  IElementType RPAREN = new GdbTokenType(")");
  IElementType SCOPE_RESOLUTION = new GdbTokenType("::");
  IElementType SEMICOLON = new GdbTokenType(";");
  IElementType SET_KW = new GdbTokenType("set");
  IElementType SINGLE_QUOTE = new GdbTokenType("'");
  IElementType STRING = new GdbTokenType("STRING");
  IElementType WORD = new GdbTokenType("WORD");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMMAND) {
        return new GdbCommandImpl(node);
      }
      else if (type == COMMAND_STATEMENT) {
        return new GdbCommandStatementImpl(node);
      }
      else if (type == DEFINE_BLOCK) {
        return new GdbDefineBlockImpl(node);
      }
      else if (type == DOCUMENTED_COMMAND) {
        return new GdbDocumentedCommandImpl(node);
      }
      else if (type == DOC_COMMENT_BLOCK) {
        return new GdbDocCommentBlockImpl(node);
      }
      else if (type == FLOATING_COMMENT) {
        return new GdbFloatingCommentImpl(node);
      }
      else if (type == SIMPLE_COMMAND) {
        return new GdbSimpleCommandImpl(node);
      }
      else if (type == VALUE_STATEMENT) {
        return new GdbValueStatementImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
