package com.electron.designsystem.components.chip.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Chip UI models.
 *
 * The legacy system shipped two disconnected chip implementations
 * with identical purpose but divergent styling paths: same UI,
 * two styling paths. ElectronDS has exactly one chip family.
 */
public sealed class ChipUiModel {

    /**
     * Filter chip: shows [defaultText] when idle and [valueText] once a
     * value is applied, with a clear affordance.
     */
    @Immutable
    public data class Filter(
        val defaultText: String,
        val valueText: String = "",
        val isSelected: Boolean = false,
        val isEnabled: Boolean = true,
        val expandIconContentDescription: String? = null,
        val clearIconContentDescription: String? = null,
        val testTag: String = "electron_chip_filter"
    ) : ChipUiModel()

    /** Assist chip: a single tappable suggestion or shortcut. */
    @Immutable
    public data class Assist(
        val text: String,
        val leadingIcon: ImageVector? = null,
        val isSelected: Boolean = false,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_chip_assist"
    ) : ChipUiModel()
}

/** Signals emitted by chips; interpreted only by the screen. */
public sealed interface ChipSignal {
    public data object Clicked : ChipSignal
    public data object Cleared : ChipSignal
}
