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

    data class TonePair(val strong: Color, val subtle: Color, val onStrong: Color)

    val pair = when (tone) {
        TagTone.Brand -> TonePair(c.brand.primary, c.brand.primarySubtle, c.brand.onPrimary)
        TagTone.Accent -> TonePair(c.brand.accent, c.brand.accentSubtle, c.brand.onAccent)
        TagTone.Neutral -> TonePair(c.content.secondary, c.background.surfaceSunken, c.content.inverse)
        TagTone.Success -> TonePair(c.status.success, c.status.successSubtle, c.status.onSuccess)
        TagTone.Warning -> TonePair(c.status.warning, c.status.warningSubtle, c.status.onWarning)
        TagTone.Error -> TonePair(c.status.error, c.status.errorSubtle, c.status.onError)
        TagTone.Info -> TonePair(c.status.info, c.status.infoSubtle, c.status.onInfo)
    }

    return when (style) {
        TagStyle.Filled -> TagColorsResolved(pair.strong, pair.onStrong, pair.strong)
        TagStyle.Tinted -> TagColorsResolved(pair.subtle, pair.strong, pair.subtle)
        TagStyle.Outlined -> TagColorsResolved(Color.Transparent, pair.strong, pair.strong)
    }
}
