package org.limepepper.gdb_plugin.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.limepepper.gdb_plugin.GdbTokenTypes;

%%

%class GdbLexer
%implements FlexLexer
%public
%unicode
%function advance
%type IElementType

// Character classes
WHITESPACE = [ \t\f]
NEWLINE = \r\n | \r | \n
DIGIT = [0-9]
HEX_DIGIT = [0-9a-fA-F]
LETTER = [a-zA-Z]
IDENTIFIER_CHAR = [a-zA-Z0-9_]

// Literals
NUMBER = {DIGIT}+
HEX_NUMBER = 0x{HEX_DIGIT}+
IDENTIFIER = ({LETTER}|_){IDENTIFIER_CHAR}*
REGISTER = \${IDENTIFIER_CHAR}+ | \${DIGIT}+
STRING = \"([^\"\\\r\n]|\\.)*\"

// Comments
COMMENT = #.*

// Execution control commands
EXECUTION_COMMANDS = "run"|"r"|"start"|"continue"|"c"|"step"|"s"|"next"|"n"|"finish"|"until"|"u"|"kill"|"quit"|"q"|"detach"

// Breakpoint commands  
BREAKPOINT_COMMANDS = "break"|"b"|"tbreak"|"hbreak"|"thbreak"|"rbreak"|"watch"|"rwatch"|"awatch"|"catch"|"delete"|"enable"|"disable"|"condition"

// Stack navigation commands
STACK_COMMANDS = "backtrace"|"bt"|"frame"|"f"|"up"|"down"|"select-frame"

// Data examination commands
DATA_COMMANDS = "print"|"p"|"display"|"output"|"printf"|"call"|"return"|"x"|"examine"|"info"|"i"|"show"|"list"|"l"|"disassemble"|"disas"

// Configuration commands
CONFIG_COMMANDS = "set"|"unset"|"source"|"file"|"exec-file"|"symbol-file"|"core-file"|"target"|"attach"|"load"

// User-defined command keywords
USER_COMMANDS = "define"|"document"|"end"|"if"|"else"|"while"|"loop_break"|"loop_continue"

%%

// Whitespace
{WHITESPACE}+ { return GdbTokenTypes.WHITESPACE; }
{NEWLINE} { return GdbTokenTypes.NEWLINE; }

// Comments
{COMMENT} { return GdbTokenTypes.COMMENT; }

// Commands - categorized for syntax highlighting
{EXECUTION_COMMANDS} { return GdbTokenTypes.COMMAND_EXECUTION; }
{BREAKPOINT_COMMANDS} { return GdbTokenTypes.COMMAND_BREAKPOINT; }
{STACK_COMMANDS} { return GdbTokenTypes.COMMAND_STACK; }
{DATA_COMMANDS} { return GdbTokenTypes.COMMAND_DATA; }
{CONFIG_COMMANDS} { return GdbTokenTypes.COMMAND_CONFIG; }
{USER_COMMANDS} { return GdbTokenTypes.COMMAND_USER; }

// Special condition keyword
"if" { return GdbTokenTypes.CONDITION_IF; }

// Literals
{HEX_NUMBER} { return GdbTokenTypes.HEX_NUMBER; }
{NUMBER} { return GdbTokenTypes.NUMBER; }
{STRING} { return GdbTokenTypes.STRING; }
{REGISTER} { return GdbTokenTypes.REGISTER; }

// Operators - grouped by precedence and type
"==" | "!=" | "<=" | ">=" | "<" | ">" { return GdbTokenTypes.OPERATOR; }
"&&" | "||" | "!" { return GdbTokenTypes.OPERATOR; }
"<<" | ">>" | "&" | "|" | "^" | "~" { return GdbTokenTypes.OPERATOR; }
"+" | "-" | "*" | "/" | "%" { return GdbTokenTypes.OPERATOR; }

// Assignment operators
"=" | "+=" | "-=" | "*=" | "/=" { return GdbTokenTypes.ASSIGNMENT; }

// Address marker (dereference)
"*" { return GdbTokenTypes.ADDRESS_MARKER; }

// Punctuation
"(" { return GdbTokenTypes.LPAREN; }
")" { return GdbTokenTypes.RPAREN; }
"[" { return GdbTokenTypes.LBRACKET; }
"]" { return GdbTokenTypes.RBRACKET; }
"{" { return GdbTokenTypes.LBRACE; }
"}" { return GdbTokenTypes.RBRACE; }
"," { return GdbTokenTypes.COMMA; }
";" { return GdbTokenTypes.SEMICOLON; }
":" { return GdbTokenTypes.COLON; }
"." { return GdbTokenTypes.DOT; }
"->" { return GdbTokenTypes.ARROW; }
"::" { return GdbTokenTypes.SCOPE_RESOLUTION; }

// Generic identifiers (functions, variables, symbols)
{IDENTIFIER} { return GdbTokenTypes.IDENTIFIER; }

// Anything else is a bad character
. { return GdbTokenTypes.BAD_CHARACTER; }