// This is a generated file. Not intended for manual editing.
package org.limepepper.lang.gdb.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.limepepper.lang.gdb.psi.GdbTypes.*;
import static org.limepepper.lang.gdb.parser.GdbParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class GdbParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return gdbFile(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // code_block_start command_argument* PYTHON_BLOCK* END
  public static boolean code_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "code_block")) return false;
    if (!nextTokenIs(builder_, "<code block>", GUILE_KW, PYTHON_KW)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CODE_BLOCK, "<code block>");
    result_ = code_block_start(builder_, level_ + 1);
    result_ = result_ && code_block_1(builder_, level_ + 1);
    result_ = result_ && code_block_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // command_argument*
  private static boolean code_block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "code_block_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!command_argument(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "code_block_1", pos_)) break;
    }
    return true;
  }

  // PYTHON_BLOCK*
  private static boolean code_block_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "code_block_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, PYTHON_BLOCK)) break;
      if (!empty_element_parsed_guard_(builder_, "code_block_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // PYTHON_KW
  //                     | GUILE_KW
  public static boolean code_block_start(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "code_block_start")) return false;
    if (!nextTokenIs(builder_, "<code block start>", GUILE_KW, PYTHON_KW)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, CODE_BLOCK_START, "<code block start>");
    result_ = consumeToken(builder_, PYTHON_KW);
    if (!result_) result_ = consumeToken(builder_, GUILE_KW);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // HEX_NUMBER
  //                      | REGISTER
  //                      | NUMBER
  //                      | DOUBLE_QUOTED_STRING
  //                      | STRING
  //                      | ARG
  public static boolean command_argument(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_argument")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMAND_ARGUMENT, "<command argument>");
    result_ = consumeToken(builder_, HEX_NUMBER);
    if (!result_) result_ = consumeToken(builder_, REGISTER);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, DOUBLE_QUOTED_STRING);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, ARG);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // COMMAND_EXECUTION
  //                | COMMAND_BREAKPOINT
  //                | COMMAND_STACK
  //                | COMMAND_DATA
  //                | COMMAND_PRINT
  //                | COMMAND_SET
  //                | COMMAND_SILENT
  //                | COMMAND_CONFIG
  //                | COMMAND_GENERIC
  public static boolean command_name(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_name")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMAND_NAME, "<command name>");
    result_ = consumeToken(builder_, COMMAND_EXECUTION);
    if (!result_) result_ = consumeToken(builder_, COMMAND_BREAKPOINT);
    if (!result_) result_ = consumeToken(builder_, COMMAND_STACK);
    if (!result_) result_ = consumeToken(builder_, COMMAND_DATA);
    if (!result_) result_ = consumeToken(builder_, COMMAND_PRINT);
    if (!result_) result_ = consumeToken(builder_, COMMAND_SET);
    if (!result_) result_ = consumeToken(builder_, COMMAND_SILENT);
    if (!result_) result_ = consumeToken(builder_, COMMAND_CONFIG);
    if (!result_) result_ = consumeToken(builder_, COMMAND_GENERIC);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // command_name command_argument* (CRLF | <<eof>>) {
  // }
  public static boolean command_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMAND_STATEMENT, "<command statement>");
    result_ = command_name(builder_, level_ + 1);
    result_ = result_ && command_statement_1(builder_, level_ + 1);
    result_ = result_ && command_statement_2(builder_, level_ + 1);
    result_ = result_ && command_statement_3(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // command_argument*
  private static boolean command_statement_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_statement_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!command_argument(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "command_statement_1", pos_)) break;
    }
    return true;
  }

  // CRLF | <<eof>>
  private static boolean command_statement_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_statement_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, CRLF);
    if (!result_) result_ = eof(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // {
  // }
  private static boolean command_statement_3(PsiBuilder builder_, int level_) {
    return true;
  }

  /* ********************************************************** */
  // commands_block_start command_argument* statement* END
  public static boolean commands_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "commands_block")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMANDS_BLOCK, "<commands block>");
    result_ = commands_block_start(builder_, level_ + 1);
    result_ = result_ && commands_block_1(builder_, level_ + 1);
    result_ = result_ && commands_block_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // command_argument*
  private static boolean commands_block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "commands_block_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!command_argument(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "commands_block_1", pos_)) break;
    }
    return true;
  }

  // statement*
  private static boolean commands_block_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "commands_block_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "commands_block_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // COMMAND_COMMANDS
  //                       | COMMAND_USER
  //                       | COMMAND_CONTROL
  public static boolean commands_block_start(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "commands_block_start")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMANDS_BLOCK_START, "<commands block start>");
    result_ = consumeToken(builder_, COMMAND_COMMANDS);
    if (!result_) result_ = consumeToken(builder_, COMMAND_USER);
    if (!result_) result_ = consumeToken(builder_, COMMAND_CONTROL);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // text_block_start command_argument* {CRLF}? doc_block_body END
  public static boolean doc_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_block")) return false;
    if (!nextTokenIs(builder_, COMMAND_DOCUMENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = text_block_start(builder_, level_ + 1);
    result_ = result_ && doc_block_1(builder_, level_ + 1);
    result_ = result_ && doc_block_2(builder_, level_ + 1);
    result_ = result_ && doc_block_body(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    exit_section_(builder_, marker_, DOC_BLOCK, result_);
    return result_;
  }

  // command_argument*
  private static boolean doc_block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_block_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!command_argument(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "doc_block_1", pos_)) break;
    }
    return true;
  }

  // {CRLF}?
  private static boolean doc_block_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_block_2")) return false;
    consumeToken(builder_, CRLF);
    return true;
  }

  /* ********************************************************** */
  // doc_block_lines
  public static boolean doc_block_body(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_block_body")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, DOC_BLOCK_BODY, "<doc block body>");
    result_ = doc_block_lines(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // DOC_BLOCK_LINE*
  static boolean doc_block_lines(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_block_lines")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, DOC_BLOCK_LINE)) break;
      if (!empty_element_parsed_guard_(builder_, "doc_block_lines", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // statement*
  static boolean gdbFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "gdbFile")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "gdbFile", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // CRLF
  //             | comment
  //             | commands_block
  //             | doc_block
  //             | code_block
  //             | command_statement
  static boolean statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement")) return false;
    boolean result_;
    result_ = consumeToken(builder_, CRLF);
    if (!result_) result_ = consumeToken(builder_, COMMENT);
    if (!result_) result_ = commands_block(builder_, level_ + 1);
    if (!result_) result_ = doc_block(builder_, level_ + 1);
    if (!result_) result_ = code_block(builder_, level_ + 1);
    if (!result_) result_ = command_statement(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // COMMAND_DOCUMENT
  public static boolean text_block_start(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "text_block_start")) return false;
    if (!nextTokenIs(builder_, COMMAND_DOCUMENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMAND_DOCUMENT);
    exit_section_(builder_, marker_, TEXT_BLOCK_START, result_);
    return result_;
  }

}
