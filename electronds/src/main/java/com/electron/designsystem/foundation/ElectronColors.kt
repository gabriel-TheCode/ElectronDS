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
 *
 * Every hue has two roles that must not be confused:
 * - The plain role (`brand.primary`, `status.success`, ...) is for text,
 *   icons, outlines and thin marks drawn on a background. In dark theme it
 *   is a light step so it reads on dark surfaces.
 * - The `*Fill` role is the solid container of a filled component (button,
 *   FAB, badge, filled tag, checked checkbox and switch). It is deep enough
 *   in both themes to carry white `on*` content at 4.5:1.
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
        val primaryFill: Color,
        val primaryStrong: Color,
        val primarySubtle: Color,
        val onPrimary: Color,
        val accent: Color,
        val accentFill: Color,
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
        val successFill: Color,
        val successSubtle: Color,
        val onSuccess: Color,
        val warning: Color,
        val warningFill: Color,
        val warningSubtle: Color,
        val onWarning: Color,
        val error: Color,
        val errorFill: Color,
        val errorSubtle: Color,
        val onError: Color,
        val info: Color,
        val infoFill: Color,
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
