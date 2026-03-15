import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Apply the Kotlin DSL plugin for Gradle build logic
plugins {
    `kotlin-dsl`
}

// Set the group for publishing
group = "dev.yamh.io.convention"

// Configure Java compatibility to version 21
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

// Set the JVM target and context receivers for Kotlin compilation
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}

// Declare dependencies required for the convention plugins
dependencies {
    implementation(libs.org.jetbrains.kotlin.multiplatform.gradle.plugin)
    implementation(libs.gradle)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.compose.gradle.plugin)
}

// Register custom Gradle plugins for different project types
gradlePlugin {
    plugins {
        // Feature module convention plugin
        register("dev.yamh.io.convention.feature") {
            id = "dev.yamh.io.convention.feature"
            implementationClass =
                "dev.yamh.io.convention.source.plugin.FeatureConventionPlugin"
        }
        // Application module convention plugin
        register("dev.yamh.io.convention.application") {
            id = "dev.yamh.io.convention.application"
            implementationClass =
                "dev.yamh.io.convention.source.plugin.ApplicationConventionPlugin"
        }
        // Library module convention plugin
        register("dev.yamh.io.convention.library") {
            id = "dev.yamh.io.convention.library"
            implementationClass =
                "dev.yamh.io.convention.source.plugin.LibraryConventionPlugin"
        }
        // Dependency injection convention plugin
        register("dev.yamh.io.convention.di") {
            id = "dev.yamh.io.convention.di"
            implementationClass =
                "dev.yamh.io.convention.source.plugin.DiConventionPlugin"
        }
    }
}
