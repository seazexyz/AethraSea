pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "AethraSea"

include("folia-api")
include("folia-server")
include("folia-checkstyle")

gradle.lifecycle.beforeProject {
    val mcVersion = providers.gradleProperty("mcVersion").getOrElse("1.20.4").trim()
    val buildChannel = providers.gradleProperty("channel").getOrElse("release").trim().lowercase()
    val buildNumber = providers.environmentVariable("BUILD_NUMBER").orNull?.trim()?.toIntOrNull()

    val versionString = if (buildNumber == null) {
        "$mcVersion-SNAPSHOT"
    } else {
        "$mcVersion-B$buildNumber-$buildChannel"
    }
    
    version = versionString
}