// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.limepepper.gdb.psi.GdbTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
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
  // set_print_command | normal_command
  public static boolean command(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMAND, "<command>");
    result_ = set_print_command(builder_, level_ + 1);
    if (!result_) result_ = normal_command(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // define_block | command
  public static boolean command_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMAND_STATEMENT, "<command statement>");
    result_ = define_block(builder_, level_ + 1);
    if (!result_) result_ = command(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // COMMANDS CRLF statement* END CRLF?
  static boolean commands_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "commands_block")) return false;
    if (!nextTokenIs(builder_, COMMANDS)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMANDS, CRLF);
    result_ = result_ && commands_block_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    result_ = result_ && commands_block_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
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

  // CRLF?
  private static boolean commands_block_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "commands_block_4")) return false;
    consumeToken(builder_, CRLF);
    return true;
  }

  /* ********************************************************** */
  // documented_command | floating_comment | doc_comment_block
  static boolean comment_driven_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comment_driven_statement")) return false;
    if (!nextTokenIs(builder_, COMMENT)) return false;
    boolean result_;
    result_ = documented_command(builder_, level_ + 1);
    if (!result_) result_ = floating_comment(builder_, level_ + 1);
    if (!result_) result_ = doc_comment_block(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // DEFINE WORD+ CRLF statement* END CRLF?
  public static boolean define_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "define_block")) return false;
    if (!nextTokenIs(builder_, DEFINE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, DEFINE);
    result_ = result_ && define_block_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, CRLF);
    result_ = result_ && define_block_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, END);
    result_ = result_ && define_block_5(builder_, level_ + 1);
    exit_section_(builder_, marker_, DEFINE_BLOCK, result_);
    return result_;
  }

  // WORD+
  private static boolean define_block_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "define_block_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, WORD);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, WORD)) break;
      if (!empty_element_parsed_guard_(builder_, "define_block_1", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // statement*
  private static boolean define_block_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "define_block_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!statement(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "define_block_3", pos_)) break;
    }
    return true;
  }

  // CRLF?
  private static boolean define_block_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "define_block_5")) return false;
    consumeToken(builder_, CRLF);
    return true;
  }

  /* ********************************************************** */
  // (COMMENT CRLF)+
  public static boolean doc_comment_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_comment_block")) return false;
    if (!nextTokenIs(builder_, COMMENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = doc_comment_block_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!doc_comment_block_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "doc_comment_block", pos_)) break;
    }
    exit_section_(builder_, marker_, DOC_COMMENT_BLOCK, result_);
    return result_;
  }

  // COMMENT CRLF
  private static boolean doc_comment_block_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "doc_comment_block_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMENT, CRLF);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // doc_comment_block &(!CRLF) command_statement
  public static boolean documented_command(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "documented_command")) return false;
    if (!nextTokenIs(builder_, COMMENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = doc_comment_block(builder_, level_ + 1);
    result_ = result_ && documented_command_1(builder_, level_ + 1);
    result_ = result_ && command_statement(builder_, level_ + 1);
    exit_section_(builder_, marker_, DOCUMENTED_COMMAND, result_);
    return result_;
  }

  // &(!CRLF)
  private static boolean documented_command_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "documented_command_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _AND_);
    result_ = documented_command_1_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !CRLF
  private static boolean documented_command_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "documented_command_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, CRLF);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // doc_comment_block CRLF
  public static boolean floating_comment(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "floating_comment")) return false;
    if (!nextTokenIs(builder_, COMMENT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = doc_comment_block(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, CRLF);
    exit_section_(builder_, marker_, FLOATING_COMMENT, result_);
    return result_;
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
  // normal_command_line commands_block?
  static boolean normal_command(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = normal_command_line(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && normal_command_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // commands_block?
  private static boolean normal_command_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_1")) return false;
    commands_block(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // simple_command+
  //                                 ((IDENTIFIER | WORD) &(!CRLF))*
  //                                 (COMMENT)? CRLF
  static boolean normal_command_line(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = normal_command_line_0(builder_, level_ + 1);
    result_ = result_ && normal_command_line_1(builder_, level_ + 1);
    result_ = result_ && normal_command_line_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, CRLF);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // simple_command+
  private static boolean normal_command_line_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = simple_command(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!simple_command(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "normal_command_line_0", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ((IDENTIFIER | WORD) &(!CRLF))*
  private static boolean normal_command_line_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!normal_command_line_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "normal_command_line_1", pos_)) break;
    }
    return true;
  }

  // (IDENTIFIER | WORD) &(!CRLF)
  private static boolean normal_command_line_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = normal_command_line_1_0_0(builder_, level_ + 1);
    result_ = result_ && normal_command_line_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | WORD
  private static boolean normal_command_line_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, WORD);
    return result_;
  }

  // &(!CRLF)
  private static boolean normal_command_line_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _AND_);
    result_ = normal_command_line_1_0_1_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !CRLF
  private static boolean normal_command_line_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, CRLF);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (COMMENT)?
  private static boolean normal_command_line_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "normal_command_line_2")) return false;
    consumeToken(builder_, COMMENT);
    return true;
  }

  /* ********************************************************** */
  // set_print_command_line commands_block?
  static boolean set_print_command(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command")) return false;
    if (!nextTokenIs(builder_, "", PRINT_KW, SET_KW)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = set_print_command_line(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && set_print_command_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // commands_block?
  private static boolean set_print_command_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_1")) return false;
    commands_block(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // (SET_KW | PRINT_KW)
  //                                    ((IDENTIFIER | REGISTER | EQUALS | COMMENT) &(!CRLF))*
  //                                     value_statement
  //                                    CRLF
  static boolean set_print_command_line(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line")) return false;
    if (!nextTokenIs(builder_, "", PRINT_KW, SET_KW)) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = set_print_command_line_0(builder_, level_ + 1);
    pinned_ = result_; // pin = 1
    result_ = result_ && report_error_(builder_, set_print_command_line_1(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, value_statement(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, CRLF) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // SET_KW | PRINT_KW
  private static boolean set_print_command_line_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, SET_KW);
    if (!result_) result_ = consumeToken(builder_, PRINT_KW);
    return result_;
  }

  // ((IDENTIFIER | REGISTER | EQUALS | COMMENT) &(!CRLF))*
  private static boolean set_print_command_line_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!set_print_command_line_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "set_print_command_line_1", pos_)) break;
    }
    return true;
  }

  // (IDENTIFIER | REGISTER | EQUALS | COMMENT) &(!CRLF)
  private static boolean set_print_command_line_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = set_print_command_line_1_0_0(builder_, level_ + 1);
    result_ = result_ && set_print_command_line_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // IDENTIFIER | REGISTER | EQUALS | COMMENT
  private static boolean set_print_command_line_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line_1_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, REGISTER);
    if (!result_) result_ = consumeToken(builder_, EQUALS);
    if (!result_) result_ = consumeToken(builder_, COMMENT);
    return result_;
  }

  // &(!CRLF)
  private static boolean set_print_command_line_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _AND_);
    result_ = set_print_command_line_1_0_1_0(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !CRLF
  private static boolean set_print_command_line_1_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "set_print_command_line_1_0_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, CRLF);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // COMMAND_EXECUTION
  //          | COMMAND_BREAKPOINT
  //          | COMMAND_STACK
  //          | COMMAND_DATA
  //          | COMMAND_CONFIG
  //          | COMMAND_USER
  public static boolean simple_command(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_command")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SIMPLE_COMMAND, "<simple command>");
    result_ = consumeToken(builder_, COMMAND_EXECUTION);
    if (!result_) result_ = consumeToken(builder_, COMMAND_BREAKPOINT);
    if (!result_) result_ = consumeToken(builder_, COMMAND_STACK);
    if (!result_) result_ = consumeToken(builder_, COMMAND_DATA);
    if (!result_) result_ = consumeToken(builder_, COMMAND_CONFIG);
    if (!result_) result_ = consumeToken(builder_, COMMAND_USER);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // comment_driven_statement | command_statement | CRLF
  static boolean statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement")) return false;
    boolean result_;
    result_ = comment_driven_statement(builder_, level_ + 1);
    if (!result_) result_ = command_statement(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, CRLF);
    return result_;
  }

  /* ********************************************************** */
  // HEX_NUMBER | NUMBER | STRING | IDENTIFIER | WORD
  public static boolean value_statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "value_statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VALUE_STATEMENT, "<value statement>");
    result_ = consumeToken(builder_, HEX_NUMBER);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, WORD);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

}
