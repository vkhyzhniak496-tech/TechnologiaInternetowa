

plugins {
    alias(libs.plugins.kotlinJvm) // Używamy aliasu z katalogu wersji
    id("io.ktor.plugin") version "3.0.3"
    kotlin("plugin.serialization") version "2.3.20"
}
dependencies {
    implementation(project(":shared")) // To pozwala serwerowi widzieć TramDTO
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")

    // EXPOSED - To naprawi błędy z obraz_17.png
    val exposedVersion = "0.56.0"
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    // Baza danych H2
    implementation("com.h2database:h2:2.2.224")

    implementation("ch.qos.logback:logback-classic:1.4.11")
}