# GDB test script demonstrating syntax highlighting
# This file shows different command categories and syntax elements

# GDB script for debugging Epub3Generator

# set in the form key = value
set pagination off
# set in the form object key = value
set breakpoint pending on
# set object key to value which is a file
set logging file gdb.output
# not valid after assignment it seems
# (gdb) set $var = 0 # yes, you can declare variables
# Invalid character '#' in expression.
set $var = 0


# This is using the non ambiguous substr of command breakpoint
bre function_name
commands
    backtrace
    continue
end

# Configuration commands (should be highlighted differently)
set confirm off
set pagination off
set print pretty on

# Execution control commands (run, continue, step, etc.)
run arg1 "argument with spaces"
start
continue

# Breakpoint commands (break, watch, catch, etc.)
break main
break function_name if argc > 1
break *0x400000
catch syscall

# loading and running lists of commands
source /opt/gkr-debug/watchpoints.gdb

# Stack navigation commands
# backtrace

# Data examination commands
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

define hexdump
    # arg0: the address
    # arg1: the data
    printf "0x%04x: 0x%02x 0x%02x 0x%02x 0x%02x\n", \
        ( $arg0 & 0x0FFF ), \
        ( ( $arg1 >>  0 ) & 0xFF ), \
        ( ( $arg1 >>  8 ) & 0xFF ), \
        ( ( $arg1 >> 16 ) & 0xFF ), \
        ( ( $arg1 >> 24 ) & 0xFF )
end

# python extension examples
python
def switch_inferior_and_continue(x):
    print("switching inferior and continuing")
    gdb.execute("inferior %d" % x)
    gdb.execute("continue")

def exit_handler(event):
    print("in the exit handler")
    has_threads = [ inferior.num for inferior in gdb.inferiors() if inferior.threads() ]
    if has_threads:
        print("have threads")
        has_threads.sort()
        gdb.post_event(lambda: switch_inferior_and_continue(has_threads[0]))

gdb.events.exited.connect(exit_handler)
end
