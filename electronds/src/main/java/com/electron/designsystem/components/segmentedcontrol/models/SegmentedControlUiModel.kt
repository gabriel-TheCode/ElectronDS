package com.electron.designsystem.components.segmentedcontrol.models

import androidx.compose.runtime.Immutable

/**
 * Segmented control UI model: a small set (2 to 4) of mutually exclusive
 * options. The selected index is data owned by the screen.
 */
public sealed class SegmentedControlUiModel {

    @Immutable
    public data class Default(
        val options: List<String>,
        val selectedIndex: Int,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_segmented_control"
    ) : SegmentedControlUiModel()
}
