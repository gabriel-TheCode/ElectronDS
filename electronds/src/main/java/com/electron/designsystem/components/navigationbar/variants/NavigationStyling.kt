package com.electron.designsystem.components.navigationbar.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.badge.ElectronBadge
import com.electron.designsystem.components.navigationbar.models.NavigationItem
import com.electron.designsystem.components.navigationbar.primitives.NavigationColors
import com.electron.designsystem.components.navigationbar.primitives.NavigationItemPrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun navigationColors(): NavigationColors {
    val c = ElectronTheme.colors
    return NavigationColors(
        container = c.background.surface,
        divider = c.border.subtle,
        indicator = c.brand.primarySubtle,
        selectedIcon = c.brand.primaryStrong,
        icon = c.content.secondary,
        selectedLabel = c.content.primary,
        label = c.content.secondary
    )
}

/** Renders every destination with the shared item primitive. */
@Composable
internal fun NavigationItems(
    items: List<NavigationItem>,
    selectedIndex: Int,
    colors: NavigationColors,
    testTag: String,
    itemModifier: Modifier,
    onItemSelected: (Int) -> Unit
) {
    items.forEachIndexed { index, item ->
        val isSelected = index == selectedIndex
        val badge = item.badge
        NavigationItemPrimitive(
            label = item.label,
            icon = if (isSelected) item.selectedIcon ?: item.icon else item.icon,
            isSelected = isSelected,
            colors = colors,
            labelStyle = ElectronTheme.typography.labelMedium,
            testTag = "${testTag}_$index",
            onClick = { onItemSelected(index) },
            modifier = itemModifier,
            badge = if (badge != null) {
                { ElectronBadge(badge) }
            } else {
                null
            }
        )
    }
}
