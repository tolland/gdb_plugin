// This is a generated file. Not intended for manual editing.
package org.limepepper.gdb_plugin.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.limepepper.gdb_plugin.psi.GdbTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
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
  // multiplicative_expr (('+' | '-') multiplicative_expr)*
  public static boolean additive_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ADDITIVE_EXPR, "<additive expr>");
    r = multiplicative_expr(b, l + 1);
    r = r && additive_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('+' | '-') multiplicative_expr)*
  private static boolean additive_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!additive_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "additive_expr_1", c)) break;
    }
    return true;
  }

  // ('+' | '-') multiplicative_expr
  private static boolean additive_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = additive_expr_1_0_0(b, l + 1);
    r = r && multiplicative_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '+' | '-'
  private static boolean additive_expr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "additive_expr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, "+");
    if (!r) r = consumeToken(b, "-");
    return r;
  }

  /* ********************************************************** */
  // ADDRESS_MARKER (HEX_NUMBER | IDENTIFIER | LPAREN expression RPAREN)
  public static boolean address(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address")) return false;
    if (!nextTokenIs(b, ADDRESS_MARKER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ADDRESS_MARKER);
    r = r && address_1(b, l + 1);
    exit_section_(b, m, ADDRESS, r);
    return r;
  }

  // HEX_NUMBER | IDENTIFIER | LPAREN expression RPAREN
  private static boolean address_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HEX_NUMBER);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = address_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // LPAREN expression RPAREN
  private static boolean address_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "address_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // expression
  //           | STRING
  //           | IDENTIFIER
  //           | address
  //           | register_ref
  public static boolean argument(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "argument")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARGUMENT, "<argument>");
    r = expression(b, l + 1);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = address(b, l + 1);
    if (!r) r = register_ref(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (IDENTIFIER | register_ref) LBRACKET expression RBRACKET
  public static boolean array_access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_access")) return false;
    if (!nextTokenIs(b, "<array access>", IDENTIFIER, REGISTER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ARRAY_ACCESS, "<array access>");
    r = array_access_0(b, l + 1);
    r = r && consumeToken(b, LBRACKET);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // IDENTIFIER | register_ref
  private static boolean array_access_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_access_0")) return false;
    boolean r;
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = register_ref(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // simple_command
  //                      | formatted_command
  public static boolean command_with_format(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "command_with_format")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, COMMAND_WITH_FORMAT, "<command with format>");
    r = simple_command(b, l + 1);
    if (!r) r = formatted_command(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // COMMENT
  public static boolean comment_line(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment_line")) return false;
    if (!nextTokenIs(b, COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMENT);
    exit_section_(b, m, COMMENT_LINE, r);
    return r;
  }

  /* ********************************************************** */
  // CONDITION_IF expression
  public static boolean condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "condition")) return false;
    if (!nextTokenIs(b, CONDITION_IF)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CONDITION_IF);
    r = r && expression(b, l + 1);
    exit_section_(b, m, CONDITION, r);
    return r;
  }

  /* ********************************************************** */
  // relational_expr (('==' | '!=') relational_expr)*
  public static boolean equality_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equality_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EQUALITY_EXPR, "<equality expr>");
    r = relational_expr(b, l + 1);
    r = r && equality_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('==' | '!=') relational_expr)*
  private static boolean equality_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equality_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!equality_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "equality_expr_1", c)) break;
    }
    return true;
  }

  // ('==' | '!=') relational_expr
  private static boolean equality_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equality_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = equality_expr_1_0_0(b, l + 1);
    r = r && relational_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '==' | '!='
  private static boolean equality_expr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equality_expr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, "==");
    if (!r) r = consumeToken(b, "!=");
    return r;
  }

  /* ********************************************************** */
  // logical_or_expr
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EXPRESSION, "<expression>");
    r = logical_or_expr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // (X_CMD | PRINT_CMD | P_CMD) FORMAT_SPEC?
  //                    | (BREAK_CMD | B_CMD) ADDRESS_MARKER?
  public static boolean formatted_command(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FORMATTED_COMMAND, "<formatted command>");
    r = formatted_command_0(b, l + 1);
    if (!r) r = formatted_command_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (X_CMD | PRINT_CMD | P_CMD) FORMAT_SPEC?
  private static boolean formatted_command_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = formatted_command_0_0(b, l + 1);
    r = r && formatted_command_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // X_CMD | PRINT_CMD | P_CMD
  private static boolean formatted_command_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command_0_0")) return false;
    boolean r;
    r = consumeToken(b, X_CMD);
    if (!r) r = consumeToken(b, PRINT_CMD);
    if (!r) r = consumeToken(b, P_CMD);
    return r;
  }

  // FORMAT_SPEC?
  private static boolean formatted_command_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command_0_1")) return false;
    consumeToken(b, FORMAT_SPEC);
    return true;
  }

  // (BREAK_CMD | B_CMD) ADDRESS_MARKER?
  private static boolean formatted_command_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = formatted_command_1_0(b, l + 1);
    r = r && formatted_command_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // BREAK_CMD | B_CMD
  private static boolean formatted_command_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command_1_0")) return false;
    boolean r;
    r = consumeToken(b, BREAK_CMD);
    if (!r) r = consumeToken(b, B_CMD);
    return r;
  }

  // ADDRESS_MARKER?
  private static boolean formatted_command_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "formatted_command_1_1")) return false;
    consumeToken(b, ADDRESS_MARKER);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER LPAREN (expression (COMMA expression)*)? RPAREN
  public static boolean function_call(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, LPAREN);
    r = r && function_call_2(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, FUNCTION_CALL, r);
    return r;
  }

  // (expression (COMMA expression)*)?
  private static boolean function_call_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_2")) return false;
    function_call_2_0(b, l + 1);
    return true;
  }

  // expression (COMMA expression)*
  private static boolean function_call_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = expression(b, l + 1);
    r = r && function_call_2_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA expression)*
  private static boolean function_call_2_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_2_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!function_call_2_0_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "function_call_2_0_1", c)) break;
    }
    return true;
  }

  // COMMA expression
  private static boolean function_call_2_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "function_call_2_0_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item*
  static boolean gdbFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gdbFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "gdbFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // statement | comment_line | NEWLINE
  static boolean item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item")) return false;
    boolean r;
    r = statement(b, l + 1);
    if (!r) r = comment_line(b, l + 1);
    if (!r) r = consumeToken(b, NEWLINE);
    return r;
  }

  /* ********************************************************** */
  // equality_expr ('&&' equality_expr)*
  public static boolean logical_and_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_and_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LOGICAL_AND_EXPR, "<logical and expr>");
    r = equality_expr(b, l + 1);
    r = r && logical_and_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('&&' equality_expr)*
  private static boolean logical_and_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_and_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!logical_and_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "logical_and_expr_1", c)) break;
    }
    return true;
  }

  // '&&' equality_expr
  private static boolean logical_and_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_and_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "&&");
    r = r && equality_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // logical_and_expr ('||' logical_and_expr)*
  public static boolean logical_or_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_or_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LOGICAL_OR_EXPR, "<logical or expr>");
    r = logical_and_expr(b, l + 1);
    r = r && logical_or_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('||' logical_and_expr)*
  private static boolean logical_or_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_or_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!logical_or_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "logical_or_expr_1", c)) break;
    }
    return true;
  }

  // '||' logical_and_expr
  private static boolean logical_or_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "logical_or_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "||");
    r = r && logical_and_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (IDENTIFIER | register_ref) (DOT | ARROW) IDENTIFIER
  public static boolean member_access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "member_access")) return false;
    if (!nextTokenIs(b, "<member access>", IDENTIFIER, REGISTER)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MEMBER_ACCESS, "<member access>");
    r = member_access_0(b, l + 1);
    r = r && member_access_1(b, l + 1);
    r = r && consumeToken(b, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // IDENTIFIER | register_ref
  private static boolean member_access_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "member_access_0")) return false;
    boolean r;
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = register_ref(b, l + 1);
    return r;
  }

  // DOT | ARROW
  private static boolean member_access_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "member_access_1")) return false;
    boolean r;
    r = consumeToken(b, DOT);
    if (!r) r = consumeToken(b, ARROW);
    return r;
  }

  /* ********************************************************** */
  // unary_expr (('*' | '/' | '%') unary_expr)*
  public static boolean multiplicative_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiplicative_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MULTIPLICATIVE_EXPR, "<multiplicative expr>");
    r = unary_expr(b, l + 1);
    r = r && multiplicative_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('*' | '/' | '%') unary_expr)*
  private static boolean multiplicative_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiplicative_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!multiplicative_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "multiplicative_expr_1", c)) break;
    }
    return true;
  }

  // ('*' | '/' | '%') unary_expr
  private static boolean multiplicative_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiplicative_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = multiplicative_expr_1_0_0(b, l + 1);
    r = r && unary_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '*' | '/' | '%'
  private static boolean multiplicative_expr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multiplicative_expr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, ADDRESS_MARKER);
    if (!r) r = consumeToken(b, "/");
    if (!r) r = consumeToken(b, "%");
    return r;
  }

  /* ********************************************************** */
  // NUMBER
  //               | HEX_NUMBER
  //               | STRING
  //               | register_ref
  //               | function_call
  //               | member_access
  //               | array_access
  //               | IDENTIFIER
  //               | LPAREN expression RPAREN
  public static boolean primary_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRIMARY_EXPR, "<primary expr>");
    r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, HEX_NUMBER);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = register_ref(b, l + 1);
    if (!r) r = function_call(b, l + 1);
    if (!r) r = member_access(b, l + 1);
    if (!r) r = array_access(b, l + 1);
    if (!r) r = consumeToken(b, IDENTIFIER);
    if (!r) r = primary_expr_8(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // LPAREN expression RPAREN
  private static boolean primary_expr_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "primary_expr_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && expression(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // REGISTER
  public static boolean register_ref(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "register_ref")) return false;
    if (!nextTokenIs(b, REGISTER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, REGISTER);
    exit_section_(b, m, REGISTER_REF, r);
    return r;
  }

  /* ********************************************************** */
  // additive_expr (('<' | '>' | '<=' | '>=') additive_expr)*
  public static boolean relational_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relational_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, RELATIONAL_EXPR, "<relational expr>");
    r = additive_expr(b, l + 1);
    r = r && relational_expr_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (('<' | '>' | '<=' | '>=') additive_expr)*
  private static boolean relational_expr_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relational_expr_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!relational_expr_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "relational_expr_1", c)) break;
    }
    return true;
  }

  // ('<' | '>' | '<=' | '>=') additive_expr
  private static boolean relational_expr_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relational_expr_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = relational_expr_1_0_0(b, l + 1);
    r = r && additive_expr(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // '<' | '>' | '<=' | '>='
  private static boolean relational_expr_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "relational_expr_1_0_0")) return false;
    boolean r;
    r = consumeToken(b, "<");
    if (!r) r = consumeToken(b, ">");
    if (!r) r = consumeToken(b, "<=");
    if (!r) r = consumeToken(b, ">=");
    return r;
  }

  /* ********************************************************** */
  // COMMAND_EXECUTION
  //          | COMMAND_BREAKPOINT  
  //          | COMMAND_STACK
  //          | COMMAND_DATA
  //          | COMMAND_CONFIG
  //          | COMMAND_USER
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
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // command_with_format argument* condition?
  public static boolean statement(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, STATEMENT, "<statement>");
    r = command_with_format(b, l + 1);
    r = r && statement_1(b, l + 1);
    r = r && statement_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // argument*
  private static boolean statement_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!argument(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "statement_1", c)) break;
    }
    return true;
  }

  // condition?
  private static boolean statement_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "statement_2")) return false;
    condition(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ('!' | '-' | '+' | ADDRESS_MARKER)? primary_expr
  public static boolean unary_expr(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, UNARY_EXPR, "<unary expr>");
    r = unary_expr_0(b, l + 1);
    r = r && primary_expr(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ('!' | '-' | '+' | ADDRESS_MARKER)?
  private static boolean unary_expr_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr_0")) return false;
    unary_expr_0_0(b, l + 1);
    return true;
  }

  // '!' | '-' | '+' | ADDRESS_MARKER
  private static boolean unary_expr_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_expr_0_0")) return false;
    boolean r;
    r = consumeToken(b, "!");
    if (!r) r = consumeToken(b, "-");
    if (!r) r = consumeToken(b, "+");
    if (!r) r = consumeToken(b, ADDRESS_MARKER);
    return r;
  }

}
