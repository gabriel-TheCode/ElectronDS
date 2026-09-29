package com.electron.designsystem.components.sheetheader

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.components.sheetheader.variants.SheetHeaderDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronSheetHeader
 *
 * Purpose: standard header row for bottom sheets, with a close affordance,
 * a centered title, and an optional reset action.
 *
 * API:
 * - [uiModel]: visual configuration (sealed [SheetHeaderUiModel]).
 * - [onCloseClick]: emitted when the close affordance is tapped.
 * - [onResetClick]: emitted when the reset action is tapped. Only relevant
 *   when the UI model provides a `resetLabel`.
 *
 * Usage:
 * ```
 * ElectronSheetHeader(
 *     uiModel = SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"),
 *     onCloseClick = viewModel::onFiltersDismissed,
 *     onResetClick = viewModel::onFiltersReset
 * )
 * ```
 */
@Composable
public fun ElectronSheetHeader(
    uiModel: SheetHeaderUiModel,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    onResetClick: () -> Unit = {}
) {
    when (uiModel) {
        is SheetHeaderUiModel.Default -> SheetHeaderDefault(uiModel, onCloseClick, onResetClick, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronSheetHeaderPreview() {
    ElectronPreviewSurface {
        ElectronSheetHeader(
            uiModel = SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"),
            onCloseClick = {},
            onResetClick = {}
        )
        ElectronSheetHeader(
            uiModel = SheetHeaderUiModel.Default(title = "Select account"),
            onCloseClick = {}
        )
    }
}
