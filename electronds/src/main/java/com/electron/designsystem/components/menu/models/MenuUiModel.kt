package com.electron.designsystem.components.menu.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * One menu entry. [isSelected] shows a trailing check (single choice
 * lists); [isDestructive] renders the entry in the error color and should
 * be the last entry of the menu.
 */
@Immutable
public data class MenuItem(
    val label: String,
    val leadingIcon: ImageVector? = null,
    val trailingText: String? = null,
    val isSelected: Boolean = false,
    val isDestructive: Boolean = false,
    val isEnabled: Boolean = true
)

/**
 * Menu UI model. [isExpanded] is data owned by the screen (like
 * `isChecked`): keeping the menu composed and flipping the flag lets it
 * animate out instead of disappearing.
 */
public sealed class MenuUiModel {

    @Immutable
    public data class Default(
        val items: List<MenuItem>,
        val isExpanded: Boolean,
        val testTag: String = "electron_menu"
    ) : MenuUiModel()
}
