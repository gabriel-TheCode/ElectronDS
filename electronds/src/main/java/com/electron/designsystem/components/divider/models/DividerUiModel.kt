package com.electron.designsystem.components.divider.models

import androidx.compose.runtime.Immutable

/** Horizontal inset of a divider, resolved to spacing tokens. */
public enum class DividerInset { None, Start, Both }

/** Visual weight of the separator line. */
public enum class DividerEmphasis { Subtle, Default, Strong }

public sealed class DividerUiModel {

    @Immutable
    public data class Horizontal(
        val inset: DividerInset = DividerInset.None,
        val emphasis: DividerEmphasis = DividerEmphasis.Subtle,
        val testTag: String = "electron_divider_horizontal"
    ) : DividerUiModel()

    @Immutable
    public data class Vertical(
        val emphasis: DividerEmphasis = DividerEmphasis.Subtle,
        val testTag: String = "electron_divider_vertical"
    ) : DividerUiModel()
}
