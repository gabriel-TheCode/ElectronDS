package com.electron.designsystem.components.sheetheader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.sheetheader.models.SheetHeaderSignal
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.components.sheetheader.variants.SheetHeaderDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronSheetHeader
 *
 * Purpose: standard header row for bottom sheets, with a close affordance,
 * a centered title, and an optional reset action.
 *
 * Usage:
 * ```
 * ElectronSheetHeader(
 *     uiModel = SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"),
 *     onSignal = { signal ->
 *         when (signal) {
 *             SheetHeaderSignal.CloseClicked -> viewModel.onFiltersDismissed()
 *             SheetHeaderSignal.ResetClicked -> viewModel.onFiltersReset()
 *         }
 *     }
 * )
 * ```
 */
@Composable
public fun ElectronSheetHeader(
    uiModel: SheetHeaderUiModel,
    onSignal: (SheetHeaderSignal) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is SheetHeaderUiModel.Default -> SheetHeaderDefault(uiModel, onSignal, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronSheetHeaderPreview() {
    ElectronPreviewSurface {
        ElectronSheetHeader(
            uiModel = SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"),
            onSignal = {}
        )
        ElectronSheetHeader(
            uiModel = SheetHeaderUiModel.Default(title = "Select account"),
            onSignal = {}
        )
    }
}
