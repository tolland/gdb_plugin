package org.limepepper.lang.gdb.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.limepepper.lang.gdb.psi.GdbTypes.*;

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

DOC_BLOCK=document\n.*?(end\b)
PYTHON_BLOCK=python\n.*?(end\b)
GUILE_BLOCK=guile\n.*?(end\b)
COMMAND_EXECUTION=(run|r|start|continue|c|step|s|next|n|finish|until|u|kill|quit|q|detach)
COMMAND_BREAKPOINT=(b|br|bre|brea|break|tb|tbr|tbre|tbreak|hbreak|thbreak|rbreak|watch|rwatch|awatch|catch|delete|enable|disable|condition)
COMMAND_STACK=(backtrace|bt|frame|f|up|down|select-frame)
COMMAND_DATA=(display|output|printf|call|return|examine|info|i|show|list|l|disassemble|disas)
COMMAND_CONFIG=(unset|source|exec-file|symbol-file|core-file|target|attach|load)
WHITE_SPACE=[ \t]
COMMENT=#[^\r\n]*
LINE_CONTINUATION=\\\\(\r\n|\n|\r)
CRLF=(\r\n|\n|\r)
HEX_NUMBER=0x[0-9a-fA-F]+
REGISTER=\$[a-zA-Z_][a-zA-Z0-9_]*|\$[0-9]+
NUMBER=[0-9]+
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
STRING=\"([^\"\\]|\\.)*\"
DOUBLE_QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
WORD=[^#\s\\]+

%%
<YYINITIAL> {
  {WHITE_SPACE}                { return WHITE_SPACE; }

  "python"                     { return PYTHON_KW; }
  "guile"                      { return GUILE_KW; }
  "set"                        { return SET_KW; }
  "print"                      { return PRINT_KW; }
  "define"                     { return DEFINE; }
  "end"                        { return END; }
  "commands"                   { return COMMANDS; }
  "->"                         { return ARROW; }
  ":"                          { return COLON; }
  ","                          { return COMMA; }
  "."                          { return DOT; }
  "=="                         { return OP_EQUAL; }
  "="                          { return OP_ASSIGN; }
  "{"                          { return LBRACE; }
  "["                          { return LBRACKET; }
  "("                          { return LPAREN; }
  "/"                          { return OP_DIV; }
  "..."                        { return OP_ELLIPSIS; }
  ">="                         { return OP_GREATER_OR_EQUAL; }
  ">"                          { return OP_GREATER; }
  "<="                         { return OP_LESS_OR_EQUAL; }
  "<"                          { return OP_LESS; }
  "-"                          { return OP_MINUS; }
  "%"                          { return OP_MOD; }
  "*"                          { return OP_MUL; }
  "+"                          { return OP_PLUS; }
  "}"                          { return RBRACE; }
  "]"                          { return RBRACKET; }
  ")"                          { return RPAREN; }
  ";"                          { return SEMICOLON; }
  "'"                          { return SINGLE_QUOTE; }
  "::"                         { return SCOPE_RESOLUTION; }
  "COMMAND_USER"               { return COMMAND_USER; }

  {DOC_BLOCK}                  { return DOC_BLOCK; }
  {PYTHON_BLOCK}               { return PYTHON_BLOCK; }
  {GUILE_BLOCK}                { return GUILE_BLOCK; }
  {COMMAND_EXECUTION}          { return COMMAND_EXECUTION; }
  {COMMAND_BREAKPOINT}         { return COMMAND_BREAKPOINT; }
  {COMMAND_STACK}              { return COMMAND_STACK; }
  {COMMAND_DATA}               { return COMMAND_DATA; }
  {COMMAND_CONFIG}             { return COMMAND_CONFIG; }
  {WHITE_SPACE}                { return WHITE_SPACE; }
  {COMMENT}                    { return COMMENT; }
  {LINE_CONTINUATION}          { return LINE_CONTINUATION; }
  {CRLF}                       { return CRLF; }
  {HEX_NUMBER}                 { return HEX_NUMBER; }
  {REGISTER}                   { return REGISTER; }
  {NUMBER}                     { return NUMBER; }
  {IDENTIFIER}                 { return IDENTIFIER; }
  {STRING}                     { return STRING; }
  {DOUBLE_QUOTED_STRING}       { return DOUBLE_QUOTED_STRING; }
  {WORD}                       { return WORD; }

}

[^] { return BAD_CHARACTER; }
