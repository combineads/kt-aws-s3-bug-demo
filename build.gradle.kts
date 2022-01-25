import com.adarshr.gradle.testlogger.TestLoggerExtension
import com.adarshr.gradle.testlogger.theme.ThemeType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10" apply false
    kotlin("plugin.spring") version "1.6.10" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("com.adarshr.test-logger") version "3.1.0" apply false
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
    apply(plugin = "com.adarshr.test-logger")

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

        toolchain {
            version = "17"
            vendor.set(JvmVendorSpec.ADOPTOPENJDK)
        }
    }
}
