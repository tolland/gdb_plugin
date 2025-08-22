// src/main/kotlin/org/limepepper/gdb/lexer/GdbLexer.flex
package org.limepepper.lang.gdb.lexer;

import com.intellij.lexer.FlexLexer;

import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Stack;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.limepepper.lang.gdb.psi.GdbTypes.*;

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
COMMENT_WITH_CONTINUATION=#[^\r\n]*\\{CRLF}
COMMENT=#[^\r\n]*
LINE_CONTINUATION=\\{CRLF}
InputCharacter           = [^\r\n]

// Numbers and identifiers
HEX_NUMBER=0x[0-9a-fA-F]+
NUMBER=[0-9]+
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
REGISTER=\$([a-zA-Z_][a-zA-Z0-9_]*|[0-9]+)

// Commands - these need to be whole words to avoid conflicts
END=end
PYTHON_KW=python

// Commands and their abbreviations
COMMAND_SET=set
/**
(gdb) help commands
Set commands to be executed when the given breakpoints are hit.
Give a space-separated breakpoint list as argument after "commands".
A list element can be a breakpoint number (e.g. `5') or a range of numbers
(e.g. `5-7').
With no argument, the targeted breakpoint is the last one set.
The commands themselves follow starting on the next line.
Type a line containing "end" to indicate the end of them.
Give "silent" as the first line to make the breakpoint silent;
then no output is printed when it is hit, except what the commands print
 */
COMMAND_COMMANDS=(commands|command|comman|comma|comm)

COMMAND_CONTROL=(if|while)
/**
(gdb) help document
Document a user-defined command or user-defined alias.
Give command or alias name as argument.  Give documentation on following lines. End with a line of just "end".
 */
COMMAND_DOCUMENT=document
/**
(gdb) help run
run, r
Start debugged program.
You may specify arguments to give it.
Args may include "*", or "[...]"; they are expanded using the
shell that will start the program (specified by the "$SHELL" environment
variable).  Input and output redirection with ">", "<", or ">>"
are also allowed.

With no arguments, uses arguments last specified (with "run" or
"set args").  To cancel previous arguments and run with no arguments,
use "set args" without arguments.

To start the inferior without using a shell, use "set startup-with-shell off".
 */
COMMAND_EXECUTION=(r|run|start|c|continue|s|step|n|next|finish|until|kill|q|quit|detach)
/**
(gdb) help br
break, brea, bre, br, b
Set breakpoint at specified location.
break [PROBE_MODIFIER] [LOCATION] [thread THREADNUM]
        [-force-condition] [if CONDITION]
PROBE_MODIFIER shall be present if the command is to be placed in a
probe point.  Accepted values are `-probe' (for a generic, automatically
guessed probe type), `-probe-stap' (for a SystemTap probe) or
`-probe-dtrace' (for a DTrace probe).
LOCATION may be a linespec, address, or explicit location as described
below.

With no LOCATION, uses current execution address of the selected
stack frame.  This is useful for breaking on return to a stack frame.
 */
COMMAND_BREAKPOINT=(b|br|bre|brea|break|until|unti|unt)
COMMAND_STACK=(backtrace|bt|f|frame|up|down|select-frame)
COMMAND_DATA=(display|output|printf|call|return|examine|i|info|show|l|list|disassemble|disas)
/**
(gdb) help source
Read commands from a file named FILE.

Usage: source [-s] [-v] FILE
-s: search for the script in the source search path,
    even if FILE contains directories.
-v: each command in FILE is echoed as it is executed.

Note that the file ".gdbinit" is read automatically in this way
when GDB is started.
 */
COMMAND_CONFIG=(unset|source|exec-file|symbol-file|core-file|target|attach|load)
COMMAND_PRINT=(print|p)
// user defined commands
/*
A user-defined command is a sequence of GDB commands to which you assign a new
 name as a command. This is done with the define command. User commands may
 accept an unlimited number of arguments separated by whitespace. Arguments
 are accessed within the user command via $arg0…$argN. The arguments are text
 substitutions, so they may reference variables, use complex expressions, or
 even perform inferior functions calls.
 */
COMMAND_USER=define
// something that looks like a command, in a command location. probably
// user defined. same as IDENTIFIER.
// <https://sourceware.org/gdb/current/onlinedocs/gdb.html/Define.html#index-user_002ddefined-command>
// The argument commandname may be a bare command name consisting of letters, numbers, dashes, dots, and underscores. It may also start with any predefined or user-defined prefix command. For example, ‘define target my-target’ creates a user-defined ‘target my-target’ command.
COMMAND_GENERIC=[a-zA-Z_][a-zA-Z0-9_]*

// String content - everything except quotes, backslashes, and line endings
STRING_CONTENT=[^\"\\\r\n]+
ESCAPE_SEQUENCE=\\[^\r\n]

// General word - anything that's not whitespace, comment, or special chars
WORD=[^#\s\"\\(){}\[\]=,;:.>-]+

ENDLINE_BODY=\s*end\s*

%state STATE_D_STRING
%state STATE_PYTHON_BLOCK
%state PYTHON_BLOCK_BODY
%state STATE_PYTHON_INLINE
%state IN_TEXT_BLOCK
%state IN_ARGS
%state STATE_DEFINE_BODY
%state STATE_COMMANDS_LIST
%state STATE_COMMENT_CONTINUATION
%state ARGS_AND_BLOCK
%state END_TOKEN

%{
    public GdbLexer() {
      this((java.io.Reader)null);
    }

    public record StackState (int stack, int start, int next) {}

    // track state for popping back (from handlebars.flex)
    private Stack<Integer> stack = new Stack<>();
    private Stack<StackState> stackState = new Stack<>();

    // Track start of a multi-line (continued) comment so we can return a single COMMENT token
    private int commentStart = -1;

    public void yypushState(int newState) {
      yypushState(newState, -1, -1);
    }

    public void yypushState(int newState, int start, int next) {
      int currentState = yystate();
      assert currentState != YYINITIAL || stack.empty() : "Can't push initial state into the not empty stack";
      stack.push(currentState);
      stackState.push(new StackState(currentState,start,next));
      yybegin(newState);
    }

    public void yypopState() {
      yybegin(stack.pop());
    }

    private int stringStart = -1;
    private int blockStart = -1;
    private int contentStart = -1;

    private IElementType finishDoubleString() {
        yypopState();
        zzStartRead = stringStart;
        return DOUBLE_QUOTED_STRING;
    }

    private IElementType finishPythonBlockPushbackEnd() {
        // push back matched 'end' so it will be lexed by parent state
        yypopState();
        yypushback(yylength());
        return PYTHON_BLOCK;
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

//    @Override
//    public String toString() {
//      return "yystate = " + state + (lBraceCount == 0 ? "" : "lBraceCount = " + lBraceCount);
//    }

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
  ^end / [^a-zA-Z0-9_] {
    yypopState();
    return PYTHON_BLOCK;
  }
}

<PYTHON_BLOCK_BODY> {
    [^]+ !([^]* {CRLF}{ENDLINE_BODY}{CRLF} [^]*) {CRLF} / {ENDLINE_BODY}{CRLF}? {
        yybegin(END_TOKEN);
        return PYTHON_BLOCK;
      }
}

<STATE_PYTHON_INLINE> {
//    [^]                 { /* consume any character */ }
      [^\r\n\\]+           { return PYTHON_INLINE; }
    {CRLF}                 { yypopState(); return CRLF; }
      {WHITE_SPACE}        { return WHITE_SPACE; }
       <<EOF>>             {  yypopState();
                           return PYTHON_INLINE;
      }
}

<IN_TEXT_BLOCK> {
  /* Standalone 'end' line: optional indent + 'end' + optional trailing spaces + optional newline */
  ^{WHITE_SPACE}*end{WHITE_SPACE}*{CRLF}?   { yypopState(); return END; }

  /* Any other full line */
  [^\r\n]*{CRLF}          { return DOC_BLOCK_LINE; }

  /* Last partial line before EOF */
  [^\r\n]+                { return DOC_BLOCK_LINE; }
}

<END_TOKEN> {
    {CRLF}                    { return CRLF; }
    {WHITE_SPACE}             { return WHITE_SPACE; }
    end                       { yypopState(); return END; }
}

// processing the args, subcommands and options of a command
// This is generic args processing, when no more specific one is available
<IN_ARGS> {

    // String start transitions
    \"                        { stringStart = zzStartRead; yypushState(STATE_D_STRING, zzStartRead, -1); }

    // Check for end keyword to pop back
//    {END}                     { yypopState(); return END; }
    {CRLF}                    { yypopState(); return CRLF;  }

    // Whitespace and comments
    {WHITE_SPACE}               { return WHITE_SPACE; }
    {COMMENT_WITH_CONTINUATION} { commentStart = zzStartRead; yypushState(STATE_COMMENT_CONTINUATION); }
    {COMMENT}                   { return COMMENT; }
    {LINE_CONTINUATION}         { return LINE_CONTINUATION; }

    // Consume everything else as ARG until we hit CRLF or END
    [^\"\r\n\s#\\]+            { return ARG; }

}

// Comment continuation state - handles comments that span multiple lines with backslash continuation
<STATE_COMMENT_CONTINUATION> {
    // Continue consuming content until we hit a bare CRLF (not preceded by backslash)
    [^\r\n\\]+         { /* consume comment content */ }
    \\{CRLF}           { /* consume line continuation */ }
    {CRLF}             {
                            // finalize a single COMMENT token spanning from commentStart
                            if (commentStart >= 0) {
                              pushbackEOL();
                              zzStartRead = commentStart;
                              commentStart = -1;
                            }
                            yypopState();
                            return COMMENT;
                        }
    <<EOF>>            {
                            if (commentStart >= 0) {
                              zzStartRead = commentStart;
                              commentStart = -1;
                            }
                            yypopState();
                            return COMMENT;
                        }
    [^]                { /* consume any other character */ }
}

<STATE_COMMANDS_LIST, STATE_DEFINE_BODY> {
  {END}               { return END; }
}

<ARGS_AND_BLOCK> {
{END}               { return END; }
}


// top level command context
<YYINITIAL, STATE_COMMANDS_LIST, STATE_DEFINE_BODY> {

    {END}               { return END; }

    // Language block transitions
    {PYTHON_KW} / {WHITE_SPACE}*{CRLF} {
      yypushState(PYTHON_BLOCK_BODY);
      return PYTHON_KW;
     }
    // Single-line python (followed by non-newline content)
    {PYTHON_KW}{WHITE_SPACE}+ / [^\r\n] {
        yypushState(STATE_PYTHON_INLINE);
        return PYTHON_KW;
    }

    // Whitespace and comments
    {WHITE_SPACE}       { return WHITE_SPACE; }
    {COMMENT_WITH_CONTINUATION} { commentStart = zzStartRead; yypushState(STATE_COMMENT_CONTINUATION); }
    {COMMENT}           { return COMMENT; }
    {CRLF}              { return CRLF; }
    {LINE_CONTINUATION} { return LINE_CONTINUATION; }

    // Special keywords (these should be recognized anywhere)
//    {PRINT}             { return PRINT_KW; }

    // Commands - only recognize at command boundaries
    {COMMAND_EXECUTION} / [^a-zA-Z0-9_]  { yypushState(IN_ARGS); return COMMAND_EXECUTION; }
    {COMMAND_STACK} / [^a-zA-Z0-9_]     { return COMMAND_STACK; }
    {COMMAND_DATA} / [^a-zA-Z0-9_]      {  yypushState(IN_ARGS); return COMMAND_DATA; }
    {COMMAND_CONFIG} / [^a-zA-Z0-9_]    { yypushState(IN_ARGS); return COMMAND_CONFIG; }


    // print, set and with have complicated lexing due various
    // argument configurations available.
    {COMMAND_PRINT}            { yypushState(IN_ARGS); return COMMAND_PRINT; }
    {COMMAND_SET}               { yypushState(IN_ARGS); return COMMAND_SET; }

    // command has an optional argument, and no block
    {COMMAND_BREAKPOINT} / [^a-zA-Z0-9_] { yypushState(IN_ARGS); return COMMAND_BREAKPOINT; }

      // command optionally takes and argument has a required block
    {COMMAND_COMMANDS}          {
        yypushState(STATE_COMMANDS_LIST);
        yypushState(IN_ARGS);
        return COMMAND_COMMANDS;
      }

     // commands require an argument and have a required block
    {COMMAND_USER}            { yypushState(IN_ARGS); return COMMAND_USER; }
    {COMMAND_CONTROL}            { yypushState(IN_ARGS); return COMMAND_CONTROL; }
      //     {DOC_KW}{WHITE_SPACE}{IDENTIFIER}{CRLF}       { blockStart = zzStartRead; yypushState(STATE_DOC_BLOCK); return DOC_BLOCK; }
    {COMMAND_DOCUMENT}            {
            yypushState(IN_TEXT_BLOCK);
            yypushState(IN_ARGS);
            return COMMAND_DOCUMENT;
          }

    // catch all for unknown commands. If a block command matches this it
    // will run to end before failing
    {COMMAND_GENERIC}        { yypushState(IN_ARGS); return COMMAND_GENERIC; }
}

[^] { return BAD_CHARACTER; }
