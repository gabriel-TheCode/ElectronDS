package com.electron.designsystem.components.navigationbar.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.badge.models.BadgeUiModel

/**
 * One top-level destination. [selectedIcon] (usually the filled glyph) is
 * shown when selected, so the active destination differs by shape as well
 * as color.
 */
@Immutable
public data class NavigationItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val badge: BadgeUiModel? = null
)

/**
 * Top-level navigation. The same items render as a bottom bar on phones
 * and as a rail on tablets and TV; the screen picks the variant from its
 * window size class.
 */
public sealed class NavigationBarUiModel {

    @Immutable
    public data class Bottom(
        val items: List<NavigationItem>,
        val selectedIndex: Int,
        val testTag: String = "electron_navigation_bottom"
    ) : NavigationBarUiModel()

    @Immutable
    public data class Rail(
        val items: List<NavigationItem>,
        val selectedIndex: Int,
        val testTag: String = "electron_navigation_rail"
    ) : NavigationBarUiModel()
}
