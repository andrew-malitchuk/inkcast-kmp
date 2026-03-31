package dev.yaxca.io.di

/**
 * Convenience wrapper for [initKoin] used by iOS entry point.
 *
 * Called from Swift via Kotlin/Native to bootstrap the DI graph
 * without providing platform-specific configuration.
 *
 * @see initKoin
 */
public fun doInitKoin() {
    initKoin()
}
