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

@Composable
internal fun MerriweatherFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.merriweather_regular, FontWeight.Normal),
        Font(Res.font.merriweather_semibold, FontWeight.SemiBold),
        Font(Res.font.merriweather_bold, FontWeight.Bold),
    )

@Composable
internal fun LatoFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.lato_regular, FontWeight.Normal),
        Font(Res.font.lato_bold, FontWeight.Bold),
    )

@Composable
internal fun AttributeTypography(): ThemeTypography {
    val merriweather = MerriweatherFontFamily()
    val lato = LatoFontFamily()

    return ThemeTypography(
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
