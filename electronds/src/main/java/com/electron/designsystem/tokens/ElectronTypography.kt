package com.electron.designsystem.tokens

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * ElectronDS font families.
 *
 * Intended brand pairing:
 * - Display: Space Grotesk (geometric, technical character)
 * - Body: Inter (neutral, highly legible)
 * - Data: JetBrains Mono (numeric readouts, code, identifiers)
 *
 * The families resolve to platform fallbacks until the licensed font files
 * are added under res/font. Swapping them in here re-skins the entire
 * system without touching any component.
 */
public object ElectronFontFamilies {
    public val display: FontFamily = FontFamily.SansSerif
    public val body: FontFamily = FontFamily.SansSerif
    public val data: FontFamily = FontFamily.Monospace
}

/**
 * ElectronDS type scale.
 *
 * Role-based, Material 3 aligned. The signature Electron addition is the
 * data family: monospaced styles for amounts, counters and identifiers,
 * so numbers align vertically in dense financial or telemetry layouts.
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
        lineHeight = 34.sp
    )
    public val headlineSmall: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.display,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    )

    // Titles
    public val titleLarge: TextStyle = TextStyle(
        fontFamily = ElectronFontFamilies.body,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
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
        fontWeight = FontWeight.Medium,
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
        lineHeight = 14.sp,
        letterSpacing = 0.5.sp
    )

    // Data: the Electron signature styles for numeric content
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
