
```gdb
(gdb) b main
Note: breakpoint 5 also set at pc 0x555555555550.
Breakpoint 9 at 0x555555555550: file ../src/sleep.c, line 96.
(gdb) br main
Note: breakpoints 5 and 9 also set at pc 0x555555555550.
Breakpoint 10 at 0x555555555550: file ../src/sleep.c, line 96.
(gdb) bre main
Note: breakpoints 5, 9 and 10 also set at pc 0x555555555550.
Breakpoint 11 at 0x555555555550: file ../src/sleep.c, line 96.
(gdb) brea main
Note: breakpoints 5, 9, 10 and 11 also set at pc 0x555555555550.
Breakpoint 12 at 0x555555555550: file ../src/sleep.c, line 96.
(gdb) 

```
