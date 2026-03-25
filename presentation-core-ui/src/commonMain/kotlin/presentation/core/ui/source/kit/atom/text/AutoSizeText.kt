package presentation.core.ui.source.kit.atom.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit

/**
 * future releases:
 * 1. Support maxFontSize
 * 2. Add types: Maximisation, Minimization, Balanced
 */

/**
 * Resizeable text element that automatically shrinks the font to fit the available space.
 *
 * Minimisation is applied by height only. To trigger auto-sizing, either set [maxLines] or
 * place this composable inside a fixed-size container; otherwise the original [fontSize]
 * is used as-is.
 *
 * The shrink algorithm works in four stages (see [SizeDecreasingStage]):
 * 1. **Offense** -- halve the font size until it fits.
 * 2. **Defence** -- grow it back by 20 % while it still fits.
 * 3. **Diplomacy** -- fine-tune by shrinking 5 % until it fits.
 * 4. **Peace** -- final size found, draw content.
 *
 * @param text The text to display.
 * @param modifier Modifier applied to the underlying [Text].
 * @param minFontSize Minimum allowed font size; if reached and text still overflows,
 *   the standard [overflow] behaviour applies.
 * @param keepLineHeight When `true` the line height stays at its original value;
 *   when `false` it scales proportionally with the font size.
 *
 * @see <a href="https://www.figma.com/design/STUB_REPLACE_ME">Figma</a>
 */
@Composable
public fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    minFontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    overflow: TextOverflow = TextOverflow.Clip,
    keepLineHeight: Boolean = false,
) {
    val defaultFontSize =
        coerceTextUnit(
            expected = fontSize,
            default = style.fontSize,
        )
    val defaultLineHeight =
        coerceTextUnit(
            expected = lineHeight,
            default = style.lineHeight,
        )

    val ratio = defaultFontSize.value / defaultLineHeight.value

    var overriddenMetrics by remember(key1 = text) {
        mutableStateOf(
            InnerMetrics(
                fontSize = defaultFontSize,
                lineHeight = defaultLineHeight,
            ),
        )
    }
    var textReadyToDraw by remember(key1 = text) {
        mutableStateOf(false)
    }
    var decreasingStage: SizeDecreasingStage? by remember(key1 = text) {
        mutableStateOf(null)
    }

    Text(
        modifier =
        modifier.drawWithContent {
            // Only draw once the correct font size has been determined
            if (textReadyToDraw) {
                drawContent()
            }
        },
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = overriddenMetrics.fontSize,
        fontFamily = fontFamily,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        lineHeight = overriddenMetrics.lineHeight,
        style = style,
        maxLines = maxLines,
        minLines = minLines,
        softWrap = softWrap,
        overflow = if (textReadyToDraw) overflow else TextOverflow.Clip,
        onTextLayout = { result ->
            if (textReadyToDraw) {
                onTextLayout(result)
                return@Text
            }
            if (minFontSize == TextUnit.Unspecified || overriddenMetrics.fontSize > minFontSize) {
                if (result.didOverflowHeight.not() && decreasingStage == null) {
                    textReadyToDraw = true
                    onTextLayout(result)
                    return@Text
                }

                decreasingStage = decreasingStage.next(result.didOverflowHeight)
                if (decreasingStage == SizeDecreasingStage.Peace) {
                    textReadyToDraw = true
                } else {
                    val correctedFontSize =
                        overriddenMetrics.fontSize.times(decreasingStage!!.value)
                    val correctedLineHeight =
                        if (keepLineHeight) lineHeight else correctedFontSize.div(ratio)
                    overriddenMetrics =
                        overriddenMetrics.copy(
                            fontSize = correctedFontSize,
                            lineHeight = correctedLineHeight,
                        )
                }
            } else {
                if (overriddenMetrics.fontSize <= minFontSize) {
                    val minLineHeight = if (keepLineHeight) lineHeight else minFontSize.div(ratio)
                    overriddenMetrics =
                        InnerMetrics(
                            fontSize = minFontSize,
                            lineHeight = minLineHeight,
                        )
                    textReadyToDraw = true
                }
            }
            onTextLayout(result)
        },
    )
}

internal const val SIZE_DECREASER = 0.9f

/**
 * Stages of the binary-search-like font-size reduction algorithm used by [AutoSizeText].
 *
 * @property value Multiplier applied to the current font size at this stage.
 */
internal enum class SizeDecreasingStage(val value: Float) {
    Offense(0.5f),
    Defence(1.2f),
    Diplomacy(0.95f),
    Peace(Float.NaN),
}

/** Advances to the next stage based on whether the text still overflows. */
internal fun SizeDecreasingStage?.next(didOverflowHeight: Boolean): SizeDecreasingStage {
    return when {
        this == null -> SizeDecreasingStage.Offense
        this == SizeDecreasingStage.Offense && didOverflowHeight -> SizeDecreasingStage.Offense
        this == SizeDecreasingStage.Offense -> SizeDecreasingStage.Defence
        this == SizeDecreasingStage.Defence && didOverflowHeight.not() -> SizeDecreasingStage.Defence
        this == SizeDecreasingStage.Defence -> SizeDecreasingStage.Diplomacy
        this == SizeDecreasingStage.Diplomacy && didOverflowHeight -> SizeDecreasingStage.Diplomacy
        else -> SizeDecreasingStage.Peace
    }
}

/** Intermediate metrics tracked during the auto-size algorithm. */
internal data class InnerMetrics(
    val fontSize: TextUnit,
    val lineHeight: TextUnit,
)

/** Returns [expected] when it is specified, otherwise falls back to [default]. */
internal fun coerceTextUnit(expected: TextUnit, default: TextUnit) =
    if (expected != TextUnit.Unspecified) expected else default
