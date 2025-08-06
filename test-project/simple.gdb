# Simple comment
set pagination off
# split over lines
set var \
$myvar2 \
= \
7

define adder
  print $arg0 + $arg1 + $arg2
end

define adder
    print $arg0 + $arg1 + $arg2
    printf "Print something if breakpoint hit"
end

# conditional breakpoints
break foo if x>0
commands
    silent
    printf "Print something if breakpoint hit"
    cont
end

break # simple comment with trailing comment

# set some variable in a program
set variable width = 50
