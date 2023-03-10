plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.13.1"
}

group = "org.intellij.sdk"
version = "2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("2022.1.4")
    plugins.set(listOf("com.intellij.java"))
}

tasks {
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        version.set("${project.version}")
        sinceBuild.set("221")
        untilBuild.set("223.*")
    }
}