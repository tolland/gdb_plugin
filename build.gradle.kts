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
            "-Didea.show.tips.on.startup.default.value=false", // Disable tips
            "-Dide.show.tips.on.startup.default.value=false",
            "-Didea.initially.ask.config=never",
            "-Didea.config.path=\${buildDir}/idea-config",
            "-XX:+UnlockDiagnosticVMOptions"
        )
        
        // Open test project automatically
        systemProperty("idea.auto.reload.plugins", "true")
    }
    
    // Configure JFlex lexer generation
    generateLexer {
        sourceFile.set(file("src/main/kotlin/org/limepepper/gdb_plugin/lexer/Gdb.flex"))
        targetOutputDir.set(file("src/main/gen/org/limepepper/gdb_plugin/lexer"))
    }
}
