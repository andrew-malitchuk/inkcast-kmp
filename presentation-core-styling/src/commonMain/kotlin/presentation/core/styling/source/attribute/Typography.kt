package presentation.core.styling.source.attribute

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import inkcast_kmp.presentation_core_styling.generated.resources.Res
import inkcast_kmp.presentation_core_styling.generated.resources.lato_bold
import inkcast_kmp.presentation_core_styling.generated.resources.lato_regular
import inkcast_kmp.presentation_core_styling.generated.resources.merriweather_bold
import inkcast_kmp.presentation_core_styling.generated.resources.merriweather_regular
import inkcast_kmp.presentation_core_styling.generated.resources.merriweather_semibold
import org.jetbrains.compose.resources.Font
import presentation.core.styling.core.ThemeTypography

/**
 * Builds the Merriweather [FontFamily] from bundled Compose Resources font files.
 *
 * Merriweather is a serif typeface used for display and title text to convey an editorial,
 * book-like feel that aligns with the Inkcast reading experience.
 *
 * @return [FontFamily] containing Normal, SemiBold, and Bold weights.
 */
@Composable
internal fun MerriweatherFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.merriweather_regular, FontWeight.Normal),
        Font(Res.font.merriweather_semibold, FontWeight.SemiBold),
        Font(Res.font.merriweather_bold, FontWeight.Bold),
    )

/**
 * Builds the Lato [FontFamily] from bundled Compose Resources font files.
 *
 * Lato is a sans-serif typeface used for body copy, labels, captions, and action elements
 * to provide clean readability at smaller sizes.
 *
 * @return [FontFamily] containing Normal and Bold weights.
 */
@Composable
internal fun LatoFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.lato_regular, FontWeight.Normal),
        Font(Res.font.lato_bold, FontWeight.Bold),
    )

/**
 * Assembles the complete [ThemeTypography] by combining [attributeFontSize],
 * [attributeLineHeight], font families, and weights into ready-to-use [TextStyle] tokens.
 *
 * Display and title roles use [MerriweatherFontFamily] (serif) for an editorial feel,
 * while body, label, caption, and action roles use [LatoFontFamily] (sans-serif) for
 * UI clarity.
 *
 * @return [ThemeTypography] containing a [TextStyle] for every typographic scale level.
 * @see ThemeTypography
 * @see attributeFontSize
 * @see attributeLineHeight
 */
@Composable
internal fun AttributeTypography(): ThemeTypography {
    val merriweather = MerriweatherFontFamily()
    val lato = LatoFontFamily()

    return ThemeTypography(
        // Serif styles for hero / heading text
        display = TextStyle(
            fontSize = attributeFontSize.display,
            lineHeight = attributeLineHeight.display,
            fontWeight = FontWeight.SemiBold,
            fontFamily = merriweather,
        ),
        title = TextStyle(
            fontSize = attributeFontSize.title,
            lineHeight = attributeLineHeight.title,
            fontWeight = FontWeight.SemiBold,
            fontFamily = merriweather,
        ),
        // Sans-serif styles for UI / body text
        label = TextStyle(
            fontSize = attributeFontSize.label,
            lineHeight = attributeLineHeight.label,
            fontWeight = FontWeight.Normal,
            fontFamily = lato,
        ),
        body = TextStyle(
            fontSize = attributeFontSize.body,
            lineHeight = attributeLineHeight.body,
            fontWeight = FontWeight.Normal,
            fontFamily = lato,
        ),
        bodyEmphasis = TextStyle(
            fontSize = attributeFontSize.bodyEmphasis,
            lineHeight = attributeLineHeight.bodyEmphasis,
            fontWeight = FontWeight.Bold,
            fontFamily = lato,
        ),
        caption = TextStyle(
            fontSize = attributeFontSize.caption,
            lineHeight = attributeLineHeight.caption,
            fontWeight = FontWeight.Normal,
            fontFamily = lato,
        ),
        action = TextStyle(
            fontSize = attributeFontSize.action,
            lineHeight = attributeLineHeight.action,
            fontWeight = FontWeight.Bold,
            fontFamily = lato,
        ),
    )
}
