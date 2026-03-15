package dev.yamh.io.convention.source.plugin

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import dev.yamh.io.convention.core.ext.libs
import dev.yamh.io.convention.core.ext.moduleName
import dev.yamh.io.convention.source.plugin.base.BaseConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for configuring library modules in a multiplatform project.
 *
 * Uses `com.android.kotlin.multiplatform.library` (AGP 9.0) instead of `com.android.library`.
 */
class LibraryConventionPlugin : BaseConventionPlugin() {

    /**
     * Applies the Kotlin Multiplatform and Android KMP Library plugins to the project.
     */
    override fun Project.configurePlugin() = with(project.pluginManager) {
        apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        apply(libs.findPlugin("androidMultiplatformLibrary").get().get().pluginId)
    }

    /**
     * Configures the Android platform using the new KMP android library DSL.
     */
    override fun Project.configureAndroidPlatform() {
        val minSdkVersion = libs.findVersion("minSdk").get().requiredVersion.toInt()
        val targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()

        extensions.getByType<KotlinMultiplatformExtension>().apply {
            targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
                namespace = moduleName
                compileSdk = targetSdk
                minSdk = minSdkVersion
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                }
            }
        }
    }

    /**
     * Configures the iOS platform for the library module.
     *
     * Note: androidTarget() is no longer called — the
     * `com.android.kotlin.multiplatform.library` plugin registers it automatically.
     */
    override fun Project.configureIOsPlatform() {
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            listOf(
                iosX64(),
                iosArm64(),
                iosSimulatorArm64()
            ).forEach { iosTarget ->
                iosTarget.binaries.framework {
                    baseName = moduleName.replace(".", "-")
                    isStatic = true
                }
            }
            jvm("desktop")
        }
    }

    /**
     * Configures common dependencies for all platforms.
     *
     * @see <a href="https://slack-chats.kotlinlang.org/t/23173883/web-target-it-works-with-ios-android-desktop-but-now-my-wasm">Slack discussion</a>
     */
    override fun Project.configureCommonDependencies() {
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            explicitApi()
            sourceSets.apply {
commonMain.dependencies {
                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                    implementation(libs.findLibrary("androidx-lifecycle-viewmodel").get())
                    implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                }
            }
        }
    }

}
