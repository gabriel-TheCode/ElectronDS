package com.electron.designsystem.components.navigationbar

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.models.NavigationItem
import com.electron.designsystem.components.navigationbar.variants.NavigationBottom
import com.electron.designsystem.components.navigationbar.variants.NavigationRail
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronNavigationBar
 *
 * Purpose: top-level navigation between 3 to 5 destinations, as a bottom
 * bar ([NavigationBarUiModel.Bottom]) on phones or a rail
 * ([NavigationBarUiModel.Rail]) on tablets. The screen owns the
 * selected index and reacts to [onItemSelected], which carries the tapped
 * index. Destinations can carry an ElectronBadge.
 *
 * Usage:
 * ```
 * ElectronNavigationBar(
 *     uiModel = if (isCompactWidth) NavigationBarUiModel.Bottom(items, selected) else NavigationBarUiModel.Rail(items, selected),
 *     onItemSelected = viewModel::onDestinationSelected
 * )
 * ```
 */
@Composable
public fun ElectronNavigationBar(
    uiModel: NavigationBarUiModel,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is NavigationBarUiModel.Bottom -> NavigationBottom(uiModel, onItemSelected, modifier)
        is NavigationBarUiModel.Rail -> NavigationRail(uiModel, onItemSelected, modifier)
    }
}

private val previewItems = listOf(
    NavigationItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
    NavigationItem("Charging", Icons.Outlined.Bolt, Icons.Filled.Bolt, badge = BadgeUiModel.Dot()),
    NavigationItem("Account", Icons.Outlined.Person, Icons.Filled.Person)
)

@Preview(showBackground = true)
@Composable
private fun ElectronNavigationBarPreview() {
    ElectronPreviewSurface {
        ElectronNavigationBar(NavigationBarUiModel.Bottom(previewItems, selectedIndex = 0), onItemSelected = {})
        ElectronNavigationBar(
            NavigationBarUiModel.Rail(previewItems, selectedIndex = 1),
            onItemSelected = {},
            modifier = Modifier.height(ElectronDimens.readableWidth)
        )
    }
}
