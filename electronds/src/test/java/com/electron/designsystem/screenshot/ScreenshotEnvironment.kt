package com.electron.designsystem.screenshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Tolerance for pixel differences between the recorded golden and the new
 * rendering. Low on purpose: a design system regression is often a 1dp
 * padding or a color step, which only moves a few pixels.
 */
internal const val MAX_PERCENT_DIFFERENCE = 0.1

/** Every snapshot is rendered in both themes: dark is a remapping, not an afterthought. */
internal enum class ThemeVariant(val isDark: Boolean) {
    Light(isDark = false),
    Dark(isDark = true)
}

/**
 * Mobile form factors the system must hold on. Tablet checks the adaptive
 * decisions (navigation rail, readable widths, capped sheets and dialogs).
 */
internal enum class DeviceVariant(val config: DeviceConfig, val isCompact: Boolean) {
    Phone(DeviceConfig.PIXEL_5, isCompact = true),
    Tablet(DeviceConfig.PIXEL_C, isCompact = false)
}

/**
 * Snapshots [content] inside ElectronTheme, on the canvas color, with the
 * screen gutter. Sections get a small caption so a gallery image can be
 * read without the test source next to it.
 */
internal fun Paparazzi.electronGallery(
    theme: ThemeVariant,
    content: @Composable ColumnScope.() -> Unit
) {
    snapshot {
        ElectronTheme(darkTheme = theme.isDark) {
            Column(
                verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ElectronTheme.colors.background.canvas)
                    .padding(ElectronSpacing.lg),
                content = content
            )
        }
    }
}

/** Full-screen snapshot inside ElectronTheme (adaptive layout tests). */
internal fun Paparazzi.electronScreen(theme: ThemeVariant, content: @Composable () -> Unit) {
    snapshot {
        ElectronTheme(darkTheme = theme.isDark) { content() }
    }
}

@Composable
internal fun Caption(text: String) {
    Text(
        text = text.uppercase(),
        style = ElectronTheme.typography.labelSmall,
        color = ElectronTheme.colors.content.muted,
        modifier = Modifier.padding(top = ElectronSpacing.sm)
    )
}
