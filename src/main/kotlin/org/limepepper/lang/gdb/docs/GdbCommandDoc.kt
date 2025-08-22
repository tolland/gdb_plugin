package org.limepepper.lang.gdb.docs

/**
 * Documentation data for GDB commands
 */
object GdbCommandDoc {

    /**
     * Documentation for execution control commands
     */
    val executionCommands = mapOf(
        "run" to CommandDoc(
            "run [args...]",
            "Start the debugged program with optional command line arguments.",
            """
            Starts execution of the program being debugged. The program will run until it hits a breakpoint,
            encounters an error, or completes execution.

            Examples:
            - run
            - run arg1 arg2
            - r "argument with spaces"
            """.trimIndent()
        ),

        "r" to CommandDoc(
            "r [args...]",
            "Alias for 'run' command.",
            "Short form of the run command. Starts program execution with optional arguments."
        ),

        "start" to CommandDoc(
            "start [args...]",
            "Start the program and stop at the beginning of main function.",
            """
            Starts the program and automatically sets a temporary breakpoint at the main function.
            This is equivalent to setting a breakpoint at main and then running.
            """.trimIndent()
        ),

        "continue" to CommandDoc(
            "continue [count]",
            "Continue program execution after stopping at a breakpoint.",
            """
            Resumes execution of the program after it has been stopped. Optional count parameter
            specifies how many breakpoints to ignore before stopping again.

            Examples:
            - continue
            - c
            - continue 5
            """.trimIndent()
        ),

        "c" to CommandDoc(
            "c [count]",
            "Alias for 'continue' command.",
            "Short form of continue. Resumes program execution."
        ),

        "step" to CommandDoc(
            "step [count]",
            "Execute one line of source code, stepping into function calls.",
            """
            Executes the next line of source code. If the line contains a function call,
            step will enter the function and stop at its first line.

            Examples:
            - step
            - s
            - step 3
            """.trimIndent()
        ),

        "s" to CommandDoc(
            "s [count]",
            "Alias for 'step' command.",
            "Short form of step. Execute one line, entering function calls."
        ),

        "next" to CommandDoc(
            "next [count]",
            "Execute one line of source code, stepping over function calls.",
            """
            Executes the next line of source code. If the line contains a function call,
            next will execute the entire function and stop after the call returns.

            Examples:
            - next
            - n
            - next 5
            """.trimIndent()
        ),

        "n" to CommandDoc(
            "n [count]",
            "Alias for 'next' command.",
            "Short form of next. Execute one line, stepping over function calls."
        ),

        "finish" to CommandDoc(
            "finish",
            "Continue execution until the current function returns.",
            """
            Executes until the current function returns, then stops and prints the return value.
            Useful for stepping out of a function you've stepped into.
            """.trimIndent()
        ),

        "until" to CommandDoc(
            "until [location]",
            "Continue execution until reaching a specified location or line.",
            """
            Continues execution until the program reaches the specified location or the next line
            (if no location is given). Useful for getting past loops.

            Examples:
            - until
            - u
            - until 100
            - until main.c:50
            """.trimIndent()
        ),

        "u" to CommandDoc(
            "u [location]",
            "Alias for 'until' command.",
            "Short form of until. Continue to specified location."
        ),

        "kill" to CommandDoc(
            "kill",
            "Terminate the program being debugged.",
            "Kills the currently running program. The program can be restarted with 'run'."
        ),

        "quit" to CommandDoc(
            "quit",
            "Exit GDB.",
            "Terminates the GDB session. Any running program will be killed."
        ),

        "q" to CommandDoc(
            "q",
            "Alias for 'quit' command.",
            "Short form of quit. Exit GDB."
        )
    )

    /**
     * Documentation for breakpoint commands
     */
    val breakpointCommands = mapOf(
        "break" to CommandDoc(
            "break [location] [if condition]",
            "Set a breakpoint at specified location.",
            """
            Sets a breakpoint at the specified location. The program will stop when it reaches this point.

            Location formats:
            - function_name
            - filename:line_number
            - *address

            Examples:
            - break main
            - break myfile.c:25
            - break *0x400000
            - break main if argc > 1
            """.trimIndent()
        ),

        "b" to CommandDoc(
            "b [location] [if condition]",
            "Alias for 'break' command.",
            "Short form of break. Set a breakpoint at specified location."
        ),

        "watch" to CommandDoc(
            "watch expression",
            "Set a watchpoint on a variable or expression.",
            """
            Sets a watchpoint that triggers when the value of the expression changes.
            The program will stop whenever the watched memory is modified.

            Examples:
            - watch variable_name
            - watch *0x12345678
            - watch array[index]
            """.trimIndent()
        ),

        "catch" to CommandDoc(
            "catch event",
            "Set a catchpoint for specified events.",
            """
            Sets a catchpoint that triggers on specific events like exceptions or system calls.

            Common events:
            - throw (C++ exceptions)
            - catch (C++ exception handling)
            - syscall [name]

            Examples:
            - catch throw
            - catch syscall
            - catch syscall write
            """.trimIndent()
        ),

        "delete" to CommandDoc(
            "delete [breakpoint_numbers...]",
            "Delete breakpoints by number.",
            """
            Deletes specified breakpoints. If no numbers are given, deletes all breakpoints.
            Use 'info breakpoints' to see breakpoint numbers.

            Examples:
            - delete
            - delete 1
            - delete 1 3 5
            """.trimIndent()
        ),

        "enable" to CommandDoc(
            "enable [breakpoint_numbers...]",
            "Enable specified breakpoints.",
            """
            Enables previously disabled breakpoints. If no numbers are given, enables all breakpoints.

            Examples:
            - enable
            - enable 1
            - enable 1 3 5
            """.trimIndent()
        ),

        "disable" to CommandDoc(
            "disable [breakpoint_numbers...]",
            "Disable specified breakpoints.",
            """
            Disables breakpoints without deleting them. Disabled breakpoints can be re-enabled later.

            Examples:
            - disable
            - disable 1
            - disable 1 3 5
            """.trimIndent()
        )
    )

    /**
     * Documentation for data examination commands
     */
    val dataCommands = mapOf(
        "print" to CommandDoc(
            "print [/format] expression",
            "Print value of expression.",
            """
            Evaluates and prints the value of an expression. Supports various output formats.

            Formats:
            - /x (hexadecimal)
            - /d (decimal)
            - /o (octal)
            - /t (binary)
            - /c (character)
            - /s (string)

            Examples:
            - print variable_name
            - print /x 0x12345
            - print *pointer
            - print array[5]
            """.trimIndent()
        ),

        "p" to CommandDoc(
            "p [/format] expression",
            "Alias for 'print' command.",
            "Short form of print. Display value of expression."
        ),

        "x" to CommandDoc(
            "x/[count][format][size] address",
            "Examine memory at specified address.",
            """
            Examines memory contents at the given address with specified format and size.

            Count: number of units to display
            Format: o(octal), x(hex), d(decimal), u(unsigned), t(binary), f(float), a(address), i(instruction), c(char), s(string)
            Size: b(byte), h(halfword), w(word), g(giant, 8 bytes)

            Examples:
            - x/10i ${'$'}pc (10 instructions at PC)
            - x/8x ${'$'}sp (8 hex words at stack pointer)
            - x/s 0x400000 (string at address)
            """.trimIndent()
        ),

        "info" to CommandDoc(
            "info topic",
            "Display information about various topics.",
            """
            Shows information about program state, breakpoints, registers, etc.

            Common topics:
            - registers: Show register values
            - breakpoints: List all breakpoints
            - locals: Show local variables
            - args: Show function arguments
            - stack: Show call stack
            - threads: Show thread information

            Examples:
            - info registers
            - info breakpoints
            - info locals
            """.trimIndent()
        ),

        "i" to CommandDoc(
            "i topic",
            "Alias for 'info' command.",
            "Short form of info. Display information about topics."
        ),

        "disassemble" to CommandDoc(
            "disassemble [function_or_address]",
            "Display assembly code for specified function or address range.",
            """
            Shows the assembly language instructions for a function or memory range.

            Examples:
            - disassemble
            - disassemble main
            - disassemble 0x400000,0x400100
            - disas main
            """.trimIndent()
        ),

        "disas" to CommandDoc(
            "disas [function_or_address]",
            "Alias for 'disassemble' command.",
            "Short form of disassemble. Show assembly code."
        )
    )

    /**
     * Documentation for configuration commands
     */
    val configCommands = mapOf(
        "set" to CommandDoc(
            "set variable value",
            "Set value of a variable or GDB setting.",
            """
            Sets the value of a program variable or GDB configuration setting.

            Common settings:
            - set confirm off/on
            - set print pretty off/on
            - set pagination off/on

            Examples:
            - set confirm off
            - set variable = 42
            - set print pretty on
            """.trimIndent()
        ),

        "show" to CommandDoc(
            "show [setting]",
            "Display current value of GDB settings.",
            """
            Shows the current value of GDB settings or variables.

            Examples:
            - show
            - show confirm
            - show print pretty
            """.trimIndent()
        )
    )

    /**
     * Documentation for stack navigation commands
     */
    val stackCommands = mapOf(
        "backtrace" to CommandDoc(
            "backtrace [count]",
            "Display the call stack (stack trace).",
            """
            Shows the sequence of function calls that led to the current location.
            Optional count limits the number of frames shown.

            Examples:
            - backtrace
            - bt
            - bt 10
            """.trimIndent()
        ),

        "bt" to CommandDoc(
            "bt [count]",
            "Alias for 'backtrace' command.",
            "Short form of backtrace. Show call stack."
        ),

        "frame" to CommandDoc(
            "frame [number]",
            "Select or display information about a stack frame.",
            """
            Selects a specific stack frame or shows information about the current frame.
            Frame 0 is the innermost (current) frame.

            Examples:
            - frame
            - frame 2
            - f 0
            """.trimIndent()
        ),

        "f" to CommandDoc(
            "f [number]",
            "Alias for 'frame' command.",
            "Short form of frame. Select stack frame."
        ),

        "up" to CommandDoc(
            "up [count]",
            "Move up the call stack (to caller frames).",
            """
            Moves up the call stack toward the caller functions.
            Optional count specifies how many frames to move up.

            Examples:
            - up
            - up 2
            """.trimIndent()
        ),

        "down" to CommandDoc(
            "down [count]",
            "Move down the call stack (to callee frames).",
            """
            Moves down the call stack toward the called functions.
            Optional count specifies how many frames to move down.

            Examples:
            - down
            - down 2
            """.trimIndent()
        )
    )

    /**
     * Get documentation for a command
     */
    fun getDocumentation(command: String): CommandDoc? {
        return executionCommands[command]
            ?: breakpointCommands[command]
            ?: dataCommands[command]
            ?: configCommands[command]
            ?: stackCommands[command]
    }

    /**
     * Get all available commands
     */
    fun getAllCommands(): Set<String> {
        return executionCommands.keys +
               breakpointCommands.keys +
               dataCommands.keys +
               configCommands.keys +
               stackCommands.keys
    }
}

/**
 * Documentation for a single GDB command
 */
data class CommandDoc(
    val syntax: String,
    val summary: String,
    val description: String
)
