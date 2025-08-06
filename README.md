# Jetbrains plugin for gdb scripts

gdb supports providing lists of commands as a file as an option:

```shell
gdb --command=file.gdb
```


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
