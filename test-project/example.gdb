# GDB test script demonstrating syntax highlighting
# This file shows different command categories and syntax elements

# Configuration commands (should be highlighted differently)
set confirm off
set pagination off
set print pretty on

# Execution control commands (run, continue, step, etc.)
run arg1 "argument with spaces"
start
continue
step
next
finish

# Breakpoint commands (break, watch, catch, etc.)
break main
break function_name if argc > 1
break *0x400000
watch variable_name
catch syscall

# Stack navigation commands
backtrace
frame 0
up
down

# Data examination commands
print variable_name
print $rax
x/10i $pc
x/10x $sp
info registers
info breakpoints
disassemble main

# User-defined commands
define mycommand
  print "Custom command executed"
  info stack
end

# Complex expressions with operators
if argc == 2
  set var counter = 42
  print *pointer->member
end

# Memory operations with hex addresses
x/10x 0x401000
set *(int*)0x400000 = 0xdeadbeef

# Registers and special syntax
print $rsp
print $rbp
print *(char**)($rsp + 8)

quit