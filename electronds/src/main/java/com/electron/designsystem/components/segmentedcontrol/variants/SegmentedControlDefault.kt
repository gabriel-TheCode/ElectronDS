package com.electron.designsystem.components.segmentedcontrol.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.segmentedcontrol.primitives.SegmentColors
import com.electron.designsystem.components.segmentedcontrol.primitives.SegmentedControlPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/**
 * Moving indicators (segmented control, navigation) use the brand tint
 * `primarySubtle` with `primaryStrong` content: a neutral raised indicator
 * disappeared on the dark track. Disabled keeps the indicator visible with
 * a neutral step so the current value can still be read.
 */
@Composable
internal fun segmentColors(isEnabled: Boolean): SegmentColors {
    val c = ElectronTheme.colors
    return if (isEnabled) {
        SegmentColors(
            track = c.background.surfaceSunken,
            indicator = c.brand.primarySubtle,
            selectedContent = c.brand.primaryStrong,
            content = c.content.secondary
        )
    } else {
        SegmentColors(
            track = c.interaction.disabledBackground,
            indicator = c.border.default,
            selectedContent = c.content.disabled,
            content = c.interaction.disabledContent
        )
    }
}

@Composable
internal fun SegmentedControlDefault(
    uiModel: SegmentedControlUiModel.Default,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    SegmentedControlPrimitive(
        options = uiModel.options,
        selectedIndex = uiModel.selectedIndex,
        isEnabled = uiModel.isEnabled,
        colors = segmentColors(uiModel.isEnabled),
        pressedColor = ElectronTheme.colors.interaction.pressed,
        textStyle = ElectronTheme.typography.labelLarge,
        testTag = uiModel.testTag,
        onOptionSelected = onOptionSelected,
        modifier = modifier
    )
}
