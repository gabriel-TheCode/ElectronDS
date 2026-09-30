package com.electron.designsystem.components.navigationbar.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.primitives.BottomNavigationPrimitive

@Composable
internal fun NavigationBottom(
    uiModel: NavigationBarUiModel.Bottom,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = navigationColors()
    BottomNavigationPrimitive(colors = colors, testTag = uiModel.testTag, modifier = modifier) { itemModifier ->
        NavigationItems(uiModel.items, uiModel.selectedIndex, colors, uiModel.testTag, itemModifier, onItemSelected)
    }
}
