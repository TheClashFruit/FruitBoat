plugins {
    kotlin("jvm") version "1.8.20"
    application

    id("com.github.gmazzo.buildconfig") version "4.0.4"
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

buildConfig {
    packageName("me.theclashfruit.fruitboat")

    buildConfigField("String", "VERSION", provider { "\"${project.version}\"" })
    buildConfigField("long", "BUILD_TIME", "${System.currentTimeMillis()}L")
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

    build {
        dependsOn(fatJar)
    }
}

kotlin {
    jvmToolchain(11)
}

java {
    withSourcesJar()
}

application {
    mainClass.set("me.theclashfruit.fruitboat.FruitBoatKt")
}