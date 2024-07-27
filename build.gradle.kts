import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.bspoones.zephyr"


repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.spongepowered:configurate-gson:4.1.2")
    implementation("org.slf4j:slf4j-api:1.7.32") // SLF4J dependency
    implementation("ch.qos.logback:logback-classic:1.5.6") // Logback logging implementation
    implementation("io.github.cdimascio:java-dotenv:5.2.2") // Dotenv
    implementation(kotlin("reflect"))
    implementation(fileTree("libs"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    manifest {
        attributes(
            "Main-Class" to "org.bspoones.zephyr.LauncherKt"
        )
    }
    mergeServiceFiles()
    from(sourceSets["main"].output)
    from(file("libs")) {
        include("**/*.jar")
    }
}

tasks.named("build") {
    dependsOn("shadowJar")
}

kotlin {
    jvmToolchain(8)
}