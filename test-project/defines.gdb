
define mycommand
  print "Custom command"
  info warranty

  define mycommand2
    print "Custom command"
    info warranty
  end
end

print "Somerthing here"

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
