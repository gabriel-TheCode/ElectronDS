package com.electron.designsystem.components.badge.models

import androidx.compose.runtime.Immutable

/** Semantic tone: the design system maps it to colors per theme. */
public enum class BadgeTone { Brand, Accent, Neutral, Success, Error }

/**
 * Badge UI models: small notification markers attached to icons, list
 * rows or tabs. Purely descriptive, no interaction.
 */
public sealed class BadgeUiModel {

    /** Presence marker without a number ("something new"). */
    @Immutable
    public data class Dot(
        val tone: BadgeTone = BadgeTone.Error,
        val contentDescription: String? = null,
        val testTag: String = "electron_badge_dot"
    ) : BadgeUiModel()

    /** Counter; values above [max] render as "max+". */
    @Immutable
    public data class Count(
        val count: Int,
        val max: Int = 99,
        val tone: BadgeTone = BadgeTone.Error,
        val contentDescription: String? = null,
        val testTag: String = "electron_badge_count"
    ) : BadgeUiModel()
}
