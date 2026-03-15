package dev.yamh.io.convention.source.plugin

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import dev.yamh.io.convention.core.ext.libs
import dev.yamh.io.convention.core.ext.moduleName
import dev.yamh.io.convention.source.plugin.base.BaseConventionPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Convention plugin for feature modules.
 *
 * Uses `com.android.kotlin.multiplatform.library` (AGP 9.0) instead of `com.android.library`.
 */
class FeatureConventionPlugin : BaseConventionPlugin() {

    /**
     * Applies the necessary plugins for a feature module.
     */
    override fun Project.configurePlugin() = with(project.pluginManager) {
        apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
        apply(libs.findPlugin("androidMultiplatformLibrary").get().get().pluginId)
        apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
        apply(libs.findPlugin("composeCompiler").get().get().pluginId)
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
                androidResources { enable = true }
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_21)
                }
            }
        }
    }

    /**
     * Configures the iOS platform for the feature module.
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
     * Configures common dependencies for all source sets.
     *
     * @see <a href="https://slack-chats.kotlinlang.org/t/23173883/web-target-it-works-with-ios-android-desktop-but-now-my-wasm">Slack discussion</a>
     */
    override fun Project.configureCommonDependencies() {
        val composeDependencies = extensions.getByType<ComposeExtension>().dependencies
        extensions.getByType<KotlinMultiplatformExtension>().apply {
            explicitApi()
            sourceSets.apply {
                commonMain.dependencies {
                    implementation(composeDependencies.runtime)
                    implementation(composeDependencies.foundation)
                    implementation(composeDependencies.material)
                    implementation(composeDependencies.ui)
                    implementation(composeDependencies.components.resources)
                    implementation(composeDependencies.components.uiToolingPreview)

                    implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                    implementation(libs.findLibrary("androidx-lifecycle-viewmodel").get())
                    implementation(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                }
            }
        }
    }

    /**
     * Configures Android-specific dependencies including Compose UI tooling for previews.
     *
     * Uses `androidRuntimeClasspath` as required by the `com.android.kotlin.multiplatform.library` plugin.
     */
    override fun Project.configureAndroidDependencies() {
        val composeDependencies = extensions.getByType<ComposeExtension>().dependencies
        project.dependencies.add("androidRuntimeClasspath", composeDependencies.uiTooling)
    }

}
