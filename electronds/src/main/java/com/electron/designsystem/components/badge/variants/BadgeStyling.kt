package com.electron.designsystem.components.badge.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.electron.designsystem.components.badge.models.BadgeTone
import com.electron.designsystem.foundation.ElectronTheme

internal data class BadgeColorsResolved(val background: Color, val content: Color)

@Composable
internal fun BadgeTone.resolve(): BadgeColorsResolved {
    val c = ElectronTheme.colors
    return when (this) {
        BadgeTone.Brand -> BadgeColorsResolved(c.brand.primaryFill, c.brand.onPrimary)
        BadgeTone.Accent -> BadgeColorsResolved(c.brand.accentFill, c.brand.onAccent)
        BadgeTone.Neutral -> BadgeColorsResolved(c.content.secondary, c.content.inverse)
        BadgeTone.Success -> BadgeColorsResolved(c.status.successFill, c.status.onSuccess)
        BadgeTone.Error -> BadgeColorsResolved(c.status.errorFill, c.status.onError)
    }
}

/** Caps large counters so the badge never grows past a fixed width. */
internal fun formatBadgeCount(count: Int, max: Int): String =
    if (count > max) "$max+" else count.coerceAtLeast(0).toString()
