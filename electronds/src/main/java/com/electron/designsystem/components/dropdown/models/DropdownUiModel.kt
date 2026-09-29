package com.electron.designsystem.components.dropdown.models

import androidx.compose.runtime.Immutable

/**
 * Dropdown (select field) UI model: picks one value among [options].
 * [selectedIndex] and [isExpanded] are data owned by the screen.
 */
public sealed class DropdownUiModel {

    @Immutable
    public data class Default(
        val options: List<String>,
        val selectedIndex: Int? = null,
        val isExpanded: Boolean = false,
        val label: String? = null,
        val placeholder: String? = null,
        val helperText: String? = null,
        val errorText: String? = null,
        val isError: Boolean = false,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_dropdown"
    ) : DropdownUiModel()
}
