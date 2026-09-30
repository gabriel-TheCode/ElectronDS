package com.electron.designsystem.components.tabs.models

import androidx.compose.runtime.Immutable
import com.electron.designsystem.components.badge.models.BadgeUiModel

/** One tab: a label and an optional notification badge. */
@Immutable
public data class TabItem(
    val label: String,
    val badge: BadgeUiModel? = null
)

/**
 * Tabs UI models. Tabs switch between sibling views of the same screen
 * (Overview / History / Settings); to filter or pick a value inside one
 * view, use ElectronSegmentedControl instead.
 */
public sealed class TabsUiModel {

    /** 2 to 4 tabs sharing the width equally. */
    @Immutable
    public data class Fixed(
        val tabs: List<TabItem>,
        val selectedIndex: Int,
        val testTag: String = "electron_tabs_fixed"
    ) : TabsUiModel()

    /** Any number of tabs, sized to their labels, scrolling horizontally. */
    @Immutable
    public data class Scrollable(
        val tabs: List<TabItem>,
        val selectedIndex: Int,
        val testTag: String = "electron_tabs_scrollable"
    ) : TabsUiModel()
}
