package com.electron.designsystem.components.checkbox.models

import androidx.compose.runtime.Immutable

/**
 * Checkbox UI model. The checked state is data owned by the screen; the
 * whole row (box + label) is one touch target.
 */
public sealed class CheckboxUiModel {

    @Immutable
    public data class Default(
        val isChecked: Boolean,
        val label: String? = null,
        val isEnabled: Boolean = true,
        val isError: Boolean = false,
        val testTag: String = "electron_checkbox"
    ) : CheckboxUiModel()
}
