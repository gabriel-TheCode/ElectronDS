package com.electron.designsystem.components.avatar.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Semantic avatar size. Replaces the legacy raw Dp field, which forced the
 * primitive to pattern-match on Dp values to pick an icon size (a silent
 * fallback fired for any non-listed value).
 */
public enum class AvatarSize { Sm, Md, Lg }

/** Semantic tones resolved by the design system. */
public enum class AvatarTone { Brand, Accent, Neutral, Critical }

public sealed class AvatarUiModel {

    @Immutable
    public data class Default(
        val icon: ImageVector,
        val size: AvatarSize = AvatarSize.Md,
        val tone: AvatarTone = AvatarTone.Brand,
        val contentDescription: String? = null,
        val testTag: String = "electron_avatar"
    ) : AvatarUiModel()

    @Immutable
    public data class Initials(
        val initials: String,
        val size: AvatarSize = AvatarSize.Md,
        val tone: AvatarTone = AvatarTone.Neutral,
        val testTag: String = "electron_avatar_initials"
    ) : AvatarUiModel()
}
