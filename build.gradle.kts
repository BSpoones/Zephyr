plugins {
    kotlin("jvm") version "1.9.0"
}

group = "org.bspoones.zephyr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.slf4j:slf4j-api:1.7.32") // SLF4J dependency
    implementation("ch.qos.logback:logback-classic:1.2.9") // Logback logging implementation
    implementation("net.dv8tion:JDA:5.0.0-beta.17") // JDA
    implementation ("io.github.cdimascio:java-dotenv:5.2.2") // Dotenv
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}