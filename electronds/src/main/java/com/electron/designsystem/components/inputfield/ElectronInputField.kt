package com.electron.designsystem.components.inputfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.inputfield.models.InputFieldUiModel
import com.electron.designsystem.components.inputfield.variants.InputFieldDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronInputField
 *
 * Purpose: single-line text entry with label, placeholder, helper and
 * error messaging.
 *
 * State hoisting: the screen owns the value; every edit is emitted through
 * [onValueChange] and flows back down through a new UI model instance.
 *
 * Usage:
 * ```
 * ElectronInputField(
 *     uiModel = InputFieldUiModel.Default(
 *         value = uiState.iban,
 *         label = "IBAN",
 *         isError = uiState.ibanError != null,
 *         errorText = uiState.ibanError
 *     ),
 *     onValueChange = viewModel::onIbanChanged
 * )
 * ```
 */
@Composable
public fun ElectronInputField(
    uiModel: InputFieldUiModel,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is InputFieldUiModel.Default -> InputFieldDefault(uiModel, onValueChange, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronInputFieldPreview() {
    ElectronPreviewSurface {
        ElectronInputField(
            uiModel = InputFieldUiModel.Default(value = "CH93 0076 2011 6238 5295 7", label = "IBAN", helperText = "Swiss format"),
            onValueChange = {}
        )
        ElectronInputField(
            uiModel = InputFieldUiModel.Default(value = "abc", label = "Amount", isError = true, errorText = "Numbers only"),
            onValueChange = {}
        )
        ElectronInputField(
            uiModel = InputFieldUiModel.Default(value = "", label = "Reference", placeholder = "Optional", isEnabled = false),
            onValueChange = {}
        )
    }
}
