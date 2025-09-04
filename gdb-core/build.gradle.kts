plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij.platform") version "2.7.2"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = "org.limepepper.lang.gdb"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")

    intellijPlatform {
        intellijIdeaCommunity("2025.1")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "251"
        }

        changeNotes = """
        Initial version - Core language support
        """.trimIndent()
    }

    // Disable buildSearchableOptions for development
    buildSearchableOptions = false
}

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
        enabled = false
    }

    generateLexer {
        sourceFile.set(file("src/main/kotlin/org/limepepper/lang/gdb/lexer/GdbLexer.flex"))
        targetOutputDir.set(file("src/main/gen/org/limepepper/lang/gdb/lexer"))
    }

    generateParser {
        sourceFile.set(file("src/main/kotlin/org/limepepper/lang/gdb/parser/Gdb.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("org/limepepper/lang/gdb/parser/GdbParser.java")
        pathToPsiRoot.set("org/limepepper/lang/gdb/psi")
        purgeOldFiles.set(true)
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
}
