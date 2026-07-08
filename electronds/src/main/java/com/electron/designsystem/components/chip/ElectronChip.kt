package com.electron.designsystem.components.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.chip.models.ChipSignal
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
 * Signals: chips can emit more than one interaction (tap, clear), so the
 * component exposes a typed [ChipSignal] callback rather than N lambdas.
 * The screen interprets the signal; the chip never does.
 *
 * Usage:
 * ```
 * ElectronChip(
 *     uiModel = ChipUiModel.Filter(defaultText = "Period", valueText = "30 days", isSelected = true),
 *     onSignal = { signal ->
 *         when (signal) {
 *             ChipSignal.Clicked -> viewModel.onPeriodChipClicked()
 *             ChipSignal.Cleared -> viewModel.onPeriodCleared()
 *         }
 *     }
 * )
 * ```
 */
@Composable
public fun ElectronChip(
    uiModel: ChipUiModel,
    onSignal: (ChipSignal) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is ChipUiModel.Filter -> ChipFilter(uiModel, onSignal, modifier)
        is ChipUiModel.Assist -> ChipAssist(uiModel, onSignal, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronChipPreview() {
    ElectronPreviewSurface {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronChip(ChipUiModel.Filter(defaultText = "Period"), onSignal = {})
            ElectronChip(
                ChipUiModel.Filter(defaultText = "Period", valueText = "30 days", isSelected = true),
                onSignal = {}
            )
            ElectronChip(ChipUiModel.Assist(text = "Filters", leadingIcon = Icons.Outlined.Tune), onSignal = {})
        }
    }
}
