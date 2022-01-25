plugins {
    kotlin("jvm")
}

dependencies {
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0")

    // AWS
    implementation("aws.sdk.kotlin:s3:0.11.0-beta")
}

testing {
    suites {
        create("testIntegration", JvmTestSuite::class) {
            useJUnitJupiter()
            dependencies {
                // File Storage
                implementation(projects.fileStorage)

                // Kotest
                implementation("io.kotest:kotest-runner-junit5-jvm:5.0.3")
                implementation("io.kotest:kotest-assertions-core-jvm:5.0.3")
                implementation("io.kotest:kotest-property-jvm:5.0.3")
                implementation("io.kotest:kotest-assertions-json-jvm:5.0.3")
                implementation("io.kotest.extensions:kotest-extensions-testcontainers:1.1.1")

                // Test Containers
                implementation("org.testcontainers:testcontainers:1.16.2")
                implementation("org.testcontainers:junit-jupiter:1.16.2")
                implementation("org.testcontainers:localstack:1.16.2")

                // AWS
                implementation("com.amazonaws:aws-java-sdk-core:1.12.145") // Used ONLY for localstack credential provider
                implementation("aws.sdk.kotlin:s3:0.11.0-beta")

                // Mockk
                implementation("io.mockk:mockk:1.12.1")
            }
        }
    }
}
