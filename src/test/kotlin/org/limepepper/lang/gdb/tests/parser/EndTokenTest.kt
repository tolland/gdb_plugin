package org.limepepper.lang.gdb.tests.parser

import com.intellij.testFramework.ParsingTestCase
import org.limepepper.lang.gdb.parser.GdbParserDefinition
import java.io.IOException

class EndTokenTest : ParsingTestCase("", "gdb", GdbParserDefinition()) {

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
            print "Something here"

            document mycommand
            a
            This is some documentation for a command. end
            b
            this has en "end" in it, and another end
               end - this one is indented and has trailing chars
            end

            define adder
              print "this is another block with an end"
            end

            # another end in a comment for good measure

            document mycommand
            This one finishes with EOF
            end
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
