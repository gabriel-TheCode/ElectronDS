package com.electron.designsystem.components.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.chip.variants.ChipAssist
import com.electron.designsystem.components.chip.variants.ChipFilter
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronChip
 *
 * Purpose: filter and assist chips with a single API.
 *
 * API:
 * - [uiModel]: visual configuration (sealed [ChipUiModel]).
 * - [onClick]: emitted when the chip body is tapped.
 * - [onClear]: emitted when the clear affordance of a selected
 *   [ChipUiModel.Filter] is tapped. Ignored by [ChipUiModel.Assist].
 *
 * Callbacks are emitted upward and never interpreted here.
 *
 * Usage:
 * ```
 * ElectronChip(
 *     uiModel = ChipUiModel.Filter(defaultText = "Period", valueText = "30 days", isSelected = true),
 *     onClick = viewModel::onPeriodChipClicked,
 *     onClear = viewModel::onPeriodCleared
 * )
 * ```
 */
@Composable
public fun ElectronChip(
    uiModel: ChipUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClear: () -> Unit = {}
) {
    when (uiModel) {
        is ChipUiModel.Filter -> ChipFilter(uiModel, onClick, onClear, modifier)
        is ChipUiModel.Assist -> ChipAssist(uiModel, onClick, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronChipPreview() {
    ElectronPreviewSurface {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronChip(ChipUiModel.Filter(defaultText = "Period"), onClick = {})
            ElectronChip(
                ChipUiModel.Filter(defaultText = "Period", valueText = "30 days", isSelected = true),
                onClick = {},
                onClear = {}
            )
            ElectronChip(ChipUiModel.Assist(text = "Filters", leadingIcon = Icons.Outlined.Tune), onClick = {})
        }
    }
}
