package dev.yaxca.io

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable

/**
 * Single-activity entry point for the Android application.
 *
 * Enables edge-to-edge rendering and delegates all UI to the shared
 * Compose [App] composable. Handles `ACTION_SEND` intents to receive
 * URLs shared from other applications.
 *
 * @see App
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val sharedUrl = extractSharedUrl(intent)

        setContent {
            App(sharedUrl = sharedUrl)
        }
    }

    /**
     * Extracts a plain-text URL from an `ACTION_SEND` intent.
     *
     * @param intent The incoming intent, nullable for safety.
     * @return The shared URL string, or `null` if the intent is not a text share.
     */
    private fun extractSharedUrl(intent: Intent?): String? {
        if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            return intent.getStringExtra(Intent.EXTRA_TEXT)
        }
        return null
    }
}

@Composable
fun AppAndroidPreview() {
    App()
}
