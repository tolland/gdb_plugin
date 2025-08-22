# gdb command scripts plugin

This project implements a subset of the available gdb command script

gdb supports providing lists of commands as a file:

```shell
gdb --command=file.gdb --args some-prog --option arg
```

This file can contain arbitrary gdb commands and user defined commands, settings, breakpoints, and extension code.

## gdb syntax

> A GDB command is a single line of input. There is no limit on how long it can be. It starts with a command name, which is followed by arguments whose meaning depends on the command name. For example, the command step accepts an argument which is the number of times to step, as in ‘step 5’. You can also use the step command with no arguments. Some commands do not allow any arguments.
> <https://sourceware.org/gdb/current/onlinedocs/gdb.html/Command-Syntax.html>

gdb supports line continuation and embedded blocks of whatever syntax the extension uses, such as python and guile.

> GDB command names may always be truncated if that abbreviation is unambiguous. Other possible command abbreviations are listed in the documentation for individual commands. In some cases, even ambiguous abbreviations are allowed; for example, s is specially defined as equivalent to step even though there are other commands whose names start with s. You can test abbreviations by using them as arguments to the help command.


```shell
(gdb) help
List of classes of commands:

aliases -- User-defined aliases of other commands.
breakpoints -- Making program stop at certain points.
data -- Examining data.
files -- Specifying and examining files.
internals -- Maintenance commands.
obscure -- Obscure features.
running -- Running the program.
stack -- Examining the stack.
status -- Status inquiries.
support -- Support facilities.
text-user-interface -- TUI is the GDB text based interface.
tracepoints -- Tracing of program execution without stopping the program.
user-defined -- User-defined commands.
```

These files can be simple, but support increasingly complicated nested structures and edge cases which make it hard to model in a syntax highlighter.

<https://sourceware.org/gdb/current/onlinedocs/gdb#Command-Syntax>
A GDB command is a single line of input. There is no limit on how long it can be. It starts with a command name, which is followed by arguments whose meaning depends on the command name. For example, the command step accepts an argument which is the number of times to step, as in ‘step 5’. You can also use the step command with no arguments. Some commands do not allow any arguments.
GDB command names may always be truncated if that abbreviation is unambiguous. Other possible command abbreviations are listed in the documentation for individual commands. In some cases, even ambiguous abbreviations are allowed; for example, s is specially defined as equivalent to step even though there are other commands whose names start with s. You can test abbreviations by using them as arguments to the help command.

Any text from a # to the end of the line is a comment; it does nothing. This is useful mainly in command files (see Command Files).
Note: this isn't true for set command:
(gdb) set $myVar = "this is a test"
(gdb) set $myVar = "this is a test" # this is a comment
Invalid character '#' in expression.

some complex cases:

(gdb) with print array on -- print $myvar
$2 = void

## early initialization

>  Only set or source commands should be placed into an early initialization file, and the only set commands that can be used are those that control how GDB starts up.


## gdb syntax structures


gdb is pretty flexible, that makes it complicated to lex.

### basic structure

> A GDB command is a single line of input. There is no limit on how long it can be. It starts with a command name, which is followed by arguments whose meaning depends on the command name. For example, the command step accepts an argument which is the number of times to step, as in ‘step 5’. You can also use the step command with no arguments. Some commands do not allow any arguments.
<https://sourceware.org/gdb/current/onlinedocs/gdb.html/Command-Syntax.html#Command-Syntax>

```gdb
cont
step
s
```

### command abbreviation

> You can abbreviate a GDB command to the first few letters of the command name, if that abbreviation is unambiguous;
<https://sourceware.org/gdb/current/onlinedocs/gdb.html/Commands.html#Commands>


```gdb
b main
br main
bre main
break main
```

are all valid


### flexible white space


```gdb
(gdb) define myfunc2
Type commands for definition of "myfunc2".
End with a line saying just "end".
>print "myfunc2"
>    end
(gdb) myfunc2
$5 = "myfunc2"
(gdb)
// end has trailing whitespace, is valid. no trailing comments though
```

## lexer used by live preview


- matches regexp on the first hit, so `COMMAND_PRINT='regexp:(print|p)` needs to be in that order to allow print to hit before p


## jflex macros

> The regular expression on the right hand side must be well formed and must not contain the ^, / or $ operators.

## jflex lookaheads

### lookahead

```
<YYINITIAL> {IDENTIFIER} / (".")  {
    if (ArrayUtil.find(CfmlUtil.getVariableScopes(myProject), StringUtil.toLowerCase(yytext().toString())) != -1) {
        return CfscriptTokenTypes.SCOPE_KEYWORD;
    } else {
        return CfscriptTokenTypes.IDENTIFIER;
    }
 }
```


```
  {LineTerminator} / {WhiteSpace}? ":" {Identifier} {
    endBlockOrContinueOnNewline();
    startBlock(JadeTokenTypes.FILTER_CODE, BlockInfo.State.PREDICTED);
    return doRegularEol();
  }
```

### up and including

```
  ~"}}" {
      // backtrack over any extra stache characters at the end of this string
      while (yylength() > 2 && yytext().subSequence(yylength() - 3, yylength()).toString().equals("}}}")) {
        yypushback(1);
      }

      yypushback(2);
      yybegin(comment_end);
      return HbTokenTypes.COMMENT_CONTENT;
  }
```

### attempting lexing multiple line blocks is bad

the negative lookahead is not well-supported. so trying to do multi line except
in cases like line continuation characters is bad.

```
// the [^]+ is required to ensure the match can't be an empty string
[^]+ !([^]* {CRLF}{ENDLINE_BODY}{CRLF} [^]*) {CRLF} / {ENDLINE_BODY}{CRLF}? {
    yybegin(END_TOKEN);
    return DOC_BLOCK;
  }
```

This matches the longest match, not the one we want. so have to fall back to successive line based BLOCKS. not the end of the world

## flip back and forth with end

```
<HERE_DOC_END_MARKER> {
  {WhiteSpace}+                   { if (!heredocWithWhiteSpaceIgnore) yybegin(HERE_DOC_BODY); return HEREDOC_CONTENT; }
  {HeredocMarker}+                { if (yytext().toString().equals(heredocMarker))
                                  { heredocMarker = null; heredocWithWhiteSpaceIgnore = false; popState(); return HEREDOC_MARKER_END; }
                                    else { yypushback(yylength()); yybegin(HERE_DOC_BODY); } }
  [^]                             { yypushback(yylength()); yybegin(HERE_DOC_BODY); }
}

<HERE_DOC_BODY> {
    {InputCharacter}+             { return HEREDOC_CONTENT; }
    {LineTerminator}              { yybegin(HERE_DOC_END_MARKER); return HEREDOC_CONTENT; }
}
```

### python extension

gdb supports running python, it injects a `gdb` module
