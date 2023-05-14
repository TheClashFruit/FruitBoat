import de.fuerstenau.gradle.buildconfig.GenerateBuildConfigTask

buildscript {
    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("gradle.plugin.de.fuerstenau:BuildConfigPlugin:1.1.8")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.0.1-2")
    }
}

plugins {
    kotlin("jvm") version "1.8.20"
    application

    id("de.fuerstenau.buildconfig") version "1.1.8" apply false
}

group = "me.theclashfruit"
version = "0.1.0-alpha+discord"

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://plugins.gradle.org/m2/")
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(kotlin("stdlib-jdk8"))

    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("dev.kord:kord-core:0.9.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
        archiveClassifier.set("standalone") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }

    val generateBuildConfig = register<GenerateBuildConfigTask>("generateBuildConfig") {
        outputDir = File("${buildDir}/gen/buildconfig/src/main")

        appName = "FruitBoat"
        clsName = "BuildConfig"
        packageName = "me.theclashfruit.fruitboat"
    }

    val compileBuildConfig = register<JavaCompile>("compileBuildConfig") {
        dependsOn(generateBuildConfig) // Trigger build config generation during build

        classpath = files() // we need no extra class path

        destinationDir = File("${buildDir}/gen/buildconfig/classes/main/")

        source = fileTree(generateBuildConfig.get().outputDir)
    }

    build {
        dependsOn(compileBuildConfig)
        dependsOn(fatJar)
    }
}

kotlin {
    jvmToolchain(11)

    sourceSets {
        main {
            this.kotlin.srcDir("$buildDir/gen/buildconfig/src/")
        }
    }
}

java {
    withSourcesJar()
}

application {
    mainClass.set("me.theclashfruit.fruitboat.FruitBoatKt")
}