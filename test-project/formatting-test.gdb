# Test file for GDB code formatting
# This file demonstrates various formatting scenarios

# Poor formatting that should be improved
  set confirm off
print   variable_name

# User-defined command (should indent body)

print "Inside command"
info registers
backtrace
end

# Complex expressions (should format operators)
break function if argc>1&&argv!=NULL
set variable=42+10*3
print *(int*)0x400000

# Various command types
info breakpoints
x/10i $pc
disassemble main

bre

