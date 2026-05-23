plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.1.0" // upewnij się, że wersja pasuje do reszty projektu
    id("com.android.library")
}

kotlin {
    androidTarget()
    iosArm64()
    iosSimulatorArm64()
    jvm()
    js { browser() }
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs { browser() }
    kotlin {
        // ... Twoje targety (androidTarget, ios...)

        sourceSets {
            val commonMain by getting {
                dependencies {
                    // To już masz:
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
                    implementation("io.ktor:ktor-client-core:3.0.3")
                    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")
                    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.3")

                    // DODAJ TO (opcjonalnie, ale bardzo pomaga w debugowaniu):
                    implementation("io.ktor:ktor-client-logging:3.0.3")
                }
            }

            // DODAJ CAŁY TEN BLOK:
            val androidMain by getting {
                dependencies {
                    // Silnik OkHttp dla Androida
                    implementation("io.ktor:ktor-client-okhttp:3.0.3")
                }
            }

            // DODAJ CAŁY TEN BLOK:
            val iosMain by creating {
                dependsOn(commonMain)
                dependencies {
                    // Silnik Darwin dla iOS
                    implementation("io.ktor:ktor-client-darwin:3.0.3")
                }
            }
        }
    }

    android {
        namespace = "com.example.technologiainternetowa.shared"
        compileSdk = 34

        defaultConfig {
            minSdk = 24
        }
    }
}