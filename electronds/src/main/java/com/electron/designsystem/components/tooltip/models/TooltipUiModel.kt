package com.electron.designsystem.components.tooltip.models

import androidx.compose.runtime.Immutable

/**
 * Tooltip UI models. Tooltips explain; they never hold the only way to do
 * something, because touch users only see them on long-press.
 */
public sealed class TooltipUiModel {

    /** Short label for an icon-only control ("Settings"). Dismisses itself. */
    @Immutable
    public data class Plain(
        val text: String,
        val testTag: String = "electron_tooltip_plain"
    ) : TooltipUiModel()

    /** Explanation with an optional title and action. Stays until dismissed. */
    @Immutable
    public data class Rich(
        val text: String,
        val title: String? = null,
        val actionLabel: String? = null,
        val testTag: String = "electron_tooltip_rich"
    ) : TooltipUiModel()
}
