package com.electron.designsystem.components.tag.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.tag.models.TagSize
import com.electron.designsystem.components.tag.models.TagStyle
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.tokens.ElectronTypography

internal data class TagColorsResolved(
    val background: Color,
    val content: Color,
    val border: Color
)

internal data class TagMetrics(
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val iconSize: Dp,
    val textStyle: TextStyle
)

internal fun TagSize.metrics(): TagMetrics = when (this) {
    TagSize.Sm -> TagMetrics(ElectronSpacing.sm, ElectronSpacing.xxs, ElectronDimens.iconXs, ElectronTypography.labelSmall)
    TagSize.Md -> TagMetrics(ElectronSpacing.md, ElectronSpacing.xs, ElectronDimens.iconSm, ElectronTypography.labelMedium)
}

@Composable
internal fun resolveTagColors(style: TagStyle, tone: TagTone): TagColorsResolved {
    val c = ElectronTheme.colors

    // strong: text and outline on a background; fill + onFill: the Filled style.
    data class ToneSet(val strong: Color, val subtle: Color, val fill: Color, val onFill: Color)

    val pair = when (tone) {
        TagTone.Brand -> ToneSet(c.brand.primary, c.brand.primarySubtle, c.brand.primaryFill, c.brand.onPrimary)
        TagTone.Accent -> ToneSet(c.brand.accent, c.brand.accentSubtle, c.brand.accentFill, c.brand.onAccent)
        TagTone.Neutral -> ToneSet(c.content.secondary, c.background.surfaceSunken, c.content.secondary, c.content.inverse)
        TagTone.Success -> ToneSet(c.status.success, c.status.successSubtle, c.status.successFill, c.status.onSuccess)
        TagTone.Warning -> ToneSet(c.status.warning, c.status.warningSubtle, c.status.warningFill, c.status.onWarning)
        TagTone.Error -> ToneSet(c.status.error, c.status.errorSubtle, c.status.errorFill, c.status.onError)
        TagTone.Info -> ToneSet(c.status.info, c.status.infoSubtle, c.status.infoFill, c.status.onInfo)
    }

    return when (style) {
        TagStyle.Filled -> TagColorsResolved(pair.fill, pair.onFill, pair.fill)
        TagStyle.Tinted -> TagColorsResolved(pair.subtle, pair.strong, pair.subtle)
        TagStyle.Outlined -> TagColorsResolved(Color.Transparent, pair.strong, pair.strong)
    }
}
