package com.electron.designsystem.components.topbar.variants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.badge.ElectronBadge
import com.electron.designsystem.components.icon.ElectronIcon
import com.electron.designsystem.components.icon.models.IconSize
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarNavigation
import com.electron.designsystem.components.topbar.primitives.TopBarIconButton

internal fun TopBarNavigation.icon(): ImageVector? = when (this) {
    TopBarNavigation.None -> null
    TopBarNavigation.Back -> Icons.AutoMirrored.Outlined.ArrowBack
    TopBarNavigation.Close -> Icons.Outlined.Close
}

/** Navigation slot content, or null when the bar has no navigation. */
internal fun navigationSlot(
    navigation: TopBarNavigation,
    contentDescription: String?,
    testTag: String,
    onNavigationClick: () -> Unit
): (@Composable () -> Unit)? {
    val icon = navigation.icon() ?: return null
    return {
        TopBarIconButton(
            onClick = onNavigationClick,
            testTag = "${testTag}_navigation",
            icon = {
                ElectronIcon(IconUiModel.Default(icon, contentDescription, size = IconSize.Lg))
            }
        )
    }
}

/** Action slot content, or null when the bar has no action. */
internal fun actionSlot(
    action: TopBarAction?,
    testTag: String,
    onActionClick: () -> Unit
): (@Composable () -> Unit)? {
    if (action == null) return null
    val badge = action.badge
    return {
        TopBarIconButton(
            onClick = onActionClick,
            testTag = "${testTag}_action",
            icon = {
                ElectronIcon(IconUiModel.Default(action.icon, action.contentDescription, size = IconSize.Lg))
            },
            badge = if (badge != null) {
                { ElectronBadge(badge) }
            } else {
                null
            }
        )
    }
}
