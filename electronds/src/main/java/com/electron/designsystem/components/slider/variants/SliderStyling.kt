package com.electron.designsystem.components.slider.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.slider.primitives.SliderColors
import com.electron.designsystem.components.slider.primitives.SliderPrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun sliderColors(isEnabled: Boolean): SliderColors {
    val c = ElectronTheme.colors
    return if (isEnabled) {
        SliderColors(
            activeTrack = c.brand.primary,
            inactiveTrack = c.background.surfaceSunken,
            thumb = c.background.surfaceRaised,
            thumbRing = c.brand.primary,
            activeTick = c.brand.onPrimary,
            inactiveTick = c.border.strong,
            label = c.content.secondary,
            value = c.content.primary
        )
    } else {
        SliderColors(
            activeTrack = c.interaction.disabledContent,
            inactiveTrack = c.interaction.disabledBackground,
            thumb = c.background.surface,
            thumbRing = c.interaction.disabledContent,
            activeTick = c.background.surface,
            inactiveTick = c.interaction.disabledContent,
            label = c.content.disabled,
            value = c.content.disabled
        )
    }
}

/** Shared rendering: label in labelLarge, value in the data typeface. */
@Composable
internal fun SliderRender(
    value: Float,
    valueRangeStart: Float,
    valueRangeEnd: Float,
    steps: Int,
    label: String?,
    valueText: String?,
    isEnabled: Boolean,
    testTag: String,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    modifier: Modifier
) {
    SliderPrimitive(
        value = value,
        valueRange = valueRangeStart..valueRangeEnd,
        steps = steps,
        isEnabled = isEnabled,
        label = label,
        valueText = valueText,
        colors = sliderColors(isEnabled),
        labelStyle = ElectronTheme.typography.labelLarge,
        valueStyle = ElectronTheme.typography.dataMedium,
        testTag = testTag,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
        modifier = modifier
    )
}
