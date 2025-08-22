package org.limepepper.lang.gdb.tests.parser

import com.intellij.testFramework.ParsingTestCase
import org.limepepper.lang.gdb.parser.GdbParserDefinition
import java.io.IOException

class GdbParsingTest : ParsingTestCase("", "gdb", GdbParserDefinition()) {
    fun testParsingTestData() {
        doTestWithDump(true, true)
    }

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
            document mycommand
            Source file and execute command in it
            usage:
            	check_test command/break/label.gdb
            end

            define mycommand
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

        """.trimIndent()
        val myFile = parseFile(
            "randomFile",
            content
        )
        println(toParseTreeText(myFile, true, includeRanges()))
    }

    fun testSourceKwParsing() {
        val content = """
            source /path/to/some/file.gdb
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
