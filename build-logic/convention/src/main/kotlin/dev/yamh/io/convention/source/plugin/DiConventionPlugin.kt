package dev.yamh.io.convention.source.plugin

import com.android.build.gradle.LibraryExtension
import com.android.builder.model.AndroidLibrary
import dev.yamh.io.convention.core.ext.libs
import dev.yamh.io.convention.source.plugin.base.BaseConventionPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logging
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for configuring dependency injection (DI) libraries.
 *
 * Applies Koin dependencies to common and Android-specific source sets
 * in a Kotlin Multiplatform project.
 */
class DiConventionPlugin : BaseConventionPlugin() {

    /**
     * Configures common dependencies for all platforms.
     *
     * Adds Koin core, Compose, and ViewModel libraries to the `commonMain` source set.
     */
    override fun Project.configureCommonDependencies() {
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            explicitApi()
            sourceSets.apply {
                commonMain.dependencies {
                    api(libs.findLibrary("koin-core").get())
                    implementation(libs.findLibrary("koin-compose").get())
                    implementation(libs.findLibrary("koin-viewmodel").get())
                }
            }
        }
    }

    /**
     * Configures Android-specific dependencies.
     *
     * Adds Koin Android and Android Compose libraries to the `commonMain` source set.
     */
    override fun Project.configureAndroidDependencies() {
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            sourceSets.apply {
                androidMain.dependencies {
                    implementation(libs.findLibrary("koin-android").get())
                    implementation(libs.findLibrary("koin-android-compose").get())
                }
            }
        }
    }
}