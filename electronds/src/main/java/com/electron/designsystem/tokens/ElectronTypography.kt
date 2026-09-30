package com.electron.designsystem.tokens

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.electron.designsystem.R

/**
 * ElectronDS font families.
 *
 * The typeface is a design token owned by the system, like the colors:
 * apps inherit it by wrapping their UI in ElectronTheme, and every
 * component picks it up without any app code.
 *
 * - Display and body: Manrope (geometric, technical, very legible at small
 *   sizes), bundled in four static weights (400, 500, 600, 700) under the
 *   SIL Open Font License (see FONT_LICENSE_Manrope.txt).
 * - Data: monospace, so digits keep a fixed width and align in dense
 *   numeric layouts (Manrope's figures are proportional).
 */
public object ElectronFontFamilies {
    private val manrope: FontFamily = FontFamily(
        Font(R.font.manrope_regular, FontWeight.Normal),
        Font(R.font.manrope_medium, FontWeight.Medium),
        Font(R.font.manrope_semibold, FontWeight.SemiBold),
        Font(R.font.manrope_bold, FontWeight.Bold)
    )

    public val display: FontFamily = manrope
    public val body: FontFamily = manrope
    public val data: FontFamily = FontFamily.Monospace
}

/**
 * ElectronDS type scale.
 *
 * Role-based, Material 3 aligned. The signature Electron addition is the
 * data family: monospaced styles for amounts, counters and identifiers,
 * so numbers align vertically in dense financial or telemetry layouts.
 *
 * Role rules (so two components never pick different styles for the same job):
 * - Screen and dialog titles: headline. Section and card titles: title.
 * - User content and row text (list titles, field values, control labels): bodyLarge.
 * - Supporting text (subtitles, messages, helper text): bodyMedium / bodySmall.
 * - Actions (buttons, chips, segments, field labels): labelLarge, SemiBold so an
 *   action always outweighs the content around it.
 * - Metadata (tags, badges, captions): labelMedium / labelSmall.
 * - Numbers that matter: data. `dataDisplay` is the hero figure of a screen.
 *
 * Large sizes carry slightly negative tracking: display type set at default
 * tracking looks loose and unconsidered.
 */
public object ElectronTypography {

    // Display
    public val displayLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.display,
        fontWeight = FontWeight.SemiBold,
        fontSize = 44.sp,
        lineHeight = 52.sp,
        letterSpacing = (-0.5).sp
    )
    public val displaySmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.display,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.25).sp
    )

    // Headlines
    public val headlineLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.display,
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.25).sp
    )
    public val headlineSmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.display,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.15).sp
    )

    // Titles
    public val titleLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.1).sp
    )
    public val titleMedium: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )
    public val titleSmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

    // Body
    public val bodyLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )
    public val bodyMedium: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
    public val bodySmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )

    // Labels
    public val labelLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    public val labelMedium: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp
    )
    public val labelSmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

    // Data: the Electron signature styles for numeric content
    public val dataDisplay: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.data,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = (-0.5).sp
    )
    public val dataLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.data,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp
    )
    public val dataMedium: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.data,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
    public val dataSmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.data,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )

    /** Bridge to Material 3 components that read MaterialTheme.typography. */
    internal fun toMaterial3Typography(): Typography = Typography(
        displayLarge = displayLarge,
        displaySmall = displaySmall,
        headlineLarge = headlineLarge,
        headlineSmall = headlineSmall,
        titleLarge = titleLarge,
        titleMedium = titleMedium,
        titleSmall = titleSmall,
        bodyLarge = bodyLarge,
        bodyMedium = bodyMedium,
        bodySmall = bodySmall,
        labelLarge = labelLarge,
        labelMedium = labelMedium,
        labelSmall = labelSmall
    )
}
