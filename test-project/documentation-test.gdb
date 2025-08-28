# GDB Documentation Test File
# Hover over commands, registers, and hex values to see documentation tooltips



# Stack navigation commands
backtrace "123"

# Execution control commands - hover to see documentation
run arg1 arg2
start
continue
step
next
finish
until

# Breakpoint commands - comprehensive documentation available
break main
break *0x400000
watch variable_name
catch throw
delete 1
enable
disable

# Data examination commands - detailed help available
print variable_name
print $rax
print/x 0x12345678
x/10i $pc
x/8x $sp
info registers
info breakpoints
disassemble main


# Configuration commands
set confirm off
set print pretty on
show pagination

# Register references - hover for register documentation
print $rax    # 64-bit accumulator
print $rbx    # Base register
print $rcx    # Counter register
print $rdx    # Data register
print $rsp    # Stack pointer
print $rbp    # Base pointer
print $rsi    # Source index
print $rdi    # Destination index
print $rip    # Instruction pointer
print $pc     # Program counter (alias for RIP)

# 32-bit register portions
print $eax    # 32-bit accumulator
print $esp    # 32-bit stack pointer
print $ebp    # 32-bit base pointer

# 16-bit register portions
print $ax     # 16-bit accumulator
print $sp     # 16-bit stack pointer
print $bp     # 16-bit base pointer

# 8-bit register portions
print $al     # Low 8 bits of AX
print $ah     # High 8 bits of AX

# Hexadecimal values - hover for decimal/binary conversion
break *0x400000
x/10x 0x7fff12345678
set *(int*)0xdeadbeef = 0xcafebabe
print 0x12345
print 0xff
print 0x1000

# User-defined commands
define mycommand
  print "Custom command"
  info registers
  backtrace
end
