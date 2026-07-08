package com.electron.designsystem.components.icon.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

/** Token-based icon sizes. UI models never carry raw Dp values. */
public enum class IconSize { Xs, Sm, Md, Lg, Xl }

/** Semantic tint roles resolved against the active theme. */
public enum class IconTone { Default, Muted, Brand, Accent, OnBrand, Success, Warning, Error, Info }

public sealed class IconUiModel {

    @Immutable
    public data class Default(
        val imageVector: ImageVector,
        val contentDescription: String? = null,
        val size: IconSize = IconSize.Md,
        val tone: IconTone = IconTone.Default,
        val testTag: String = "electron_icon"
    ) : IconUiModel()
}
