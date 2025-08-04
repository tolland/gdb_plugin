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
  // (value | expression | IDENTIFIER)+
  public static boolean arguments(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arguments")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ARGUMENTS, "<arguments>");
    result_ = arguments_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!arguments_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "arguments", pos_)) break;
    }
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // value | expression | IDENTIFIER
  private static boolean arguments_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "arguments_0")) return false;
    boolean result_;
    result_ = value(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

  /* ********************************************************** */
  // SET_CMD assignment_target assignment_value
  public static boolean assignment(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment")) return false;
    if (!nextTokenIs(builder_, SET_CMD)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, SET_CMD);
    result_ = result_ && assignment_target(builder_, level_ + 1);
    result_ = result_ && assignment_value(builder_, level_ + 1);
    exit_section_(builder_, marker_, ASSIGNMENT, result_);
    return result_;
  }

  /* ********************************************************** */
  // variable_assignment_target |
  //     simple_assignment_target |
  //     subcommand_assignment_target |
  //     memory_assignment_target
  public static boolean assignment_target(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_target")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ASSIGNMENT_TARGET, "<assignment target>");
    result_ = variable_assignment_target(builder_, level_ + 1);
    if (!result_) result_ = simple_assignment_target(builder_, level_ + 1);
    if (!result_) result_ = subcommand_assignment_target(builder_, level_ + 1);
    if (!result_) result_ = memory_assignment_target(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // value | expression
  public static boolean assignment_value(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "assignment_value")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, ASSIGNMENT_VALUE, "<assignment value>");
    result_ = value(builder_, level_ + 1);
    if (!result_) result_ = expression(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // WHITESPACE | NEWLINE
  public static boolean blank(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "blank")) return false;
    if (!nextTokenIs(builder_, "<blank>", NEWLINE, WHITESPACE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BLANK, "<blank>");
    result_ = consumeToken(builder_, WHITESPACE);
    if (!result_) result_ = consumeToken(builder_, NEWLINE);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ('break' | 'b') IDENTIFIER ( 'if' condition )?
  public static boolean breakpoint(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "breakpoint")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, BREAKPOINT, "<breakpoint>");
    result_ = breakpoint_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, IDENTIFIER);
    result_ = result_ && breakpoint_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // 'break' | 'b'
  private static boolean breakpoint_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "breakpoint_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, "break");
    if (!result_) result_ = consumeToken(builder_, "b");
    return result_;
  }

  // ( 'if' condition )?
  private static boolean breakpoint_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "breakpoint_2")) return false;
    breakpoint_2_0(builder_, level_ + 1);
    return true;
  }

  // 'if' condition
  private static boolean breakpoint_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "breakpoint_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, CONDITION_IF);
    result_ = result_ && condition(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // 'commands' NEWLINE command_line* 'end'
  public static boolean command_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_block")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, COMMAND_BLOCK, "<command block>");
    result_ = consumeToken(builder_, "commands");
    result_ = result_ && consumeToken(builder_, NEWLINE);
    result_ = result_ && command_block_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "end");
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // command_line*
  private static boolean command_block_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_block_2")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!command_line(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "command_block_2", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // command_name arguments?
  public static boolean command_line(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_line")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = command_name(builder_, level_ + 1);
    result_ = result_ && command_line_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, COMMAND_LINE, result_);
    return result_;
  }

  // arguments?
  private static boolean command_line_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_line_1")) return false;
    arguments(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean command_name(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "command_name")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    exit_section_(builder_, marker_, COMMAND_NAME, result_);
    return result_;
  }

  /* ********************************************************** */
  // COMMENT
  static boolean comment(PsiBuilder builder_, int level_) {
    return consumeToken(builder_, COMMENT);
  }

  /* ********************************************************** */
  // expression
  public static boolean condition(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "condition")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = expression(builder_, level_ + 1);
    exit_section_(builder_, marker_, CONDITION, result_);
    return result_;
  }

  /* ********************************************************** */
  // IDENTIFIER (GREATER | LESS | EQUALS) value
  public static boolean expression(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    result_ = result_ && expression_1(builder_, level_ + 1);
    result_ = result_ && value(builder_, level_ + 1);
    exit_section_(builder_, marker_, EXPRESSION, result_);
    return result_;
  }

  // GREATER | LESS | EQUALS
  private static boolean expression_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "expression_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, GREATER);
    if (!result_) result_ = consumeToken(builder_, LESS);
    if (!result_) result_ = consumeToken(builder_, EQUALS);
    return result_;
  }

  /* ********************************************************** */
  // item*
  static boolean gdbFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "gdbFile")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!item(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "gdbFile", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // statement | comment | blank
  static boolean item(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "item")) return false;
    boolean result_;
    result_ = statement(builder_, level_ + 1);
    if (!result_) result_ = comment(builder_, level_ + 1);
    if (!result_) result_ = blank(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // ASTERISK LPAREN IDENTIFIER ASTERISK RPAREN (NUMBER | HEX_NUMBER) EQUALS |
  //     LBRACE IDENTIFIER RBRACE (NUMBER | HEX_NUMBER) EQUALS
  public static boolean memory_assignment_target(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "memory_assignment_target")) return false;
    if (!nextTokenIs(builder_, "<memory assignment target>", ASTERISK, LBRACE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, MEMORY_ASSIGNMENT_TARGET, "<memory assignment target>");
    result_ = memory_assignment_target_0(builder_, level_ + 1);
    if (!result_) result_ = memory_assignment_target_1(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // ASTERISK LPAREN IDENTIFIER ASTERISK RPAREN (NUMBER | HEX_NUMBER) EQUALS
  private static boolean memory_assignment_target_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "memory_assignment_target_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, ASTERISK, LPAREN, IDENTIFIER, ASTERISK, RPAREN);
    result_ = result_ && memory_assignment_target_0_5(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQUALS);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | HEX_NUMBER
  private static boolean memory_assignment_target_0_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "memory_assignment_target_0_5")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, HEX_NUMBER);
    return result_;
  }

  // LBRACE IDENTIFIER RBRACE (NUMBER | HEX_NUMBER) EQUALS
  private static boolean memory_assignment_target_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "memory_assignment_target_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LBRACE, IDENTIFIER, RBRACE);
    result_ = result_ && memory_assignment_target_1_3(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQUALS);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // NUMBER | HEX_NUMBER
  private static boolean memory_assignment_target_1_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "memory_assignment_target_1_3")) return false;
    boolean result_;
    result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, HEX_NUMBER);
    return result_;
  }

  /* ********************************************************** */
  // '\n'*
  static boolean newlines(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "newlines")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!consumeToken(builder_, "\\n")) break;
      if (!empty_element_parsed_guard_(builder_, "newlines", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER EQUALS
  public static boolean simple_assignment_target(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "simple_assignment_target")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, IDENTIFIER, EQUALS);
    exit_section_(builder_, marker_, SIMPLE_ASSIGNMENT_TARGET, result_);
    return result_;
  }

  /* ********************************************************** */
  // assignment | breakpoint | command_block | command_line
  public static boolean statement(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "statement")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, STATEMENT, "<statement>");
    result_ = assignment(builder_, level_ + 1);
    if (!result_) result_ = breakpoint(builder_, level_ + 1);
    if (!result_) result_ = command_block(builder_, level_ + 1);
    if (!result_) result_ = command_line(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // IDENTIFIER IDENTIFIER?
  public static boolean subcommand_assignment_target(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subcommand_assignment_target")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    result_ = result_ && subcommand_assignment_target_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, SUBCOMMAND_ASSIGNMENT_TARGET, result_);
    return result_;
  }

  // IDENTIFIER?
  private static boolean subcommand_assignment_target_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "subcommand_assignment_target_1")) return false;
    consumeToken(builder_, IDENTIFIER);
    return true;
  }

  /* ********************************************************** */
  // VARIABLE | NUMBER | HEX_NUMBER | STRING | IDENTIFIER
  public static boolean value(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "value")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VALUE, "<value>");
    result_ = consumeToken(builder_, VARIABLE);
    if (!result_) result_ = consumeToken(builder_, NUMBER);
    if (!result_) result_ = consumeToken(builder_, HEX_NUMBER);
    if (!result_) result_ = consumeToken(builder_, STRING);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ('var' | 'variable') (VARIABLE | IDENTIFIER) EQUALS
  public static boolean variable_assignment_target(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variable_assignment_target")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, VARIABLE_ASSIGNMENT_TARGET, "<variable assignment target>");
    result_ = variable_assignment_target_0(builder_, level_ + 1);
    result_ = result_ && variable_assignment_target_1(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, EQUALS);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // 'var' | 'variable'
  private static boolean variable_assignment_target_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variable_assignment_target_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, "var");
    if (!result_) result_ = consumeToken(builder_, "variable");
    return result_;
  }

  // VARIABLE | IDENTIFIER
  private static boolean variable_assignment_target_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "variable_assignment_target_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, VARIABLE);
    if (!result_) result_ = consumeToken(builder_, IDENTIFIER);
    return result_;
  }

}
