
# Set up breakpoints for key Epub3Generator methods
break Epub3Generator::Epub3Generator
commands $bpnum
    echo \n=== Epub3Generator Constructor ===\n
    bt
    continue
end

# conditional breakpoints
break foo1
commands
    silent
    printf "x is %d\n",x
    cont
end

break foo2
commands
    silent
    printf "x is %d\n",x
    cont
end

# break on line number
break 403
commands
silent
set x = y + 4
cont
end
