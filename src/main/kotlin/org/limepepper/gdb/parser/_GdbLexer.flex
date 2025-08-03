package org.limepepper.gdb.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.limepepper.gdb.psi.GdbTypes.*;

%%

%{
  public _GdbLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _GdbLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITESPACE=[ \t\n\x0B\f\r]+
NEWLINE=\r\n|\r|\n
COMMENT=#.*
COMMAND_EXECUTION=(run|r|start|continue|c|step|s|next|n|finish|until|u|kill|quit|q|detach)
COMMAND_BREAKPOINT=(tbreak|hbreak|thbreak|rbreak|watch|rwatch|awatch|catch|delete|enable|disable|condition)
COMMAND_STACK=(backtrace|bt|frame|f|up|down|select-frame)
COMMAND_DATA=(display|output|printf|call|return|examine|info|i|show|list|l|disassemble|disas)
COMMAND_CONFIG=(set|unset|source|file|exec-file|symbol-file|core-file|target|attach|load)
COMMAND_USER=(define|document|end|if|else|while|loop_break|loop_continue)
FORMAT_SPEC="/"[0-9]*[xotducsiaf]?[bhwg]?
NUMBER=[0-9]+
HEX_NUMBER=0x[0-9a-fA-F]+
STRING=\"([^\"\\]|\\.)*\"
REGISTER=\$[a-zA-Z_][a-zA-Z0-9_]*|\$[0-9]+
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
OPERATOR=(==|!=|<=|>=|<|>|&&|\|\|!|<<|>>|[+\-*/%&|^~])
ASSIGNMENT=(=|\+=|-=|\*=|"/"=)

%%
<YYINITIAL> {
  {WHITE_SPACE}              { return WHITE_SPACE; }

  "x"                        { return X_CMD; }
  "print"                    { return PRINT_CMD; }
  "p"                        { return P_CMD; }
  "break"                    { return BREAK_CMD; }
  "b"                        { return B_CMD; }
  "*"                        { return ADDRESS_MARKER; }
  "("                        { return LPAREN; }
  ")"                        { return RPAREN; }
  "["                        { return LBRACKET; }
  "]"                        { return RBRACKET; }
  "{"                        { return LBRACE; }
  "}"                        { return RBRACE; }
  ","                        { return COMMA; }
  ";"                        { return SEMICOLON; }
  ":"                        { return COLON; }
  "."                        { return DOT; }
  "->"                       { return ARROW; }
  "::"                       { return SCOPE_RESOLUTION; }
  "if"                       { return CONDITION_IF; }

  {WHITESPACE}               { return WHITESPACE; }
  {NEWLINE}                  { return NEWLINE; }
  {COMMENT}                  { return COMMENT; }
  {COMMAND_EXECUTION}        { return COMMAND_EXECUTION; }
  {COMMAND_BREAKPOINT}       { return COMMAND_BREAKPOINT; }
  {COMMAND_STACK}            { return COMMAND_STACK; }
  {COMMAND_DATA}             { return COMMAND_DATA; }
  {COMMAND_CONFIG}           { return COMMAND_CONFIG; }
  {COMMAND_USER}             { return COMMAND_USER; }
  {FORMAT_SPEC}              { return FORMAT_SPEC; }
  {NUMBER}                   { return NUMBER; }
  {HEX_NUMBER}               { return HEX_NUMBER; }
  {STRING}                   { return STRING; }
  {REGISTER}                 { return REGISTER; }
  {IDENTIFIER}               { return IDENTIFIER; }
  {OPERATOR}                 { return OPERATOR; }
  {ASSIGNMENT}               { return ASSIGNMENT; }

}

[^] { return BAD_CHARACTER; }
