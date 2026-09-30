package com.electron.designsystem.components.tabs.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.tabs.models.TabsUiModel

@Composable
internal fun TabsFixed(
    uiModel: TabsUiModel.Fixed,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabsRow(uiModel.tabs, uiModel.selectedIndex, isScrollable = false, uiModel.testTag, onTabSelected, modifier)
}
