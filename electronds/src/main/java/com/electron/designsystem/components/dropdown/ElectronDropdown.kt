package com.electron.designsystem.components.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.dropdown.models.DropdownUiModel
import com.electron.designsystem.components.dropdown.variants.DropdownDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronDropdown
 *
 * Purpose: select one value in a form, with the same look as
 * ElectronInputField. Composes ElectronMenu for the option list.
 *
 * API:
 * - [uiModel]: options, selection and field state (sealed [DropdownUiModel]).
 * - [onFieldClick]: signal emitted when the field is tapped (typically
 *   toggles `isExpanded`).
 * - [onOptionSelected]: signal emitted with the index of the chosen option.
 * - [onDismissRequest]: signal emitted when the open menu is dismissed
 *   without a choice (outside tap, back press).
 *
 * Usage:
 * ```
 * ElectronDropdown(
 *     uiModel = DropdownUiModel.Default(
 *         label = "Tariff",
 *         options = uiState.tariffs,
 *         selectedIndex = uiState.tariffIndex,
 *         isExpanded = uiState.isTariffMenuOpen
 *     ),
 *     onFieldClick = viewModel::onTariffFieldClicked,
 *     onOptionSelected = viewModel::onTariffSelected,
 *     onDismissRequest = viewModel::onTariffMenuDismissed
 * )
 * ```
 */
@Composable
public fun ElectronDropdown(
    uiModel: DropdownUiModel,
    onFieldClick: () -> Unit,
    onOptionSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is DropdownUiModel.Default -> DropdownDefault(uiModel, onFieldClick, onOptionSelected, onDismissRequest, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronDropdownPreview() {
    ElectronPreviewSurface {
        ElectronDropdown(
            DropdownUiModel.Default(label = "Tariff", options = listOf("Standard", "Off-peak", "Dynamic"), selectedIndex = 1),
            onFieldClick = {}, onOptionSelected = {}, onDismissRequest = {}
        )
        ElectronDropdown(
            DropdownUiModel.Default(
                label = "Vehicle",
                options = listOf("Model 3", "ID.4"),
                placeholder = "Choose a vehicle",
                isError = true,
                errorText = "Select a vehicle to continue"
            ),
            onFieldClick = {}, onOptionSelected = {}, onDismissRequest = {}
        )
    }
}
