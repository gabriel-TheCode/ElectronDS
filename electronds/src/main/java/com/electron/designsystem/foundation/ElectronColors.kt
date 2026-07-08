package com.electron.designsystem.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Semantic color roles of ElectronDS.
 *
 * Deliberately flat (two levels maximum) so call sites stay readable:
 * `ElectronTheme.colors.content.primary`, `ElectronTheme.colors.status.error`.
 *
 * This is a lesson learned from deeply nested schemes in legacy systems,
 * where paths such as `colors.basic.bg.main.fixed.white` made every usage
 * hard to read and hard to review.
 *
 * All classes are [Immutable] so Compose can skip recompositions when a
 * stable theme instance is passed around.
 */
@Immutable
public data class ElectronColors(
    val brand: Brand,
    val background: Background,
    val content: Content,
    val border: Border,
    val status: Status,
    val interaction: Interaction,
    val isDark: Boolean
) {

    @Immutable
    public data class Brand(
        val primary: Color,
        val primaryStrong: Color,
        val primarySubtle: Color,
        val onPrimary: Color,
        val accent: Color,
        val accentSubtle: Color,
        val onAccent: Color
    )

    @Immutable
    public data class Background(
        val canvas: Color,
        val surface: Color,
        val surfaceRaised: Color,
        val surfaceSunken: Color,
        val scrim: Color
    )

    @Immutable
    public data class Content(
        val primary: Color,
        val secondary: Color,
        val muted: Color,
        val disabled: Color,
        val onBrand: Color,
        val inverse: Color,
        val link: Color
    )

    @Immutable
    public data class Border(
        val subtle: Color,
        val default: Color,
        val strong: Color,
        val focus: Color
    )

    @Immutable
    public data class Status(
        val success: Color,
        val successSubtle: Color,
        val onSuccess: Color,
        val warning: Color,
        val warningSubtle: Color,
        val onWarning: Color,
        val error: Color,
        val errorSubtle: Color,
        val onError: Color,
        val info: Color,
        val infoSubtle: Color,
        val onInfo: Color
    )

    @Immutable
    public data class Interaction(
        val hover: Color,
        val pressed: Color,
        val selected: Color,
        val disabledBackground: Color,
        val disabledContent: Color
    )
}
