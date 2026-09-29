package com.electron.designsystem.components.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarNavigation
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import com.electron.designsystem.components.topbar.variants.TopBarDefault
import com.electron.designsystem.components.topbar.variants.TopBarLarge
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronTopBar
 *
 * Purpose: screen header with an optional navigation affordance (back or
 * close) and an optional trailing action. Designed for the `topBar` slot
 * of ElectronScaffold.
 *
 * API:
 * - [uiModel]: bar variant (sealed [TopBarUiModel]).
 * - [onNavigationClick]: signal emitted when the back/close icon is tapped.
 *   Only relevant when `navigation` is not [TopBarNavigation.None].
 * - [onActionClick]: signal emitted when the trailing action is tapped.
 *   Only relevant when the UI model provides an `action`.
 *
 * Usage:
 * ```
 * ElectronTopBar(
 *     uiModel = TopBarUiModel.Default(title = "Charging", navigation = TopBarNavigation.Back),
 *     onNavigationClick = navigator::popBackStack
 * )
 * ```
 */
@Composable
public fun ElectronTopBar(
    uiModel: TopBarUiModel,
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    when (uiModel) {
        is TopBarUiModel.Default -> TopBarDefault(uiModel, onNavigationClick, onActionClick, modifier)
        is TopBarUiModel.Large -> TopBarLarge(uiModel, onNavigationClick, onActionClick, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronTopBarPreview() {
    ElectronPreviewSurface {
        ElectronTopBar(
            TopBarUiModel.Default(
                title = "Home charger",
                subtitle = "Connected",
                navigation = TopBarNavigation.Back,
                action = TopBarAction(Icons.Outlined.Settings, "Settings")
            )
        )
        ElectronTopBar(
            TopBarUiModel.Large(
                title = "Dashboard",
                action = TopBarAction(Icons.Outlined.Notifications, "Notifications", badge = BadgeUiModel.Dot())
            )
        )
    }
}
