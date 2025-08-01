# GDB Script Syntax Analysis

This document describes the pseudo-formal syntax of GDB debugger scripts for lexical analysis and syntax highlighting.

## Overview

GDB scripts consist of commands, arguments, comments, and various literal values. The syntax is command-oriented with flexible argument parsing and extensive use of symbols and expressions.

## High-Level Structure

```
script ::= (line | empty_line)*
line ::= comment | command_line
command_line ::= command arguments? comment?
```

## Lexical Elements

### Comments
```
comment ::= '#' rest_of_line
```
- Start with `#` character
- Continue to end of line
- Can appear standalone or after commands

### Commands
Commands are the primary structural element and fall into several categories:

#### Control Commands
```
break | b | tbreak | hbreak | thbreak | rbreak | watch | rwatch | awatch | catch
run | r | start | continue | c | step | s | next | n | finish | until | u
kill | quit | q | detach
```

#### Information Commands
```
info | i | show | list | l | disassemble | disas | x | examine
print | p | display | output | printf | call | return
backtrace | bt | frame | f | up | down | select-frame
```

#### Configuration Commands
```
set | unset | source | define | document | end | if | else | while | loop_break | loop_continue
file | exec-file | symbol-file | core-file | target | attach | load
```

### Arguments

#### Identifiers and Symbols
```
identifier ::= [a-zA-Z_][a-zA-Z0-9_]*
symbol ::= identifier ('::' identifier)*
qualified_name ::= symbol | '*' symbol | symbol '*'
```

#### Addresses and Numbers
```
address ::= '0x' [0-9a-fA-F]+
decimal ::= [0-9]+
number ::= decimal | address | '$[0-9]+' (register)
```

#### Strings and Expressions
```
string ::= '"' (char | escape_sequence)* '"'
expression ::= number | identifier | string | '(' expression ')' | expression operator expression
```

#### Special Syntax
```
location ::= filename ':' line_number | '*' address | function_name
range ::= location '-' location | location '+' number | location ',' number
condition ::= 'if' expression
```

### Operators and Punctuation
```
operators ::= '+' | '-' | '*' | '/' | '%' | '&' | '|' | '^' | '~' | '<<' | '>>'
            | '==' | '!=' | '<' | '>' | '<=' | '>=' | '&&' | '||' | '!'
punctuation ::= '(' | ')' | '[' | ']' | '{' | '}' | ',' | ';' | ':' | '.'
assignment ::= '=' | '+=' | '-=' | '*=' | '/='
```

### Memory and Register Syntax
```
register ::= '$' identifier | '$' number
memory_ref ::= '*' '(' expression ')'
array_access ::= expression '[' expression ']'
member_access ::= expression '.' identifier | expression '->' identifier
```

## Command Categories for Syntax Highlighting

### 1. Execution Control
- `run`, `start`, `continue`, `step`, `next`, `finish`, `until`, `kill`, `quit`

### 2. Breakpoints and Watchpoints
- `break`, `tbreak`, `watch`, `catch`, `delete`, `enable`, `disable`, `condition`

### 3. Stack and Frame Navigation
- `backtrace`, `frame`, `up`, `down`, `info stack`, `info frame`

### 4. Data Examination
- `print`, `x`, `display`, `info`, `show`, `list`, `disassemble`

### 5. Program State
- `info registers`, `info locals`, `info args`, `info breakpoints`

### 6. Configuration and Setup
- `set`, `show`, `source`, `file`, `target`, `attach`, `load`

### 7. User-Defined Commands
- `define`, `document`, `end`, `if`, `else`, `while`

## Lexical Patterns

### Token Types for Lexer

1. **COMMENT** - `#.*$`
2. **COMMAND** - Known command names (keyword matching)
3. **IDENTIFIER** - `[a-zA-Z_][a-zA-Z0-9_]*`
4. **NUMBER** - `[0-9]+`
5. **HEX_NUMBER** - `0x[0-9a-fA-F]+`
6. **REGISTER** - `\$[a-zA-Z_][a-zA-Z0-9_]*` or `\$[0-9]+`
7. **STRING** - `"([^"\\]|\\.)*"`
8. **OPERATOR** - `+`, `-`, `*`, `/`, `==`, `!=`, etc.
9. **PUNCTUATION** - `(`, `)`, `[`, `]`, `{`, `}`, `,`, `;`, `:`, `.`
10. **WHITESPACE** - ` `, `\t`
11. **NEWLINE** - `\n`, `\r\n`
12. **ADDRESS_MARKER** - `*` (when used for memory dereferencing)
13. **SCOPE_RESOLUTION** - `::`
14. **MEMBER_ACCESS** - `->`, `.`

## Example Syntax Patterns

```gdb
# This is a comment
set confirm off                    # Configuration with comment
break main                         # Simple command with argument
break *0x400000                    # Breakpoint at address
break function_name if argc > 1    # Conditional breakpoint
run arg1 "arg with spaces"         # Command with mixed arguments
print $rax                         # Register reference
x/10i $pc                         # Memory examination with format
info registers                     # Multi-word command
set var = 42                      # Variable assignment
define mycommand                   # User-defined command start
  print "Hello"
  info stack
end                               # User-defined command end
```

## Notes for Implementation

- Commands are case-sensitive but many have short aliases
- Whitespace is generally not significant except as token separator
- String quoting allows spaces in arguments
- Expression parsing can be complex - start with basic patterns
- Many commands have sub-commands (e.g., `info registers`, `set print pretty`)
- Consider context-sensitive highlighting for different command types