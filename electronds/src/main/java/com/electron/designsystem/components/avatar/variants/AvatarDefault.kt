package com.electron.designsystem.components.avatar.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.avatar.primitives.AvatarPrimitive
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronTypography

internal data class AvatarMetrics(val container: Dp, val icon: Dp, val textStyle: TextStyle)

/**
 * Glyphs fill half of the circle at every size, and initials scale with
 * it, so a 24dp and a 64dp avatar look like the same object zoomed.
 */
internal fun AvatarSize.metrics(): AvatarMetrics = when (this) {
    AvatarSize.Sm -> AvatarMetrics(ElectronDimens.avatarSm, ElectronDimens.iconXs, ElectronTypography.labelSmall)
    AvatarSize.Md -> AvatarMetrics(ElectronDimens.avatarMd, ElectronDimens.iconMd, ElectronTypography.titleSmall)
    AvatarSize.Lg -> AvatarMetrics(ElectronDimens.avatarLg, ElectronDimens.iconXl, ElectronTypography.headlineSmall)
}

internal data class AvatarColorsResolved(val background: Color, val content: Color)

@Composable
internal fun AvatarTone.resolve(): AvatarColorsResolved {
    val c = ElectronTheme.colors
    return when (this) {
        AvatarTone.Brand -> AvatarColorsResolved(c.brand.primarySubtle, c.brand.primary)
        AvatarTone.Accent -> AvatarColorsResolved(c.brand.accentSubtle, c.brand.accent)
        AvatarTone.Neutral -> AvatarColorsResolved(c.background.surfaceSunken, c.content.secondary)
        AvatarTone.Critical -> AvatarColorsResolved(c.status.errorSubtle, c.status.error)
    }
}

@Composable
internal fun AvatarDefault(
    uiModel: AvatarUiModel.Default,
    modifier: Modifier = Modifier
) {
    val metrics = uiModel.size.metrics()
    val colors = uiModel.tone.resolve()
    AvatarPrimitive(
        containerSize = metrics.container,
        iconSize = metrics.icon,
        icon = uiModel.icon,
        backgroundColor = colors.background,
        contentColor = colors.content,
        contentDescription = uiModel.contentDescription,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}

@Composable
internal fun AvatarInitials(
    uiModel: AvatarUiModel.Initials,
    modifier: Modifier = Modifier
) {
    val metrics = uiModel.size.metrics()
    val colors = uiModel.tone.resolve()
    AvatarPrimitive(
        containerSize = metrics.container,
        initials = uiModel.initials,
        initialsStyle = metrics.textStyle,
        backgroundColor = colors.background,
        contentColor = colors.content,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
