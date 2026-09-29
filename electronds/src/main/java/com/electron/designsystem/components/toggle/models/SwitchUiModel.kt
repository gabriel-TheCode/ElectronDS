package com.electron.designsystem.components.toggle.models

import androidx.compose.runtime.Immutable

/**
 * Switch UI model. Refactored from the legacy flat toggle button:
 * the component now follows the same UiModel plus callback contract as
 * every other Electron component.
 */
public sealed class SwitchUiModel {

    @Immutable
    public data class Default(
        val isChecked: Boolean,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_switch"
    ) : SwitchUiModel()
}
