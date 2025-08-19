# This is at the start of the file

set var $x1 = 1
set var $y1 = 12

if $x1 == 5 && $y1 >= 10
  print "true"
end

print $a = "using the python extension syntax"

define user_defined_command
	dont-repeat
	if $argc > 1
		help user_defined_command
	else
		if $argc == 1
			set $i = $arg0
		else
			set $i = $0
		end
		while $i <= 1
			echo $i
			$i = $i + 1
		end
		break function exit
	end
end

document user_defined_command
This is some documentation for the user defined command. It shows up in
`help user_defined_command`
end

python
def do_something_on_exit(x):
    pass

gdb.events.exited.connect(do_something_on_exit)
end

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

print "this is some other command"



print "Strings can have line \
continuations in them"

# This is a user defined command
define mycommand
  print "Custom command"
  info warranty
end

print "A {string} ${with} %special characters! (*&*%&%&T ~@~?>?>¬`"

# This is a user defined command
document mycommand
Source file and execute command in it
usage:
	check_test command/break/label.gdb
	check that it ignore continuation\
	character \
end


# The set command is split over lines using line continuation character
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



# assignment to simple value
set pagination off

set var $myVar = "some string value"
set variable $myVar = "some string value"

# this also works, if it doesn't conflict with gdb set subcommand
set $someVar = 50

# conditional breakpoints
break foo if x > 0
commands
    silent
    printf "x is %d\n",x
    cont
end

# This is at the end of the file
