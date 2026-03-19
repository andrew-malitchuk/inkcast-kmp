package presentation.core.styling.core

import androidx.compose.runtime.Composable
import presentation.core.styling.source.provider.LocalThemeColor
import presentation.core.styling.source.provider.LocalThemeFontSize
import presentation.core.styling.source.provider.LocalThemeLineHeight
import presentation.core.styling.source.provider.LocalThemeSpacing
import presentation.core.styling.source.provider.LocalThemeTypography

public object Theme {

    public val color: ThemeColor
        @Composable get() = LocalThemeColor.current

    public val fontSize: ThemeFontSize
        @Composable get() = LocalThemeFontSize.current

    public val lineHeight: ThemeLineHeight
        @Composable get() = LocalThemeLineHeight.current

    public val spacing: ThemeSpacing
        @Composable get() = LocalThemeSpacing.current

    public val typography: ThemeTypography
        @Composable get() = LocalThemeTypography.current
}
