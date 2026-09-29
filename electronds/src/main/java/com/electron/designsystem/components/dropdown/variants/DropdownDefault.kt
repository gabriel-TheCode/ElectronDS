package com.electron.designsystem.components.dropdown.variants

import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.electron.designsystem.components.dropdown.models.DropdownUiModel
import com.electron.designsystem.components.dropdown.primitives.DropdownColors
import com.electron.designsystem.components.dropdown.primitives.DropdownFieldPrimitive
import com.electron.designsystem.components.menu.ElectronMenu
import com.electron.designsystem.components.menu.models.MenuItem
import com.electron.designsystem.components.menu.models.MenuUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens

@Composable
internal fun dropdownColors(isEnabled: Boolean): DropdownColors {
    val c = ElectronTheme.colors
    return DropdownColors(
        container = if (isEnabled) c.background.surface else c.interaction.disabledBackground,
        border = c.border.default,
        activeBorder = c.border.focus,
        errorBorder = c.status.error,
        label = if (isEnabled) c.content.secondary else c.content.disabled,
        value = if (isEnabled) c.content.primary else c.interaction.disabledContent,
        placeholder = c.content.muted,
        chevron = if (isEnabled) c.content.secondary else c.interaction.disabledContent,
        supporting = c.content.muted,
        error = c.status.error
    )
}

/** Field plus an ElectronMenu as wide as the field; the chosen option shows a check. */
@Composable
internal fun DropdownDefault(
    uiModel: DropdownUiModel.Default,
    onFieldClick: () -> Unit,
    onOptionSelected: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    var fieldWidth by remember { mutableStateOf(ElectronDimens.menuMinWidth) }
    val selectedIndex = uiModel.selectedIndex
    DropdownFieldPrimitive(
        valueText = selectedIndex?.let { uiModel.options.getOrNull(it) },
        label = uiModel.label,
        placeholder = uiModel.placeholder,
        supportingText = if (uiModel.isError) uiModel.errorText else uiModel.helperText,
        isError = uiModel.isError,
        isEnabled = uiModel.isEnabled,
        isExpanded = uiModel.isExpanded,
        colors = dropdownColors(uiModel.isEnabled),
        labelStyle = ElectronTheme.typography.labelLarge,
        valueStyle = ElectronTheme.typography.bodyLarge,
        supportingStyle = ElectronTheme.typography.bodySmall,
        testTag = uiModel.testTag,
        onClick = onFieldClick,
        modifier = modifier.onSizeChanged { fieldWidth = with(density) { it.width.toDp() } }
    ) {
        ElectronMenu(
            uiModel = MenuUiModel.Default(
                items = uiModel.options.mapIndexed { index, option ->
                    MenuItem(label = option, isSelected = index == selectedIndex)
                },
                isExpanded = uiModel.isExpanded,
                testTag = "${uiModel.testTag}_menu"
            ),
            onItemClick = onOptionSelected,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.width(fieldWidth.coerceAtLeast(ElectronDimens.menuMinWidth))
        )
    }
}
