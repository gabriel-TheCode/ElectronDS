package com.electron.designsystem.components.menu.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.menu.models.MenuItem
import com.electron.designsystem.components.menu.models.MenuUiModel
import com.electron.designsystem.components.menu.primitives.MenuItemColors
import com.electron.designsystem.components.menu.primitives.MenuItemPrimitive
import com.electron.designsystem.components.menu.primitives.MenuPanel
import com.electron.designsystem.components.menu.primitives.MenuPopup
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun menuItemColors(item: MenuItem): MenuItemColors {
    val c = ElectronTheme.colors
    return when {
        !item.isEnabled -> MenuItemColors(c.content.disabled, c.content.disabled, c.content.disabled, c.content.disabled)
        item.isDestructive -> MenuItemColors(c.status.error, c.status.error, c.status.error, c.status.error)
        else -> MenuItemColors(c.content.primary, c.content.secondary, c.content.muted, c.brand.primary)
    }
}

/** Menu panel content, shared by the popup and by screenshot tests. */
@Composable
internal fun MenuDefaultPanel(
    uiModel: MenuUiModel.Default,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val c = ElectronTheme.colors
    MenuPanel(
        containerColor = c.background.surfaceRaised,
        borderColor = c.border.subtle,
        testTag = uiModel.testTag,
        modifier = modifier
    ) {
        uiModel.items.forEachIndexed { index, item ->
            MenuItemPrimitive(
                label = item.label,
                labelStyle = ElectronTheme.typography.bodyLarge,
                trailingStyle = ElectronTheme.typography.bodyMedium,
                colors = menuItemColors(item),
                isEnabled = item.isEnabled,
                isSelected = item.isSelected,
                leadingIcon = item.leadingIcon,
                trailingText = item.trailingText,
                testTag = "${uiModel.testTag}_$index",
                onClick = { onItemClick(index) }
            )
        }
    }
}

@Composable
internal fun MenuDefault(
    uiModel: MenuUiModel.Default,
    onItemClick: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    MenuPopup(isExpanded = uiModel.isExpanded, onDismissRequest = onDismissRequest) {
        MenuDefaultPanel(uiModel, onItemClick, modifier)
    }
}
