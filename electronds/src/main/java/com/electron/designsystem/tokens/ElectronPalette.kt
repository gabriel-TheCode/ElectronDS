package com.electron.designsystem.tokens

import androidx.compose.ui.graphics.Color

/**
 * ElectronDS raw color palette.
 *
 * These are the primitive brand values. They are internal on purpose:
 * components and features never consume raw palette entries, they consume
 * the semantic roles exposed by [com.electron.designsystem.foundation.ElectronColors].
 *
 * Brand story:
 * - Volt: the primary electric indigo. Charged, precise, unmistakably digital.
 * - Ion: the cyan accent. Used sparingly for live states and highlights.
 * - Graphite: a cool, blue-cast neutral scale, evoking anodized hardware.
 * - Status hues: calibrated for AA contrast on both light and dark canvases.
 */
internal object ElectronPalette {

    // Volt: primary scale
    val volt050 = Color(0xFFEEF0FF)
    val volt100 = Color(0xFFDCE0FF)
    val volt200 = Color(0xFFB9C2FF)
    val volt300 = Color(0xFF8E9BFF)
    val volt400 = Color(0xFF6577FF)
    val volt500 = Color(0xFF4C5BF5)
    val volt600 = Color(0xFF3B48D9)
    val volt700 = Color(0xFF2E38AD)
    val volt800 = Color(0xFF232A82)
    val volt900 = Color(0xFF191E5C)

    // Ion: accent scale
    val ion100 = Color(0xFFD6FAF5)
    val ion300 = Color(0xFF5DEBDB)
    val ion400 = Color(0xFF2BE0CE)
    val ion500 = Color(0xFF10C7B4)
    val ion600 = Color(0xFF0AA394)
    val ion700 = Color(0xFF0A7F75)

    // Graphite: cool neutral scale
    val graphite000 = Color(0xFFFFFFFF)
    val graphite025 = Color(0xFFFAFBFE)
    val graphite050 = Color(0xFFF4F6FA)
    val graphite100 = Color(0xFFE9EDF4)
    val graphite200 = Color(0xFFD8DEE9)
    val graphite300 = Color(0xFFB9C1D1)
    val graphite400 = Color(0xFF97A0B4)
    val graphite500 = Color(0xFF6E7891)
    val graphite600 = Color(0xFF525C73)
    val graphite700 = Color(0xFF3B4358)
    val graphite800 = Color(0xFF272E40)
    val graphite900 = Color(0xFF161B29)
    val graphite950 = Color(0xFF0B0F1A)

    // Status hues
    val green500 = Color(0xFF17A56A)
    val green600 = Color(0xFF108552)
    val green100 = Color(0xFFE3F7EE)
    val green900 = Color(0xFF0E3325)

    val amber500 = Color(0xFFE8930C)
    val amber600 = Color(0xFFB87104)
    val amber100 = Color(0xFFFDF1DC)

    val red500 = Color(0xFFE5484D)
    val red600 = Color(0xFFC53438)
    val red100 = Color(0xFFFDEBEC)

    val blue500 = Color(0xFF2F81F7)
    val blue600 = Color(0xFF1F6CD6)
    val blue100 = Color(0xFFE7F0FE)

    // Alpha layers for interaction states
    val voltAlpha08 = Color(0x143B48D9)
    val voltAlpha12 = Color(0x1F3B48D9)
    val whiteAlpha10 = Color(0x1AFFFFFF)
    val whiteAlpha16 = Color(0x29FFFFFF)
    val blackAlpha40 = Color(0x66000000)
    val blackAlpha06 = Color(0x0F000000)
}
