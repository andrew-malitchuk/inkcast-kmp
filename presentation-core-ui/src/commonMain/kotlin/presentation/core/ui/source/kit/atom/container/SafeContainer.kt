package presentation.core.ui.source.kit.atom.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.core.ui.source.kit.atom.snackbar.StackedSnackbarHost
import presentation.core.ui.source.kit.atom.snackbar.StackedSnakbarHostState
import presentation.core.ui.source.kit.atom.snackbar.rememberStackedSnackbarHostState

@Composable
public fun SafeContainer(
    modifier: Modifier = Modifier,
    snackbarHostState: StackedSnakbarHostState = rememberStackedSnackbarHostState(),
    snackbarHost: @Composable (StackedSnakbarHostState) -> Unit = {
        Box(
            modifier =
            Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            StackedSnackbarHost(hostState = it)
        }
    },
    content: @Composable (PaddingValues) -> Unit,
) {
    val windowInsets = WindowInsets.systemBars.union(WindowInsets.ime)

    Scaffold(
        snackbarHost = { snackbarHost(snackbarHostState) },
        contentWindowInsets = windowInsets,
        modifier = modifier.fillMaxSize(),
        content = { paddingValues ->
            Box(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .consumeWindowInsets(paddingValues),
            ) {
                content(paddingValues)
            }
        },
    )
}
