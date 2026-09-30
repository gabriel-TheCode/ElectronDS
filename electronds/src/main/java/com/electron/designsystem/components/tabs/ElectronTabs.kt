package com.electron.designsystem.components.tabs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.tabs.models.TabItem
import com.electron.designsystem.components.tabs.models.TabsUiModel
import com.electron.designsystem.components.tabs.variants.TabsFixed
import com.electron.designsystem.components.tabs.variants.TabsScrollable
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronTabs
 *
 * Purpose: switch between sibling views of a screen. The screen owns the
 * selected index and reacts to [onTabSelected], which carries the tapped
 * index. Tabs can carry an ElectronBadge.
 *
 * Usage:
 * ```
 * ElectronTabs(
 *     uiModel = TabsUiModel.Fixed(
 *         tabs = listOf(TabItem("Overview"), TabItem("Sessions"), TabItem("Alerts", BadgeUiModel.Count(2))),
 *         selectedIndex = uiState.tabIndex
 *     ),
 *     onTabSelected = viewModel::onTabSelected
 * )
 * ```
 */
@Composable
public fun ElectronTabs(
    uiModel: TabsUiModel,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is TabsUiModel.Fixed -> TabsFixed(uiModel, onTabSelected, modifier)
        is TabsUiModel.Scrollable -> TabsScrollable(uiModel, onTabSelected, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronTabsPreview() {
    ElectronPreviewSurface {
        ElectronTabs(
            TabsUiModel.Fixed(
                tabs = listOf(TabItem("Overview"), TabItem("Sessions"), TabItem("Alerts", BadgeUiModel.Count(2))),
                selectedIndex = 0
            ),
            onTabSelected = {}
        )
        ElectronTabs(
            TabsUiModel.Scrollable(
                tabs = listOf("All", "Home", "Work", "Public stations", "Favorites", "Recent").map { TabItem(it) },
                selectedIndex = 3
            ),
            onTabSelected = {}
        )
    }
}
