package com.electron.designsystem.foundation

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.electron.designsystem.tokens.ElectronPalette

/**
 * Light mapping of the Electron palette onto semantic roles.
 *
 * Contrast targets (WCAG 2.1): every content role used for text reaches
 * 4.5:1 on both `canvas` and `surface`, including `content.muted`. Every
 * status and accent color also reaches 4.5:1 on its own subtle container,
 * because tinted tags render them as 12sp text on that container.
 */
public val ElectronLightColors: ElectronColors = ElectronColors(
    isDark = false,
    brand = ElectronColors.Brand(
        primary = ElectronPalette.volt600,
        primaryStrong = ElectronPalette.volt700,
        primarySubtle = ElectronPalette.volt100,
        onPrimary = ElectronPalette.graphite000,
        accent = ElectronPalette.ion800,
        accentSubtle = ElectronPalette.ion100,
        onAccent = ElectronPalette.graphite000
    ),
    background = ElectronColors.Background(
        canvas = ElectronPalette.graphite050,
        surface = ElectronPalette.graphite000,
        surfaceRaised = ElectronPalette.graphite000,
        surfaceSunken = ElectronPalette.graphite100,
        scrim = ElectronPalette.blackAlpha40
    ),
    content = ElectronColors.Content(
        primary = ElectronPalette.graphite900,
        secondary = ElectronPalette.graphite600,
        muted = ElectronPalette.graphite550,
        disabled = ElectronPalette.graphite400,
        onBrand = ElectronPalette.graphite000,
        inverse = ElectronPalette.graphite000,
        link = ElectronPalette.volt600
    ),
    border = ElectronColors.Border(
        subtle = ElectronPalette.graphite150,
        default = ElectronPalette.graphite200,
        strong = ElectronPalette.graphite400,
        focus = ElectronPalette.volt500
    ),
    status = ElectronColors.Status(
        success = ElectronPalette.green700,
        successSubtle = ElectronPalette.green100,
        onSuccess = ElectronPalette.graphite000,
        warning = ElectronPalette.amber700,
        warningSubtle = ElectronPalette.amber100,
        onWarning = ElectronPalette.graphite000,
        error = ElectronPalette.red600,
        errorSubtle = ElectronPalette.red100,
        onError = ElectronPalette.graphite000,
        info = ElectronPalette.blue700,
        infoSubtle = ElectronPalette.blue100,
        onInfo = ElectronPalette.graphite000
    ),
    interaction = ElectronColors.Interaction(
        hover = ElectronPalette.voltAlpha08,
        pressed = ElectronPalette.voltAlpha12,
        selected = ElectronPalette.volt050,
        disabledBackground = ElectronPalette.graphite100,
        disabledContent = ElectronPalette.graphite400
    )
)

/**
 * Material 3 bridge for M3 components used inside primitives.
 *
 * Every role is mapped explicitly, including the surface container scale
 * and `surfaceTint`: left at their defaults they paint menus, sheets and
 * tonal elevation in Material's baseline lavender, which is the fastest way
 * for an app to look like "Compose by default".
 */
internal val electronLightMaterialScheme = lightColorScheme(
    primary = ElectronPalette.volt600,
    onPrimary = ElectronPalette.graphite000,
    primaryContainer = ElectronPalette.volt050,
    onPrimaryContainer = ElectronPalette.volt800,
    inversePrimary = ElectronPalette.volt300,
    secondary = ElectronPalette.ion700,
    onSecondary = ElectronPalette.graphite000,
    secondaryContainer = ElectronPalette.volt050,
    onSecondaryContainer = ElectronPalette.volt800,
    tertiary = ElectronPalette.ion700,
    onTertiary = ElectronPalette.graphite000,
    tertiaryContainer = ElectronPalette.ion100,
    onTertiaryContainer = ElectronPalette.ion700,
    background = ElectronPalette.graphite050,
    onBackground = ElectronPalette.graphite900,
    surface = ElectronPalette.graphite000,
    onSurface = ElectronPalette.graphite900,
    surfaceVariant = ElectronPalette.graphite100,
    onSurfaceVariant = ElectronPalette.graphite600,
    surfaceTint = ElectronPalette.graphite000,
    surfaceBright = ElectronPalette.graphite000,
    surfaceDim = ElectronPalette.graphite100,
    surfaceContainerLowest = ElectronPalette.graphite000,
    surfaceContainerLow = ElectronPalette.graphite000,
    surfaceContainer = ElectronPalette.graphite000,
    surfaceContainerHigh = ElectronPalette.graphite000,
    surfaceContainerHighest = ElectronPalette.graphite100,
    inverseSurface = ElectronPalette.graphite900,
    inverseOnSurface = ElectronPalette.graphite050,
    outline = ElectronPalette.graphite200,
    outlineVariant = ElectronPalette.graphite150,
    error = ElectronPalette.red600,
    onError = ElectronPalette.graphite000,
    errorContainer = ElectronPalette.red100,
    onErrorContainer = ElectronPalette.red600,
    scrim = Color.Black
)
