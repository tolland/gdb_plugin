


## handle user defined macros

```
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

print "Somerthing here"

document mycommand
Source file and execute command in it
usage:
	check_test command/break/label.gdb
end
```

## handle eval syntax

```
print %s, my_var
```

## pass python blocks to language inject in intellij

```
python
# some python stuff
end
```
