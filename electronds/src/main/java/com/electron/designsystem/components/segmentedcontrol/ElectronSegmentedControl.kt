package com.electron.designsystem.components.segmentedcontrol

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.segmentedcontrol.variants.SegmentedControlDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronSegmentedControl
 *
 * Purpose: switch between 2 to 4 mutually exclusive views or ranges
 * (Day / Week / Month). The screen owns the selected index and reacts to
 * [onOptionSelected], which carries the tapped index.
 *
 * Usage:
 * ```
 * ElectronSegmentedControl(
 *     uiModel = SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = uiState.rangeIndex),
 *     onOptionSelected = viewModel::onRangeSelected
 * )
 * ```
 */
@Composable
public fun ElectronSegmentedControl(
    uiModel: SegmentedControlUiModel,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is SegmentedControlUiModel.Default -> SegmentedControlDefault(uiModel, onOptionSelected, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronSegmentedControlPreview() {
    ElectronPreviewSurface {
        ElectronSegmentedControl(
            SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = 1),
            onOptionSelected = {}
        )
        ElectronSegmentedControl(
            SegmentedControlUiModel.Default(options = listOf("kWh", "€"), selectedIndex = 0, isEnabled = false),
            onOptionSelected = {}
        )
    }
}
