import org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask

plugins {
    idea
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    // Use the official Gradle IntelliJ plugin id 'org.jetbrains.intellij' (not 'org.jetbrains.intellij.platform')
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    id("org.jetbrains.intellij.platform") version "2.7.2"
}

//kotlin {
//    jvmToolchain(21)
//}
//
//// Configure Grammar-Kit
//grammarKit {
////    // Optional: specify JFlex version if needed
////    // jflexRelease.set("1.7.0-2")
//}

// subprojects {
//     apply {
//         plugin("org.jetbrains.intellij.platform.module")
//     }
// }


group = "org.limepepper.lang"
version = "1.0-SNAPSHOT"

allprojects {
    tasks {
        // Set the JVM compatibility versions
        withType<JavaCompile> {
            sourceCompatibility = "21"
            targetCompatibility = "21"
        }
    }
}

project(":") {

    apply {
        plugin("org.jetbrains.intellij.platform")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("idea")
    }

    repositories {
        mavenCentral()
        intellijPlatform {
            defaultRepositories()
        }
        maven { url = uri("https://jitpack.io") }
        flatDir {
            dirs("gdb-core/build/libs")
        }
    }

    // Add generated sources to compilation
    sourceSets {
        main {
            java {
                srcDirs("src/main/gen")
            }
        }
        test {
            java {
                srcDirs("src/main/gen")
            }
            kotlin {
                srcDirs("src/test/kotlin")
            }
        }
    }

    dependencies {
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

        intellijPlatform {
            create("IC", "2025.1")
            // clion("2025.2") // this is the intended target, but is not good for dev
            testFramework(org.jetbrains.intellij.platform.gradle.TestFrameworkType.Platform)
            bundledPlugin("com.intellij.java")
            bundledPlugin("com.jetbrains.sh")
            bundledPlugin("org.intellij.intelliLang")
            plugin("PythonCore", "251.23774.460")
//    plugins("org.intellij.intelliLang")
            // plugin("name.kropp.intellij.makefile", "251.23774.318")
            // plugin("org.intellij.plugins.hcl", "251.23774.426")
            // plugin("DevKit", "251.23774.460")
            pluginVerifier()
            plugin("gdb-core", "1.0-SNAPSHOT")

            //localPlugin("/home/tomhodder/Sync/projects/java/gdb_plugin/gdb-core/build/libs/gdb-core-1.0-SNAPSHOT.jar")

            //implementation(project(":gdb-core"))
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

        generateParser {
            enabled = false
        }

        generateLexer {
            enabled = false
        }

        compileJava {
            dependsOn(":gdb-core:composedJar")
        }


        initializeIntellijPlatformPlugin {
            dependsOn(":gdb-core:composedJar")
        }

        named("compileKotlin") {
            dependsOn("generateLexer", "generateParser")
        }

        named("compileJava") {
            dependsOn("generateLexer", "generateParser")
        }

        named("compileTestKotlin") {
            dependsOn("generateLexer", "generateParser")
        }

        named("compileTestJava") {
            dependsOn("generateLexer", "generateParser")
        }

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

            // Declare sandbox config files as inputs for configuration cache compatibility
            inputs.files(
                "sandbox-config/ide.general.xml",
                "sandbox-config/ui.lnf.xml",
                "sandbox-config/trusted-paths.xml"
            )
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
                "-Dide.log.level=DEBUG",
                // "-Dkotlinx.coroutines.debug=off"
            )

            args(listOf("nosplash"))

            argumentProviders += CommandLineArgumentProvider {
                listOf(
                    file("test-project").toString()
                )
            }

            systemProperty("idea.auto.reload.plugins", "true")
        }

        test {
            systemProperty("LEXER_DEBUG", "true")
            this.testLogging {
                this.showStandardStreams = true
            }
        }
    }
}
