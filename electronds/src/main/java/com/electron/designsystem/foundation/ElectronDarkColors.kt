package com.electron.designsystem.foundation

import androidx.compose.material3.darkColorScheme
import com.electron.designsystem.tokens.ElectronPalette

/** Dark mapping of the Electron palette onto semantic roles. */
public val ElectronDarkColors: ElectronColors = ElectronColors(
    isDark = true,
    brand = ElectronColors.Brand(
        primary = ElectronPalette.volt400,
        primaryStrong = ElectronPalette.volt300,
        primarySubtle = ElectronPalette.volt900,
        onPrimary = ElectronPalette.graphite950,
        accent = ElectronPalette.ion400,
        accentSubtle = ElectronPalette.ion700,
        onAccent = ElectronPalette.graphite950
    ),
    background = ElectronColors.Background(
        canvas = ElectronPalette.graphite950,
        surface = ElectronPalette.graphite900,
        surfaceRaised = ElectronPalette.graphite800,
        surfaceSunken = ElectronPalette.graphite950,
        scrim = ElectronPalette.blackAlpha40
    ),
    content = ElectronColors.Content(
        primary = ElectronPalette.graphite050,
        secondary = ElectronPalette.graphite300,
        muted = ElectronPalette.graphite400,
        disabled = ElectronPalette.graphite600,
        onBrand = ElectronPalette.graphite950,
        inverse = ElectronPalette.graphite900,
        link = ElectronPalette.volt300
    ),
    border = ElectronColors.Border(
        subtle = ElectronPalette.graphite800,
        default = ElectronPalette.graphite700,
        strong = ElectronPalette.graphite500,
        focus = ElectronPalette.volt400
    ),
    status = ElectronColors.Status(
        success = ElectronPalette.green500,
        successSubtle = ElectronPalette.green900,
        onSuccess = ElectronPalette.graphite950,
        warning = ElectronPalette.amber500,
        warningSubtle = ElectronPalette.graphite800,
        onWarning = ElectronPalette.graphite950,
        error = ElectronPalette.red500,
        errorSubtle = ElectronPalette.graphite800,
        onError = ElectronPalette.graphite950,
        info = ElectronPalette.blue500,
        infoSubtle = ElectronPalette.graphite800,
        onInfo = ElectronPalette.graphite950
    ),
    interaction = ElectronColors.Interaction(
        hover = ElectronPalette.whiteAlpha10,
        pressed = ElectronPalette.whiteAlpha16,
        selected = ElectronPalette.volt900,
        disabledBackground = ElectronPalette.graphite800,
        disabledContent = ElectronPalette.graphite600
    )
)

/** Material 3 bridge for M3 components used inside primitives. */
internal val electronDarkMaterialScheme = darkColorScheme(
    primary = ElectronPalette.volt400,
    onPrimary = ElectronPalette.graphite950,
    primaryContainer = ElectronPalette.volt900,
    onPrimaryContainer = ElectronPalette.volt100,
    secondary = ElectronPalette.ion400,
    onSecondary = ElectronPalette.graphite950,
    background = ElectronPalette.graphite950,
    onBackground = ElectronPalette.graphite050,
    surface = ElectronPalette.graphite900,
    onSurface = ElectronPalette.graphite050,
    surfaceVariant = ElectronPalette.graphite800,
    onSurfaceVariant = ElectronPalette.graphite300,
    outline = ElectronPalette.graphite600,
    error = ElectronPalette.red500,
    onError = ElectronPalette.graphite950
)
