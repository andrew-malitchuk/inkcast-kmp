package presentation.core.ui.source.kit.atom.input

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import presentation.core.styling.core.Theme
import presentation.core.ui.source.kit.atom.button.ButtonSizeType
import presentation.core.ui.source.kit.atom.button.IconButton
import presentation.core.ui.source.kit.atom.shape.SquircleShape

private val DEFAULT_MIN_HEIGHT: Dp = 120.dp
private val DEFAULT_MAX_HEIGHT: Dp = 240.dp

@Composable
public fun MultilineInput(
    modifier: Modifier = Modifier,
    initialText: String = "",
    onTextChanged: (String) -> Unit,
    placeholder: String = "",
    textStyle: TextStyle = Theme.typography.body,
    clearIcon: ImageVector? = null,
    validationRegex: Regex? = null,
    minHeight: Dp = DEFAULT_MIN_HEIGHT,
    maxHeight: Dp = DEFAULT_MAX_HEIGHT,
    keyboardOptions: KeyboardOptions =
        KeyboardOptions(
            imeAction = ImeAction.Default,
            keyboardType = KeyboardType.Text,
            autoCorrectEnabled = false,
            capitalization = KeyboardCapitalization.Sentences,
        ),
    enabled: Boolean = true,
) {
    val handleColor = Theme.color.brand
    val backgroundColor = Theme.color.brand.copy(alpha = 0.4f)

    var text by remember(initialText) { mutableStateOf(initialText) }
    var isError by remember(text) {
        mutableStateOf(validationRegex?.let { !it.matches(text) && text.isNotEmpty() } ?: false)
    }
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> Color.Transparent
            isError -> Theme.color.error
            isFocused -> Theme.color.inkMain
            else -> Theme.color.outlineLow
        },
        label = "MultilineInput: borderColor",
    )

    Row(
        modifier =
        modifier
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = Theme.spacing.spacingXXS,
                color = borderColor,
                shape = SquircleShape(Theme.spacing.spacingXL),
            )
            .padding(
                horizontal = Theme.spacing.spacingL,
                vertical = Theme.spacing.spacingM,
            ),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .heightIn(min = minHeight, max = maxHeight),
        ) {
            val scrollState = rememberScrollState()
            val customTextSelectionColors =
                remember {
                    TextSelectionColors(
                        handleColor = handleColor,
                        backgroundColor = backgroundColor,
                    )
                }

            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                BasicTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        isError = validationRegex?.let { regex -> !regex.matches(it) && it.isNotEmpty() } ?: false
                        onTextChanged(it)
                    },
                    enabled = enabled,
                    textStyle = textStyle.copy(color = Theme.color.inkMain),
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    cursorBrush = SolidColor(Theme.color.brand),
                    keyboardOptions = keyboardOptions,
                    keyboardActions =
                    KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                        onSearch = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                    ),
                )
            }
            if (text.isEmpty()) {
                Text(
                    text = placeholder,
                    style = textStyle,
                    color = Theme.color.inkMain.copy(alpha = 0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        if (clearIcon != null && text.isNotEmpty() && enabled) {
            IconButton(
                icon = clearIcon,
                onClick = {
                    text = ""
                    onTextChanged("")
                },
                size = ButtonSizeType.Small,
            )
        }
    }
}
