package com.electron.catalog.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.inputfield.ElectronInputField
import com.electron.designsystem.components.inputfield.models.InputFieldUiModel
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Feature-level example of the article's flow:
 * Screen UI state -> UI mapper -> Component UI model -> callbacks back up.
 *
 * In a production app the state below lives in a ViewModel; a local state
 * holder keeps the catalog dependency-free while showing the same shape.
 */
private data class PayeeFormUiState(
    val name: String = "",
    val iban: String = "",
    val isSubmitting: Boolean = false
) {
    val ibanError: String?
        get() = if (iban.isNotEmpty() && iban.length < 15) "IBAN looks too short" else null
    val canSubmit: Boolean
        get() = name.isNotBlank() && iban.length >= 15 && !isSubmitting
}

/** UI mapper: feature state in, component UI models out. */
private fun PayeeFormUiState.toNameFieldUiModel() = InputFieldUiModel.Default(
    value = name,
    label = "Payee name",
    placeholder = "Jane Doe",
    testTag = "payee_name_field"
)

private fun PayeeFormUiState.toIbanFieldUiModel() = InputFieldUiModel.Default(
    value = iban,
    label = "IBAN",
    helperText = "International format",
    isError = ibanError != null,
    errorText = ibanError,
    testTag = "payee_iban_field"
)

private fun PayeeFormUiState.toSubmitButtonUiModel(): ButtonUiModel =
    if (isSubmitting) {
        ButtonUiModel.Loading(text = "Saving", isFullWidth = true)
    } else {
        ButtonUiModel.Primary(text = "Save payee", isFullWidth = true, isEnabled = canSubmit)
    }

@Composable
fun PayeeFormSection() {
    var uiState by rememberSaveable(
        stateSaver = androidx.compose.runtime.saveable.mapSaver(
            save = { mapOf("name" to it.name, "iban" to it.iban) },
            restore = { PayeeFormUiState(it["name"] as String, it["iban"] as String) }
        )
    ) { mutableStateOf(PayeeFormUiState()) }

    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
        ElectronInputField(
            uiModel = uiState.toNameFieldUiModel(),
            onValueChange = { uiState = uiState.copy(name = it) }
        )
        ElectronInputField(
            uiModel = uiState.toIbanFieldUiModel(),
            onValueChange = { uiState = uiState.copy(iban = it.uppercase()) }
        )
        ElectronButton(
            uiModel = uiState.toSubmitButtonUiModel(),
            onClick = { uiState = uiState.copy(isSubmitting = true) }
        )
    }
}
