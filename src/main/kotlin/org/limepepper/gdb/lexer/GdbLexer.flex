package org.limepepper.gdb.lexer;

import com.intellij.lexer.FlexLexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.limepepper.gdb.psi.GdbTypes.*;

%%

%{
  public GdbLexer() {
    this((java.io.Reader)null);
  }
%}

%class GdbLexer
%implements FlexLexer
%public
%unicode
%function advance
%type IElementType

COMMAND_EXECUTION=(run|r|start|continue|c|step|s|next|n|finish|until|u|kill|quit|q|detach)
COMMAND_BREAKPOINT=(b|br|bre|brea|break|tbreak|hbreak|thbreak|rbreak|watch|rwatch|awatch|catch|delete|enable|disable|condition)
COMMAND_STACK=(backtrace|bt|frame|f|up|down|select-frame)
COMMAND_DATA=(display|output|printf|call|return|examine|info|i|show|list|l|disassemble|disas)
COMMAND_CONFIG=(set|unset|source|exec-file|symbol-file|core-file|target|attach|load)
WHITESPACE=[ \t]
COMMENT=#[^\r\n]*
LINE_CONTINUATION=\\(\r\n|\n|\r)
CRLF=(\r\n|\n|\r)
HEX_NUMBER=0x[0-9a-fA-F]+
REGISTER=\$[a-zA-Z_][a-zA-Z0-9_]*|\$[0-9]+
NUMBER=[0-9]+
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
STRING=\"([^\"\\]|\\.)*\"
WORD=[^#\s\\]+

%%
<YYINITIAL> {

  "define"                   { return DEFINE; }
  "end"                      { return END; }
  "commands"                 { return COMMANDS; }
  "set"                      { return SET_KW; }
  "print"                    { return PRINT_KW; }
  "("                        { return LPAREN; }
  ")"                        { return RPAREN; }
  "["                        { return LBRACKET; }
  "]"                        { return RBRACKET; }
  "{"                        { return LBRACE; }
  "}"                        { return RBRACE; }
  "="                        { return EQUALS; }
  ","                        { return COMMA; }
  ";"                        { return SEMICOLON; }
  ":"                        { return COLON; }
  "."                        { return DOT; }
  "->"                       { return ARROW; }
  "::"                       { return SCOPE_RESOLUTION; }
  "COMMAND_USER"             { return COMMAND_USER; }

  {COMMAND_EXECUTION}        { return COMMAND_EXECUTION; }
  {COMMAND_BREAKPOINT}       { return COMMAND_BREAKPOINT; }
  {COMMAND_STACK}            { return COMMAND_STACK; }
  {COMMAND_DATA}             { return COMMAND_DATA; }
  {COMMAND_CONFIG}           { return COMMAND_CONFIG; }
  {COMMENT}                  { return COMMENT; }
  {WHITESPACE}+              { return TokenType.WHITE_SPACE; }
  {LINE_CONTINUATION}        { }
  {CRLF}                     { return CRLF; }
  {HEX_NUMBER}               { return HEX_NUMBER; }
  {REGISTER}                 { return REGISTER; }
  {NUMBER}                   { return NUMBER; }
  {IDENTIFIER}               { return IDENTIFIER; }
  {STRING}                   { return STRING; }
  {WORD}                     { return WORD; }

}

[^] { return BAD_CHARACTER; }
