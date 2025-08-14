// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.limepepper.gdb.psi.GdbTypes.*;
import static org.limepepper.gdb.parser.GdbParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class GdbParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return gdbFile(b, l + 1);
  }

  /* ********************************************************** */
  // set_print_command | normal_command
  public static boolean command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMAND, "<command>");
    r = set_print_command(b, l + 1);
    if (!r) r = normal_command(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // define_block | command
  public static boolean command_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMAND_STATEMENT, "<command statement>");
    r = define_block(b, l + 1);
    if (!r) r = command(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COMMANDS CRLF statement* END CRLF?
  static boolean commands_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "commands_block")) return false;
    if (!nextTokenIs(b, COMMANDS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMANDS, CRLF);
    r = r && commands_block_2(b, l + 1);
    r = r && consumeToken(b, END);
    r = r && commands_block_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // statement*
  private static boolean commands_block_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "commands_block_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "commands_block_2", c)) break;
    }
    return true;
  }

  // CRLF?
  private static boolean commands_block_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "commands_block_4")) return false;
    consumeToken(b, CRLF);
    return true;
  }

  /* ********************************************************** */
  // documented_command | floating_comment | doc_comment_block
  static boolean comment_driven_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_driven_statement")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    r = documented_command(b, l + 1);
    if (!r) r = floating_comment(b, l + 1);
    if (!r) r = doc_comment_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // DEFINE WORD+ CRLF statement* END CRLF?
  public static boolean define_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_block")) return false;
    if (!nextTokenIs(b, DEFINE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DEFINE);
    r = r && define_block_1(b, l + 1);
    r = r && consumeToken(b, CRLF);
    r = r && define_block_3(b, l + 1);
    r = r && consumeToken(b, END);
    r = r && define_block_5(b, l + 1);
    exit_section_(b, m, DEFINE_BLOCK, r);
    return r;
  }

  // WORD+
  private static boolean define_block_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_block_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, WORD);
    while (r) {
      int c = current_position_(b);
      if (!consumeToken(b, WORD)) break;
      if (!empty_element_parsed_guard_(b, "define_block_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // statement*
  private static boolean define_block_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_block_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "define_block_3", c)) break;
    }
    return true;
  }

  // CRLF?
  private static boolean define_block_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_block_5")) return false;
    consumeToken(b, CRLF);
    return true;
  }

  /* ********************************************************** */
  // (COMMENT CRLF)+
  public static boolean doc_comment_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_comment_block")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = doc_comment_block_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!doc_comment_block_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "doc_comment_block", c)) break;
    }
    exit_section_(b, m, DOC_COMMENT_BLOCK, r);
    return r;
  }

  // COMMENT CRLF
  private static boolean doc_comment_block_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "doc_comment_block_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMENT, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // doc_comment_block &(!CRLF) command_statement
  public static boolean documented_command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "documented_command")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = doc_comment_block(b, l + 1);
    r = r && documented_command_1(b, l + 1);
    r = r && command_statement(b, l + 1);
    exit_section_(b, m, DOCUMENTED_COMMAND, r);
    return r;
  }

  // &(!CRLF)
  private static boolean documented_command_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "documented_command_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = documented_command_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !CRLF
  private static boolean documented_command_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "documented_command_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, CRLF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // doc_comment_block CRLF
  public static boolean floating_comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "floating_comment")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = doc_comment_block(b, l + 1);
    r = r && consumeToken(b, CRLF);
    exit_section_(b, m, FLOATING_COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // statement*
  static boolean gdbFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gdbFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!statement(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "gdbFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // normal_command_line commands_block?
  static boolean normal_command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = normal_command_line(b, l + 1);
    p = r; // pin = 1
    r = r && normal_command_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // commands_block?
  private static boolean normal_command_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_1")) return false;
    commands_block(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // simple_command+
  //                                 ((IDENTIFIER | WORD) &(!CRLF))*
  //                                 (COMMENT)? CRLF
  static boolean normal_command_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = normal_command_line_0(b, l + 1);
    r = r && normal_command_line_1(b, l + 1);
    r = r && normal_command_line_2(b, l + 1);
    r = r && consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  // simple_command+
  private static boolean normal_command_line_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = simple_command(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!simple_command(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "normal_command_line_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // ((IDENTIFIER | WORD) &(!CRLF))*
  private static boolean normal_command_line_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!normal_command_line_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "normal_command_line_1", c)) break;
    }
    return true;
  }

  // (IDENTIFIER | WORD) &(!CRLF)
  private static boolean normal_command_line_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = normal_command_line_1_0_0(b, l + 1);
    r = r && normal_command_line_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // IDENTIFIER | WORD
  private static boolean normal_command_line_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, WORD);
    return r;
  }

  // &(!CRLF)
  private static boolean normal_command_line_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = normal_command_line_1_0_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !CRLF
  private static boolean normal_command_line_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, CRLF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (COMMENT)?
  private static boolean normal_command_line_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "normal_command_line_2")) return false;
    consumeToken(b, COMMENT);
    return true;
  }

  /* ********************************************************** */
  // set_print_command_line commands_block?
  static boolean set_print_command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command")) return false;
    if (!nextTokenIs(b, "", PRINT_KW, SET_KW)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = set_print_command_line(b, l + 1);
    p = r; // pin = 1
    r = r && set_print_command_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // commands_block?
  private static boolean set_print_command_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_1")) return false;
    commands_block(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (SET_KW | PRINT_KW)
  //                                    ((IDENTIFIER | REGISTER | OP_ASSIGN | COMMENT) &(!CRLF))*
  //                                     value_statement
  //                                    CRLF
  static boolean set_print_command_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line")) return false;
    if (!nextTokenIs(b, "", PRINT_KW, SET_KW)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = set_print_command_line_0(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, set_print_command_line_1(b, l + 1));
    r = p && report_error_(b, value_statement(b, l + 1)) && r;
    r = p && consumeToken(b, CRLF) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // SET_KW | PRINT_KW
  private static boolean set_print_command_line_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line_0")) return false;
    boolean r;
    r = consumeToken(b, SET_KW);
    if (!r) r = consumeToken(b, PRINT_KW);
    return r;
  }

  // ((IDENTIFIER | REGISTER | OP_ASSIGN | COMMENT) &(!CRLF))*
  private static boolean set_print_command_line_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!set_print_command_line_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "set_print_command_line_1", c)) break;
    }
    return true;
  }

  // (IDENTIFIER | REGISTER | OP_ASSIGN | COMMENT) &(!CRLF)
  private static boolean set_print_command_line_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = set_print_command_line_1_0_0(b, l + 1);
    r = r && set_print_command_line_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // IDENTIFIER | REGISTER | OP_ASSIGN | COMMENT
  private static boolean set_print_command_line_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, REGISTER);
    if (!r) r = consumeToken(b, OP_ASSIGN);
    if (!r) r = consumeToken(b, COMMENT);
    return r;
  }

  // &(!CRLF)
  private static boolean set_print_command_line_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = set_print_command_line_1_0_1_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !CRLF
  private static boolean set_print_command_line_1_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_print_command_line_1_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, CRLF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COMMAND_EXECUTION
  //          | COMMAND_BREAKPOINT
  //          | COMMAND_STACK
  //          | COMMAND_DATA
  //          | COMMAND_CONFIG
  //          | COMMAND_USER
  //          | COMMAND_GENERIC
  public static boolean simple_command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simple_command")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SIMPLE_COMMAND, "<simple command>");
    r = consumeToken(b, COMMAND_EXECUTION);
    if (!r) r = consumeToken(b, COMMAND_BREAKPOINT);
    if (!r) r = consumeToken(b, COMMAND_STACK);
    if (!r) r = consumeToken(b, COMMAND_DATA);
    if (!r) r = consumeToken(b, COMMAND_CONFIG);
    if (!r) r = consumeToken(b, COMMAND_USER);
    if (!r) r = consumeToken(b, COMMAND_GENERIC);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // comment_driven_statement | command_statement | CRLF
  static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    r = comment_driven_statement(b, l + 1);
    if (!r) r = command_statement(b, l + 1);
    if (!r) r = consumeToken(b, CRLF);
    return r;
  }

  /* ********************************************************** */
  // HEX_NUMBER | NUMBER | STRING | IDENTIFIER | WORD
  public static boolean value_statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "value_statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, VALUE_STATEMENT, "<value statement>");
    r = consumeToken(b, HEX_NUMBER);
    if (!r) r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, WORD);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}
