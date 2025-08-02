import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType
import org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.5.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "org.limepepper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Add generated sources to compilation
sourceSets {
    main {
        java {
            srcDirs("src/main/gen")
        }
    }
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        create("IC", "2025.1")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")
        
        // Development plugins for runIde
//        plugin("PsiViewer", "2025.1")
//        plugin("LivePlugin")
        // plugin("org.jetbrains.plugins.gradle", "251.3")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = """
      Initial version
    """.trimIndent()
    }
    
    // Disable buildSearchableOptions for development
    buildSearchableOptions = false
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "21"
        targetCompatibility = "21"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    withType<PrepareSandboxTask> {
        sandboxDirectory = project.layout.buildDirectory.dir("custom-sandbox")
        sandboxSuffix = ""
        val pluginZips = listOf(
            File("${System.getProperty("user.home")}/Sync/projects/java/CLion-2025.1.2/plugins_ext/LivePlugin.zip"),
            File("${System.getProperty("user.home")}/Sync/projects/java/CLion-2025.1.2/plugins_ext/psiviewer-2025.1.zip")
        )

        doLast {


            val ideGeneralFile =
                sandboxConfigDirectory.file("options/ide.general.xml").get().asFile

            ideGeneralFile.writeText(
                """
                <application>
                  <component name="GeneralSettings">
                    <option name="showTipsOnStartup" value="false" />
                    <option name="confirmExit" value="false" />
                  </component>
                  <component name="StatusBar">
                    <option name="widgets">
                      <map>
                        <entry key="AIAssistant" value="false" />
                        <entry key="webDeployment.default.server.widget" value="false" />
                      </map>
                    </option>
                  </component>
                </application>
            """.trimIndent()
            )

            val uiInfFile = sandboxConfigDirectory.file("options/ui.lnf.xml").get().asFile

            uiInfFile.writeText(
                """
            <application>
              <component name="UISettings">
                <option name="SHOW_MAIN_MENU_MODE" value="SEPARATE_TOOLBAR" />
                <option name="MAX_LOOKUP_WIDTH2" value="1000" />
                <option name="SCROLL_TAB_LAYOUT_IN_EDITOR" value="false" />
                <option name="SHOW_PREVIEW_IN_SEARCH_EVERYWHERE" value="true" />
                <option name="UI_DENSITY" value="COMPACT" />
                <option name="CONTRAST_SCROLLBARS" value="true" />
              </component>
            </application>
            """.trimIndent()
            )
        }
    }
    
    runIde {
        // Configure IDE launch options for better development experience
        jvmArgs = listOf(
            "-Djb.consents.confirmation.enabled=false",
            "-Djb.privacy.policy.text=\"<!--999.999-->\"", // Skip EULA
            "-Didea.suppress.statistics.report=true",
            "-Didea.is.internal=true",
            "-Dide.ui.compact.mode=true",
            "-Dide.main.menu.separate=true",
            "-Didea.auto.reload.plugins=true",
            "-XX:+UnlockDiagnosticVMOptions",
            "-Dide.log.level=DEBUG"
        )
        args(listOf("nosplash"))
        argumentProviders += CommandLineArgumentProvider {
            listOf("${System.getProperty("user.home")}/Sync/projects/java/gdb_plugin/test-project")
        }

        // Open test project automatically
        systemProperty("idea.auto.reload.plugins", "true")
    }
    
    // Configure JFlex lexer generation
    generateLexer {
        sourceFile.set(file("src/main/kotlin/org/limepepper/gdb_plugin/lexer/Gdb.flex"))
        targetOutputDir.set(file("src/main/gen/org/limepepper/gdb_plugin/lexer"))
    }
}
