import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10" apply false
    kotlin("plugin.spring") version "1.6.10" apply false
    kotlin("plugin.serialization") version "1.6.10" apply false
    id("org.springframework.boot") version "2.6.1" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("com.github.johnrengelman.processes") version "0.5.0" apply false
    id("com.adarshr.test-logger") version "3.1.0" apply false
    id("com.github.kt3k.coveralls") version "2.12.0"
    jacoco
    java
}

// CONFIGURATION THAT APPLIES TO ALL PROJECTS, INCLUDING ROOT
allprojects {
    group = "me.lappy"
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

// CONFIGURATION THAT APPLIES ONLY TO SUBPROJECTS
subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
    apply(plugin = "com.adarshr.test-logger")
    apply(plugin = "java-test-fixtures")

    tasks.withType<Test> {
        useJUnitPlatform()
        finalizedBy(tasks.withType(JacocoReport::class))
    }

    tasks.withType<JacocoReport> {
        reports {
            html.required.set(true)
            xml.required.set(true)
        }
    }

    @Suppress("MagicNumber")
    configure<TestLoggerExtension> {
        theme = ThemeType.MOCHA
        logLevel = LogLevel.LIFECYCLE
        showExceptions = true
        showStackTraces = true
        showFullStackTraces = false
        showCauses = true
        slowThreshold = 2000
        showSummary = true
        showSimpleNames = false
        showPassed = true
        showSkipped = true
        showFailed = true
        showStandardStreams = false
        showPassedStandardStreams = true
        showSkippedStandardStreams = true
        showFailedStandardStreams = true
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    configure<JavaPluginExtension> {
        withSourcesJar()
        withJavadocJar()
    }
}
