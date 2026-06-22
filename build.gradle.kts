plugins {
    kotlin("multiplatform") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"
}

val ktorVersion = "2.2.1"
val kodeinVersion = "7.16.0"

group = "com.libermall"
version = "2.0.0"

repositories {
    mavenCentral()
//    maven("https://jitpack.io")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                devServer?.port = 8081
                cssSupport {
                    enabled = true
                    mode = "extract"
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Dependency Injection
                implementation("org.kodein.di:kodein-di:$kodeinVersion")
                implementation("org.kodein.di:kodein-di-conf:$kodeinVersion")

                // Logging
                implementation("io.github.microutils:kotlin-logging:3.0.4")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

                // Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("com.ionspin.kotlin:bignum-serialization-kotlinx:0.3.9")

                // Big integers
                implementation("com.ionspin.kotlin:bignum:0.3.9")

                // Time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-resources:$ktorVersion")
            }
        }

        val jsMain by getting {
            dependencies {
                dependsOn(commonMain)

                // Framework
                implementation("dev.fritz2:core:1.0-RC2")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-client-js:$ktorVersion")

                implementation("org.jetbrains.kotlin-wrappers:kotlin-extensions:1.0.1-pre.399")

                // Tailwindcss and loaders
                implementation(npm("tailwindcss", "3.2.4"))
                implementation(devNpm("postcss", "^8.4.20"))
                implementation(devNpm("postcss-loader", "7.0.2"))
                implementation(devNpm("autoprefixer", "10.4.13"))

                // Ton connect
//                implementation(npm("@tonconnect/sdk", "0.0.42"))
            }
        }
    }
}
