package org.limepepper.gdb;

import com.intellij.psi.PsiElement;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

public class GdbCodeInsightTest extends LightJavaCodeInsightFixtureTestCase {
    protected String getTestDataPath() {
        return "src/test/testData";
    }

    public void testDocumentation() {
        System.out.println("fuck");
    }

}
