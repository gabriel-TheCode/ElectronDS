package com.electron.designsystem.components.navigationbar.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.primitives.RailNavigationPrimitive

@Composable
internal fun NavigationRail(
    uiModel: NavigationBarUiModel.Rail,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = navigationColors()
    RailNavigationPrimitive(colors = colors, testTag = uiModel.testTag, modifier = modifier) { itemModifier ->
        NavigationItems(uiModel.items, uiModel.selectedIndex, colors, uiModel.testTag, itemModifier, onItemSelected)
    }
}
