# Test GDB script file
set confirm off
break main
run
info registers
backtrace
continue
quit