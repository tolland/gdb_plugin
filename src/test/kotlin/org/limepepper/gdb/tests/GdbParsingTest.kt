package org.limepepper.gdb.tests

import com.intellij.testFramework.ParsingTestCase
import org.limepepper.gdb.parser.GdbParserDefinition

class GdbParsingTest : ParsingTestCase("", "gdb", GdbParserDefinition()) {

    override fun getTestDataPath(): String? {
        return "src/test/testData";
    }

    override fun includeRanges(): Boolean {
        return true;
    }

    fun testParsingDataTest() {
        doTest(false);
        println("test succeeded")
    }
}
