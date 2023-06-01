import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "io.github.pedroagrs.buttons"
version = "1.0.0"

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveFileName.set("buttons-${version}.jar")

    println("Shadowing ${project.name} to ${destinationDirectory.get()}")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    sourceCompatibility = "11"
    targetCompatibility = "11"
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    compileOnly("com.comphenix.protocol:ProtocolLib:5.0.0")
    compileOnly("org.bukkit:bukkit:1.8.8-R0.1-SNAPSHOT")

    implementation("com.github.ben-manes.caffeine:caffeine:3.1.6")
    implementation("com.google.code.findbugs:jsr305:3.0.2")
}

bukkit {
    name = "minecraft-buttons"
    prefix = "Buttons"
    apiVersion = "1.8"
    version = "${project.version}"
    website = "github.com/pedroagrs"
    main = "io.pedroagrs.github.buttons.ButtonPlugin"
    description = "Create buttons packet-based using ProtocolLib"
    author = "Pedro Aguiar"
    depend = listOf("ProtocolLib")
}
