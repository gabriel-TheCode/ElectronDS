package com.electron.designsystem.components.inputfield.models

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Immutable

/**
 * Input field UI model.
 *
 * The value is data provided by the screen (single source of truth in the
 * ViewModel); edits are emitted upward through onValueChange on the
 * component. Helper and error texts are plain data, not slots, so the
 * design system fully owns their placement and styling.
 */
public sealed class InputFieldUiModel {

    @Immutable
    public data class Default(
        val value: String = "",
        val label: String? = null,
        val placeholder: String? = null,
        val helperText: String? = null,
        val errorText: String? = null,
        val isError: Boolean = false,
        val isEnabled: Boolean = true,
        val isReadOnly: Boolean = false,
        val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        val testTag: String = "electron_input_field"
    ) : InputFieldUiModel()
}
