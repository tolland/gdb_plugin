# This is at the start of the file

# split over lines
set var \
$myvar2 \
= \
7

# assignment to property of sub command
set logging file gdb.output

# This is floating

# This is associated with a command
break main

# This is multiple lines of comments
# that are associated with a command
print "This is some text"

# This is at the end of the file


# assignment to simple value
set pagination off

# this also works, if it doesn't conflict with gdb set subcommand
set someVar = 50

# set some variable in a program
set variable width = 50

set var $myVar = "some string value"
set variable $myVar = "some string value"

# set some variable in a program
set variable width = 50
# this also works, if it doesn't conflict with gdb set subcommand
set someVar = 50

# assignment to simple value
set pagination off
# assignment to property of sub command
set logging file gdb.output
# no trailing comments on assignments
# set $var = 0 # this throws error
# assignment of other type, e.g. integer
set var counter = 42
# set memory location to value
set *(int*)0x400000 = 0xdeadbeef
set {int}0x83040 = 4

# conditional breakpoints
break foo if x>0
commands
    silent
    printf "x is %d\n",x
    cont
end
