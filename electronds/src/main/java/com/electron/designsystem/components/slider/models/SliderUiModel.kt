package com.electron.designsystem.components.slider.models

import androidx.compose.runtime.Immutable

/**
 * Slider UI models. The value is data owned by the screen; [valueText] is
 * the formatted value shown in the header ("7.4 kW"), produced by the
 * screen's mapper so the design system never formats units.
 */
public sealed class SliderUiModel {

    /** Any value between [valueRangeStart] and [valueRangeEnd]. */
    @Immutable
    public data class Continuous(
        val value: Float,
        val valueRangeStart: Float = 0f,
        val valueRangeEnd: Float = 1f,
        val label: String? = null,
        val valueText: String? = null,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_slider"
    ) : SliderUiModel()

    /** Snaps to [steps] intermediate positions, drawn as ticks on the track. */
    @Immutable
    public data class Stepped(
        val value: Float,
        val steps: Int,
        val valueRangeStart: Float = 0f,
        val valueRangeEnd: Float = 1f,
        val label: String? = null,
        val valueText: String? = null,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_slider_stepped"
    ) : SliderUiModel()
}
