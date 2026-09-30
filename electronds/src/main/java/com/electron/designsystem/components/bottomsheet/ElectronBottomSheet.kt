package com.electron.designsystem.components.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.bottomsheet.models.BottomSheetUiModel
import com.electron.designsystem.components.bottomsheet.variants.BottomSheetDefault
import com.electron.designsystem.components.bottomsheet.variants.BottomSheetDefaultBody
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronBottomSheet
 *
 * Purpose: modal sheet for secondary tasks (filters, pickers, details)
 * that should keep the screen underneath in context. Composes
 * ElectronSheetHeader; the body is a content slot.
 *
 * API:
 * - [uiModel]: sheet frame (sealed [BottomSheetUiModel]).
 * - [onDismissRequest]: signal emitted when the sheet is dismissed: swipe
 *   down, scrim tap, back press, or the header close button (after the
 *   sheet has slid out).
 * - [onResetClick]: signal emitted by the header reset action. Only
 *   relevant when the UI model sets `title` and `resetLabel`.
 *
 * Usage:
 * ```
 * if (uiState.showFilters) {
 *     ElectronBottomSheet(
 *         uiModel = BottomSheetUiModel.Default(title = "Filters", resetLabel = "Reset"),
 *         onDismissRequest = viewModel::onFiltersDismissed,
 *         onResetClick = viewModel::onFiltersReset
 *     ) {
 *         FiltersContent(uiState.filters)
 *     }
 * }
 * ```
 */
@Composable
public fun ElectronBottomSheet(
    uiModel: BottomSheetUiModel,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onResetClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    when (uiModel) {
        is BottomSheetUiModel.Default -> BottomSheetDefault(uiModel, onDismissRequest, onResetClick, modifier, content)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronBottomSheetPreview() {
    ElectronPreviewSurface {
        Column(
            modifier = Modifier.background(ElectronTheme.colors.background.surfaceRaised, ElectronShapes.sheet)
        ) {
            BottomSheetDefaultBody(
                uiModel = BottomSheetUiModel.Default(title = "Charging schedule", resetLabel = "Reset"),
                onCloseClick = {},
                onResetClick = {}
            ) {
                ElectronListItem(
                    ListItemUiModel.Toggle(
                        title = "Off-peak only",
                        subtitle = "22:00 to 06:00",
                        isChecked = true,
                        leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Schedule, tone = IconTone.Muted))
                    )
                )
            }
        }
    }
}
