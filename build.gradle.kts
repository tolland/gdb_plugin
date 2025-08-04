import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.models.ProductRelease
import org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.intellij.platform") version "2.7.0"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

kotlin {
    jvmToolchain(21)
}

// Configure Grammar-Kit
//grammarKit {
//    // Optional: specify JFlex version if needed
//    // jflexRelease.set("1.7.0-2")
//}

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
        // clion("2025.1.4")
        testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)

        // Add necessary plugin dependencies for compilation here, example:
        // bundledPlugin("com.intellij.java")

        // Development plugins for runIde
//        plugin("PsiViewer", "2025.1")
//        plugin("LivePlugin")
        // plugin("org.jetbrains.plugins.gradle", "251.3")
        pluginVerifier()
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

    pluginVerification {
        ides {
            recommended()
            select {
                types = listOf(IntelliJPlatformType.CLion)
                channels = listOf(ProductRelease.Channel.RELEASE)
            }
        }
    }
}

tasks {
    generateParser {
        sourceFile.set(file("src/main/kotlin/org/limepepper/gdb/parser/Gdb_Revised.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("org/limepepper/gdb/parser/GdbParser.java")
        pathToPsiRoot.set("org/limepepper/gdb/psi")
        purgeOldFiles.set(true)
    }


    // Make sure generation happens before compilation
//    compileKotlin {
//        dependsOn("generateParser", "generateLexer")
//    }
//
//    compileJava {
//        dependsOn("generateParser", "generateLexer")
//    }

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


//        val trustedPathsFile =
//            sandboxConfigDirectory.file("options/trusted-paths.xml").get().asFile
//
//        trustedPathsFile.writeText(
//            """

        // Declare sandbox config files as inputs for configuration cache compatibility
        inputs.files("sandbox-config/ide.general.xml", "sandbox-config/ui.lnf.xml", "sandbox-config/trusted-paths.xml")
            .withPropertyName("sandboxConfigFiles")

        doLast {
            // Use Gradle's built-in copy operations instead of Files.copy for configuration cache compatibility
            val optionsDir = sandboxConfigDirectory.file("options").get().asFile
            optionsDir.mkdirs()

            // Access files through the declared inputs
            val ideGeneralFile = inputs.files.find { it.name == "ide.general.xml" }
            val uiLnfFile = inputs.files.find { it.name == "ui.lnf.xml" }
            val trustedPaths = inputs.files.find { it.name == "trusted-paths.xml" }

            ideGeneralFile?.copyTo(
                optionsDir.resolve("ide.general.xml"),
                overwrite = true
            )

            uiLnfFile?.copyTo(
                optionsDir.resolve("ui.lnf.xml"),
                overwrite = true
            )

            trustedPaths?.copyTo(
                optionsDir.resolve("trusted-paths.xml"),
                overwrite = true
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
            listOf(
                file("test-project").toString()
            )
        }

        // Open test project automatically
        systemProperty("idea.auto.reload.plugins", "true")
    }

    // Configure JFlex lexer generation
//    generateLexer {
//        sourceFile.set(file("src/main/kotlin/org/limepepper/gdb/lexer/Gdb.flex"))
//        targetOutputDir.set(file("src/main/gen/org/limepepper/gdb/lexer"))
//    }
}
