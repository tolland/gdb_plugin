
> GDB provides convenience variables that you can use within GDB to hold on to a value and refer to it later. These variables exist entirely within GDB; they are not part of your program, and setting a convenience variable has no direct effect on further execution of your program. That is why you can use them freely.
>
> Convenience variables are prefixed with ‘$’. Any name preceded by ‘$’ can be used for a convenience variable, unless it is one of the predefined machine-specific register names (see Registers). (Value history references, in contrast, are numbers preceded by ‘$’. See Value History.)
>
> You can save a value in a convenience variable with an assignment expression, just as you would set a variable in your program. For example:
>
> set $foo = *object_ptr
>
> would save in $foo the value contained in the object pointed to by object_ptr.
