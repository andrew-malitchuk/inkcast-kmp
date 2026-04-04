package dev.yaxca.io

import androidx.compose.ui.window.ComposeUIViewController

import platform.UIKit.UIViewController

/**
 * Creates the root `UIViewController` for the iOS application.
 *
 * Wraps the shared [App] composable inside a [ComposeUIViewController],
 * which is then hosted by SwiftUI via `UIViewControllerRepresentable`.
 *
 * @return A `UIViewController` rendering the Compose Multiplatform UI tree.
 *
 * @see App
 */
public fun MainViewController(): UIViewController = ComposeUIViewController { App() }