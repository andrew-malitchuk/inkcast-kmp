@file:Suppress("UnstableApiUsage")

/**
 * Configures dependency resolution management for the build logic project.
 * - Sets up repositories: Google, Maven Central, and Gradle Plugin Portal.
 * - Defines a version catalog named `libs` from the specified TOML file.
 */
dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")