package presentation.core.ui.source.kit.organism.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.shape.SquircleShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun AppBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = SquircleShape(Theme.spacing.spacingXL),
        containerColor = Theme.color.surface,
        contentColor = Theme.color.inkMain,
        dragHandle = null,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing.spacingL)
                .padding(top = Theme.spacing.spacingXL)
                .navigationBarsPadding(),
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = Theme.typography.title,
                    color = Theme.color.inkMain,
                )
                Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
            }
            content()
            Spacer(modifier = Modifier.height(Theme.spacing.spacingL))
        }
    }
}
