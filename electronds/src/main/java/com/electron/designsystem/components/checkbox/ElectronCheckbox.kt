package com.electron.designsystem.components.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.checkbox.models.CheckboxUiModel
import com.electron.designsystem.components.checkbox.variants.CheckboxDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronCheckbox
 *
 * Purpose: multi-selection control with an optional inline label.
 * State is hoisted: the screen owns `isChecked` and reacts to
 * [onCheckedChange].
 *
 * Usage:
 * ```
 * ElectronCheckbox(
 *     uiModel = CheckboxUiModel.Default(isChecked = uiState.termsAccepted, label = "I accept the terms"),
 *     onCheckedChange = viewModel::onTermsToggled
 * )
 * ```
 */
@Composable
public fun ElectronCheckbox(
    uiModel: CheckboxUiModel,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is CheckboxUiModel.Default -> CheckboxDefault(uiModel, onCheckedChange, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronCheckboxPreview() {
    ElectronPreviewSurface {
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = true, label = "Notify me"), onCheckedChange = {})
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = false, label = "Accept terms", isError = true), onCheckedChange = {})
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = true, label = "Disabled", isEnabled = false), onCheckedChange = {})
    }
}
