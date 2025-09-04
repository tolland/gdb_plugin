# Set up catchpoints for exceptions
catch throw

catch
catch h
catch ha
catch han
catch hand
catch handl
catch handle
catch handler
catch handlers


catch handlers Program_Error

catch signal SIGSTOP
commands
  silent
  # printf "🛑 Caught syscall/signal SIGSTOP!\n"
  continue
end

