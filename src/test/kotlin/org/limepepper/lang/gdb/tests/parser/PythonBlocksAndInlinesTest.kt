package org.limepepper.lang.gdb.tests.parser

import com.intellij.testFramework.ParsingTestCase
import org.limepepper.lang.gdb.parser.GdbParserDefinition
import java.io.IOException

class PythonBlocksAndInlinesTest : ParsingTestCase("", "gdb", GdbParserDefinition()) {

    /**
     * @return path to test data file directory relative to root of this module.
     */
    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun includeRanges(): Boolean {
        return true
    }

    fun testNestNestedParsing() {
        val content = """
            python
            def switch_inferior_and_continue(x):
                # handle forking in this process
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

            # this is a test

            define my_func
            print "this is myfunc"
            end


            print "test"

        """.trimIndent()
        val myFile = parseFile(
            "randomFile",
            content
        )
        println(toParseTreeText(myFile, true, includeRanges()))
    }

    fun doTestWithDump(checkResult: Boolean, ensureNoErrorElements: Boolean) {
        val name = getTestName()
        try {
            val myFile = parseFile(name, loadFile(name + "." + myFileExt))
            println(toParseTreeText(myFile, true, includeRanges()))
//            if (checkResult) {
//                checkResult(name, myFile)
//                if (ensureNoErrorElements) {
//                    ensureNoErrorElements()
//                }
//            } else {
//                toParseTreeText(myFile, skipSpaces(), includeRanges())
//            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

}
