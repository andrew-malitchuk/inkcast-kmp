package dev.yamh.io.convention.core.ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Contains reference for `libs.versions.toml`
 *
 * @receiver Project
 */
val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

/**
 * Returns the module name for the current project.
 *
 * Converts the project path by removing colons and replacing hyphens with dots.
 *
 * Example: ":feature-login" becomes "feature.login"
 *
 * @receiver Project
 * @return The formatted module name as a String.
 */
val Project.moduleName
    get(): String =
        path.replace(":", "").replace("-", ".")