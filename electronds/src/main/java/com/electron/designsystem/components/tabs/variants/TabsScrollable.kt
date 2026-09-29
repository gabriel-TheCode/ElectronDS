package com.electron.designsystem.components.tabs.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.tabs.models.TabsUiModel

@Composable
internal fun TabsScrollable(
    uiModel: TabsUiModel.Scrollable,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    TabsRow(uiModel.tabs, uiModel.selectedIndex, isScrollable = true, uiModel.testTag, onTabSelected, modifier)
}
