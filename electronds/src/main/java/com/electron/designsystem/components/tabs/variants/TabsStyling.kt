package com.electron.designsystem.components.tabs.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.badge.ElectronBadge
import com.electron.designsystem.components.tabs.models.TabItem
import com.electron.designsystem.components.tabs.primitives.TabColors
import com.electron.designsystem.components.tabs.primitives.TabsPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/**
 * The indicator is brand (it is the only signifier of position in a
 * navigation row); labels change from secondary to primary so the chosen
 * tab also reads without color.
 */
@Composable
internal fun tabColors(): TabColors {
    val c = ElectronTheme.colors
    return TabColors(
        indicator = c.brand.primary,
        divider = c.border.subtle,
        selectedContent = c.content.primary,
        content = c.content.secondary
    )
}

@Composable
internal fun TabsRow(
    tabs: List<TabItem>,
    selectedIndex: Int,
    isScrollable: Boolean,
    testTag: String,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier
) {
    TabsPrimitive(
        labels = tabs.map { it.label },
        selectedIndex = selectedIndex,
        isScrollable = isScrollable,
        colors = tabColors(),
        textStyle = ElectronTheme.typography.labelLarge,
        testTag = testTag,
        onTabSelected = onTabSelected,
        modifier = modifier,
        trailing = { index -> tabs.getOrNull(index)?.badge?.let { ElectronBadge(it) } }
    )
}
