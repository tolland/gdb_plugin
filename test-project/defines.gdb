
define mycommand
  print "Custom command"
  info warranty

  break foo2
  commands
      silent
      printf "x is %d\n",x
      cont
  end

  define mycommand2
    print "Custom command"
    info warranty
  end
end

print "Something here"

document mycommand
Source file and execute command in it
usage:
	check_test command/break/label.gdb
end

print "this isa test"

define adder
  if $argc == 2
    print $arg0 + $arg1
  end
  if $argc == 3
    print $arg0 + $arg1 + $arg2
  end
end
