package com.electron.designsystem.components.topbar.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.badge.models.BadgeUiModel

/** Leading navigation affordance. The design system picks the icon. */
public enum class TopBarNavigation { None, Back, Close }

/** Optional trailing icon action, with an optional notification badge. */
@Immutable
public data class TopBarAction(
    val icon: ImageVector,
    val contentDescription: String?,
    val badge: BadgeUiModel? = null
)

/**
 * Top bar UI models. Refactor note: navigation is an enum instead of a
 * free icon slot so every screen uses the same back and close glyphs.
 */
public sealed class TopBarUiModel {

    /** Compact bar: title (and subtitle) inline with navigation and action. */
    @Immutable
    public data class Default(
        val title: String,
        val subtitle: String? = null,
        val navigation: TopBarNavigation = TopBarNavigation.None,
        val navigationContentDescription: String? = null,
        val action: TopBarAction? = null,
        val testTag: String = "electron_top_bar"
    ) : TopBarUiModel()

    /** Large bar: navigation row on top, headline title below. */
    @Immutable
    public data class Large(
        val title: String,
        val navigation: TopBarNavigation = TopBarNavigation.None,
        val navigationContentDescription: String? = null,
        val action: TopBarAction? = null,
        val testTag: String = "electron_top_bar_large"
    ) : TopBarUiModel()
}
