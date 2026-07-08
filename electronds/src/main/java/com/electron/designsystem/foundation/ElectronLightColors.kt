package com.electron.designsystem.foundation

import androidx.compose.material3.lightColorScheme
import com.electron.designsystem.tokens.ElectronPalette

/** Light mapping of the Electron palette onto semantic roles. */
public val ElectronLightColors: ElectronColors = ElectronColors(
    isDark = false,
    brand = ElectronColors.Brand(
        primary = ElectronPalette.volt600,
        primaryStrong = ElectronPalette.volt700,
        primarySubtle = ElectronPalette.volt050,
        onPrimary = ElectronPalette.graphite000,
        accent = ElectronPalette.ion600,
        accentSubtle = ElectronPalette.ion100,
        onAccent = ElectronPalette.graphite950
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
        muted = ElectronPalette.graphite500,
        disabled = ElectronPalette.graphite400,
        onBrand = ElectronPalette.graphite000,
        inverse = ElectronPalette.graphite000,
        link = ElectronPalette.volt600
    ),
    border = ElectronColors.Border(
        subtle = ElectronPalette.graphite100,
        default = ElectronPalette.graphite200,
        strong = ElectronPalette.graphite400,
        focus = ElectronPalette.volt500
    ),
    status = ElectronColors.Status(
        success = ElectronPalette.green600,
        successSubtle = ElectronPalette.green100,
        onSuccess = ElectronPalette.graphite000,
        warning = ElectronPalette.amber600,
        warningSubtle = ElectronPalette.amber100,
        onWarning = ElectronPalette.graphite000,
        error = ElectronPalette.red600,
        errorSubtle = ElectronPalette.red100,
        onError = ElectronPalette.graphite000,
        info = ElectronPalette.blue600,
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

/** Material 3 bridge for M3 components used inside primitives. */
internal val electronLightMaterialScheme = lightColorScheme(
    primary = ElectronPalette.volt600,
    onPrimary = ElectronPalette.graphite000,
    primaryContainer = ElectronPalette.volt050,
    onPrimaryContainer = ElectronPalette.volt800,
    secondary = ElectronPalette.ion600,
    onSecondary = ElectronPalette.graphite950,
    background = ElectronPalette.graphite050,
    onBackground = ElectronPalette.graphite900,
    surface = ElectronPalette.graphite000,
    onSurface = ElectronPalette.graphite900,
    surfaceVariant = ElectronPalette.graphite100,
    onSurfaceVariant = ElectronPalette.graphite600,
    outline = ElectronPalette.graphite300,
    error = ElectronPalette.red600,
    onError = ElectronPalette.graphite000
)
