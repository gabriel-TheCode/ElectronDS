package com.electron.designsystem.components.radiobutton.models

import androidx.compose.runtime.Immutable

/**
 * Radio button UI model. Selection within a group is data owned by the
 * screen; the component only reports that it was clicked.
 */
public sealed class RadioButtonUiModel {

    @Immutable
    public data class Default(
        val isSelected: Boolean,
        val label: String? = null,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_radio_button"
    ) : RadioButtonUiModel()
}
