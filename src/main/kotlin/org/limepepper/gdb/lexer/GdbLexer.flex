package org.limepepper.gdb.lexer;

import com.intellij.lexer.FlexLexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;
import com.intellij.util.containers.Stack;
import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.limepepper.gdb.psi.GdbTypes.*;

%%

%public
%class GdbLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

// Basic patterns
WHITE_SPACE=[ \t]+
CRLF=(\r\n|\n|\r)
COMMENT=#[^\r\n\\]*
COMMENT_WITH_CONTINUATION=#[^\r\n]*\\{CRLF}
LINE_CONTINUATION=\\{CRLF}

// Numbers and identifiers
HEX_NUMBER=0x[0-9a-fA-F]+
NUMBER=[0-9]+
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
REGISTER=\$([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)

// Commands - these need to be whole words to avoid conflicts
DEFINE=define
END=end
COMMANDS=commands
SET=set
PRINT=print
PYTHON_KW=python
GUILE_KW=guile
DOC_KW=document

// Command categories - match as whole words only
COMMAND_EXECUTION=(r|run|start|c|continue|s|step|n|next|finish|until|kill|q|quit|detach)
COMMAND_BREAKPOINT_SHORT=(b|br|bre|brea)
COMMAND_BREAKPOINT_LONG=(break|tbreak|hbreak|thbreak|rbreak|watch|rwatch|awatch|catch|delete|enable|disable|condition)
COMMAND_STACK=(backtrace|bt|f|frame|up|down|select-frame)
COMMAND_DATA=(display|output|printf|call|return|examine|i|info|show|l|list|disassemble|disas)
COMMAND_CONFIG=(unset|source|exec-file|symbol-file|core-file|target|attach|load)

// String content - everything except quotes, backslashes, and line endings
STRING_CONTENT=[^\"\\\r\n]+
ESCAPE_SEQUENCE=\\[^\r\n]

// General word - anything that's not whitespace, comment, or special chars
WORD=[^#\s\"\\(){}\[\]=,;:.>-]+

%state STATE_D_STRING
%state STATE_PYTHON_BLOCK
%state STATE_GUILE_BLOCK
%state STATE_DOC_BLOCK
%state STATE_ARGS_BLOCK
%state STATE_DEFINE_BODY
%state STATE_COMMANDS_LIST
%state STATE_COMMENT_CONTINUATION

%{
    public GdbLexer() {
      this((java.io.Reader)null);
    }

    // track state for popping back (from handlebars.flex)
    private Stack<Integer> stack = new Stack<>();

    public void yypushState(int newState) {
      stack.push(yystate());
      yybegin(newState);
    }

    public void yypopState() {
      yybegin(stack.pop());
    }

    private IElementType finishDoubleString() {
        yypopState();
        return DOUBLE_QUOTED_STRING;
    }

    private IElementType finishPythonBlock() {
        yypopState();
        return PYTHON_BLOCK;
    }

    private IElementType finishGuileBlock() {
        yypopState();
        return GUILE_BLOCK;
    }

    private IElementType finishDocBlock() {
        yypopState();
        return DOC_BLOCK;
    }

    private IElementType finishArgsBlock() {
        yypopState();
        return ARGS_BLOCK;
    }

    private void pushbackEOL() {
        int eolLength = 0;
        if (yylength() > 0) {
            char last = yycharat(yylength() - 1);
            if (last == '\n') {
                eolLength = (yylength() > 1 && yycharat(yylength() - 2) == '\r') ? 2 : 1;
            } else if (last == '\r') {
                eolLength = 1;
            }
        }
        if (eolLength > 0) {
            yypushback(eolLength);
        }
    }

    // Check if we're at start of line or after whitespace (for command recognition)
    private boolean isCommandContext() {
        if (zzStartRead == 0) return true; // Start of file

        // Look backwards to see if we're after whitespace or newline
        for (int i = zzStartRead - 1; i >= 0; i--) {
            char c = zzBuffer.charAt(i);
            if (c == '\n' || c == '\r') return true;
            if (c != ' ' && c != '\t') return false;
        }
        return true;
    }
%}


%%

<STATE_D_STRING> {
    \"                  { return finishDoubleString(); }
    {ESCAPE_SEQUENCE}   { /* consume escape sequence */ }
    \\{CRLF}           { /* line continuation - consume but don't end string */ }
    {STRING_CONTENT}    { /* consume string content */ }
    {CRLF}             { pushbackEOL(); return finishDoubleString(); }
    <<EOF>>            { return finishDoubleString(); }
    [^]                { /* consume any other character */ }
}

// Python block handling - consume everything until 'end' at start of line
<STATE_PYTHON_BLOCK> {
    ^end / [^a-zA-Z0-9_] { return finishPythonBlock(); }
    ^end{CRLF}          { return finishPythonBlock(); }
    ^end                { return finishPythonBlock(); }
    [^]                 { /* consume any character */ }
    <<EOF>>             { return finishPythonBlock(); }
}

// Guile block handling - consume everything until 'end' at start of line
<STATE_GUILE_BLOCK> {
    ^end / [^a-zA-Z0-9_] { return finishGuileBlock(); }
    ^end{CRLF}          { return finishGuileBlock(); }
    ^end                { return finishGuileBlock(); }
    [^]                 { /* consume any character */ }
    <<EOF>>             { return finishGuileBlock(); }
}

// user function document block handling - consume everything until 'end' at start of line
<STATE_DOC_BLOCK> {
    ^end / [^a-zA-Z0-9_] { return finishDocBlock(); }
    ^end{CRLF}          { return finishDocBlock(); }
    ^end                { return finishDocBlock(); }
    [^]                 { /* consume any character */ }
    <<EOF>>             { return finishDocBlock(); }
}

// processing the args, subcommands and options of a command
// This is generic args processing, when no more specific one is available
<STATE_ARGS_BLOCK> {

    // String start transitions
    \"                  { yypushState(STATE_D_STRING); }

    // Check for end keyword to pop back
    {END}               { yypopState(); return END; }
    {CRLF}              { yypopState(); return CRLF; }

    // Whitespace and comments
    {WHITE_SPACE}       { return WHITE_SPACE; }
    {COMMENT_WITH_CONTINUATION} { yypushState(STATE_COMMENT_CONTINUATION); }
    {COMMENT}           { return COMMENT; }
    {LINE_CONTINUATION} { return LINE_CONTINUATION; }

    // Punctuation
    "("                        { return LPAREN; }
    ")"                        { return RPAREN; }
    "["                        { return LBRACKET; }
    "]"                        { return RBRACKET; }
    "{"                        { return LBRACE; }
    "}"                        { return RBRACE; }
    "->"                         { return ARROW; }
    ">="                         { return OP_GREATER_OR_EQUAL; }
    ">"                          { return OP_GREATER; }
    "<="                         { return OP_LESS_OR_EQUAL; }
    "<"                          { return OP_LESS; }
    "=="                       { return OP_EQUAL; }
    "="                        { return OP_ASSIGN; }
    ","                        { return COMMA; }
    ";"                        { return SEMICOLON; }
    ":"                        { return COLON; }
    "."                        { return DOT; }
    "->"                       { return ARROW; }
    "::"                       { return SCOPE_RESOLUTION; }
    "'"                        { return SINGLE_QUOTE; }
    "..."                       { return OP_ELLIPSIS; }
    "&&"                        { return OP_AND_AND; }
    "||"                        { return OP_OR_OR; }
    "|"                        { return OP_PIPE; }

    // Identifiers (must come after command patterns)
    {IDENTIFIER}               { return IDENTIFIER; }

      // Numbers and registers
    {HEX_NUMBER}               { return HEX_NUMBER; }
    {REGISTER}                 { return REGISTER; }
    {NUMBER}                   { return NUMBER; }
    <<EOF>>             { return finishArgsBlock(); }
}

// Comment continuation state - handles comments that span multiple lines with backslash continuation
<STATE_COMMENT_CONTINUATION> {
    // Continue consuming content until we hit a bare CRLF (not preceded by backslash)
    [^\r\n\\]+         { /* consume comment content */ }
    \\{CRLF}           { /* consume line continuation */ }
    {CRLF}             { yypopState(); return COMMENT; }
    <<EOF>>            { yypopState(); return COMMENT; }
    [^]                { /* consume any other character */ }
}

// Define body state - like YYINITIAL but can be nested and pops on 'end'
<STATE_DEFINE_BODY> {
    // String start transitions
    \"                  { yypushState(STATE_D_STRING); }

    // Language block transitions
    {PYTHON_KW}{CRLF}      { yypushState(STATE_PYTHON_BLOCK); }
    {GUILE_KW}{CRLF}       { yypushState(STATE_GUILE_BLOCK); }
    {DOC_KW}{WHITE_SPACE}{IDENTIFIER}{CRLF}       { yypushState(STATE_DOC_BLOCK); }

    // Whitespace and comments
    {WHITE_SPACE}       { return WHITE_SPACE; }
    {COMMENT_WITH_CONTINUATION} { yypushState(STATE_COMMENT_CONTINUATION); }
    {COMMENT}           { return COMMENT; }
    {CRLF}              { return CRLF; }
    {LINE_CONTINUATION} { return LINE_CONTINUATION; }

    // Special keywords - DEFINE can be nested, END pops back
    {DEFINE}            { yypushState(STATE_DEFINE_BODY); return DEFINE; }
    {END}               { yypopState(); return END; }
    {COMMANDS}          { yypushState(STATE_COMMANDS_LIST); return COMMANDS; }
    {SET}               { return SET_KW; }
    {PRINT}             { return PRINT_KW; }

    // Language keywords (only if not followed by newline - handled above)
    {PYTHON_KW}            { return PYTHON_KW; }
    {GUILE_KW}             { return GUILE_KW; }

    // Commands - only recognize at command boundaries
    {COMMAND_EXECUTION} / [^a-zA-Z0-9_]  { return COMMAND_EXECUTION; }
    {COMMAND_BREAKPOINT_SHORT} / [^a-zA-Z0-9_] { return COMMAND_BREAKPOINT; }
    {COMMAND_BREAKPOINT_LONG} / [^a-zA-Z0-9_]  { return COMMAND_BREAKPOINT; }
    {COMMAND_STACK} / [^a-zA-Z0-9_]     { return COMMAND_STACK; }
    {COMMAND_DATA} / [^a-zA-Z0-9_]      { return COMMAND_DATA; }
    {COMMAND_CONFIG} / [^a-zA-Z0-9_]    { return COMMAND_CONFIG; }

    // Identifiers (must come before WORD)
    {IDENTIFIER}        { return IDENTIFIER; }

    // Everything else as WORD
    {WORD}              { return WORD; }
}

// Commands list state - for breakpoint commands blocks
<STATE_COMMANDS_LIST> {
    // String start transitions
    \"                  { yypushState(STATE_D_STRING); }

    // Whitespace and comments
    {WHITE_SPACE}       { return WHITE_SPACE; }
    {COMMENT_WITH_CONTINUATION} { yypushState(STATE_COMMENT_CONTINUATION); }
    {COMMENT}           { return COMMENT; }
    {CRLF}              { return CRLF; }
    {LINE_CONTINUATION} { return LINE_CONTINUATION; }

    // END pops back to parent state (could be DEFINE_BODY or YYINITIAL)
    {END}               { yypopState(); return END; }

    // All commands are allowed in commands list
    {SET}               { return SET_KW; }
    {PRINT}             { return PRINT_KW; }
    {PYTHON_KW}         { return PYTHON_KW; }
    {GUILE_KW}          { return GUILE_KW; }

    // Commands - only recognize at command boundaries
    {COMMAND_EXECUTION} / [^a-zA-Z0-9_]  { return COMMAND_EXECUTION; }
    {COMMAND_BREAKPOINT_SHORT} / [^a-zA-Z0-9_] { return COMMAND_BREAKPOINT; }
    {COMMAND_BREAKPOINT_LONG} / [^a-zA-Z0-9_]  { return COMMAND_BREAKPOINT; }
    {COMMAND_STACK} / [^a-zA-Z0-9_]     { return COMMAND_STACK; }
    {COMMAND_DATA} / [^a-zA-Z0-9_]      { return COMMAND_DATA; }
    {COMMAND_CONFIG} / [^a-zA-Z0-9_]    { return COMMAND_CONFIG; }

    // Identifiers (must come before WORD)
    {IDENTIFIER}        { return IDENTIFIER; }

    // Everything else as WORD
    {WORD}              { return WORD; }
}

// top level command context
<YYINITIAL> {
    // String start transitions
    \"                  { yypushState(STATE_D_STRING); }

    // Language block transitions
    {PYTHON_KW}{CRLF}      { yypushState(STATE_PYTHON_BLOCK); }
    {GUILE_KW}{CRLF}       { yypushState(STATE_GUILE_BLOCK); }
    {DOC_KW}{WHITE_SPACE}{IDENTIFIER}{CRLF}       { yypushState(STATE_DOC_BLOCK); }

    // Whitespace and comments
    {WHITE_SPACE}       { return WHITE_SPACE; }
    {COMMENT_WITH_CONTINUATION} { yypushState(STATE_COMMENT_CONTINUATION); }
    {COMMENT}           { return COMMENT; }
    {CRLF}              { return CRLF; }
    {LINE_CONTINUATION} { return LINE_CONTINUATION; }

    // Special keywords (these should be recognized anywhere)
    {DEFINE}            { yypushState(STATE_DEFINE_BODY); return DEFINE; }
    {END}               { return END; }
    {COMMANDS}          { yypushState(STATE_COMMANDS_LIST); return COMMANDS; }
    {SET}               { return SET_KW; }
    {PRINT}             { return PRINT_KW; }

    // Language keywords (only if not followed by newline - handled above)
    {PYTHON_KW}            { return PYTHON_KW; }
    {GUILE_KW}             { return GUILE_KW; }

    // Commands - only recognize at command boundaries
    {COMMAND_EXECUTION} / [^a-zA-Z0-9_]  { return COMMAND_EXECUTION; }
    {COMMAND_BREAKPOINT_SHORT} / [^a-zA-Z0-9_] { return COMMAND_BREAKPOINT; }
    {COMMAND_BREAKPOINT_LONG} / [^a-zA-Z0-9_]  { return COMMAND_BREAKPOINT; }
    {COMMAND_STACK} / [^a-zA-Z0-9_]     { return COMMAND_STACK; }
    {COMMAND_DATA} / [^a-zA-Z0-9_]      { return COMMAND_DATA; }
    {COMMAND_CONFIG} / [^a-zA-Z0-9_]    { return COMMAND_CONFIG; }

    // Identifiers (must come before WORD)
    {IDENTIFIER}        { return IDENTIFIER; }

    // Everything else as WORD
    {WORD}              { return WORD; }
}

[^] { return BAD_CHARACTER; }
