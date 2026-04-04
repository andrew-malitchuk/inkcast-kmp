package dev.yamh.io.convention.source.plugin.base

import dev.yamh.io.convention.core.ext.moduleName
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Base convention plugin for Gradle projects.
 *
 * This plugin provides a template for applying common configuration and dependencies
 * across modules, supporting both Android and iOS platforms.
 *
 * Subclasses can override the provided open functions to customize behavior.
 */
@Suppress("unused")
open class BaseConventionPlugin : Plugin<Project> {

    /**
     * Applies the convention plugin to the given [target] project.
     * Logs the module path and name, then applies configuration hooks.
     */
    override fun apply(target: Project) {
        target.logger.lifecycle("> Applied convention plugin for module: ${target.path}")
        with(target) {
            logger.lifecycle("> Module name: $moduleName")

            // Apply the base plugin configuration (override to customize)
            configurePlugin()
            // Configure Android and iOS platforms (override to customize)
            configureAndroidPlatform()
            configureIOsPlatform()

            // Configure common dependencies (override to customize)
            configureCommonDependencies()

            // Configure platform-specific dependencies (override to customize)
            configureAndroidDependencies()
            configureIOsDependencies()
        }
    }

    /**
     * Hook for base plugin configuration.
     * Override in subclasses to provide custom configuration.
     */
    open fun Project.configurePlugin() = Unit

    /**
     * Hook for Android platform configuration.
     * Override in subclasses to provide Android-specific setup.
     */
    open fun Project.configureAndroidPlatform() = Unit

    /**
     * Hook for iOS platform configuration.
     * Override in subclasses to provide iOS-specific setup.
     */
    open fun Project.configureIOsPlatform() = Unit

    /**
     * Hook for configuring common dependencies.
     * Override in subclasses to add dependencies shared across platforms.
     */
    open fun Project.configureCommonDependencies() = Unit

    /**
     * Hook for configuring Android-specific dependencies.
     * Override in subclasses to add Android-only dependencies.
     */
    open fun Project.configureAndroidDependencies() = Unit

    /**
     * Hook for configuring iOS-specific dependencies.
     * Override in subclasses to add iOS-only dependencies.
     */
    open fun Project.configureIOsDependencies() = Unit
}