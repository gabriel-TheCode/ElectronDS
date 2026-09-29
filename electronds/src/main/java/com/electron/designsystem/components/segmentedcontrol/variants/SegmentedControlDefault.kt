package com.electron.designsystem.components.segmentedcontrol.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.segmentedcontrol.primitives.SegmentColors
import com.electron.designsystem.components.segmentedcontrol.primitives.SegmentedControlPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/**
 * The selected label is neutral (`content.primary`), not brand: the raised
 * indicator already says "selected", and brand color stays reserved for
 * primary actions, focus and selection that has no other signifier.
 */
@Composable
internal fun segmentColors(isEnabled: Boolean): SegmentColors {
    val c = ElectronTheme.colors
    return if (isEnabled) {
        SegmentColors(
            track = c.background.surfaceSunken,
            indicator = c.background.surfaceRaised,
            selectedContent = c.content.primary,
            content = c.content.secondary
        )
    } else {
        SegmentColors(
            track = c.interaction.disabledBackground,
            indicator = c.background.surface,
            selectedContent = c.interaction.disabledContent,
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
        textStyle = ElectronTheme.typography.labelLarge,
        testTag = uiModel.testTag,
        onOptionSelected = onOptionSelected,
        modifier = modifier
    )
}
