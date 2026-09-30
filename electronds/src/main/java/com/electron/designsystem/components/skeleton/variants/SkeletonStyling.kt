package com.electron.designsystem.components.skeleton.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.skeleton.primitives.SkeletonColors
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens

/**
 * Base is the sunken tone, highlight the raised tone: the sweep stays
 * within the surface palette, so a loading screen looks calm, not busy.
 */
@Composable
internal fun skeletonColors(): SkeletonColors {
    val c = ElectronTheme.colors
    return SkeletonColors(base = c.background.surfaceSunken, highlight = c.background.surfaceRaised)
}

internal fun AvatarSize.skeletonSize(): Dp = when (this) {
    AvatarSize.Sm -> ElectronDimens.avatarSm
    AvatarSize.Md -> ElectronDimens.avatarMd
    AvatarSize.Lg -> ElectronDimens.avatarLg
}
