package dev.yaxca.io

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Desktop (JVM) entry point for the Inkcast application.
 *
 * Creates a single always-on-top window and renders the shared [App] composable.
 */
public fun main(): Unit = application {
    Window(
        onCloseRequest = ::exitApplication,
        alwaysOnTop = true,
        title = "Inkcast",
    ) {
        App()
    }
}
