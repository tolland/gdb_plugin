
print "using the python extension syntax"

print "this is some other command"

break main

print "test"

python
def switch_inferior_and_continue(x):
    # handle forking in this process
    print("switching inferior and continuing")
    gdb.execute("inferior %d" % x)
    gdb.execute("continue")


def exit_handler(_event):
    print("in the exit handler")
    has_threads = [ inferior.num for inferior in gdb.inferiors() if inferior.threads() ]
    if has_threads:
        print("have threads")
        has_threads.sort()
        gdb.post_event(lambda: switch_inferior_and_continue(has_threads[0]))

gdb.events.exited.connect(exit_handler)
end

# this is a test

define my_func
print "this is myfunc"
end


print "test"
