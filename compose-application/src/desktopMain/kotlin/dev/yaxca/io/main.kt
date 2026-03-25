package dev.yaxca.io

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

public fun main(): Unit = application {
    Window(
        onCloseRequest = ::exitApplication,
        alwaysOnTop = true,
        title = "Inkcast",
    ) {
        App()
    }
}
