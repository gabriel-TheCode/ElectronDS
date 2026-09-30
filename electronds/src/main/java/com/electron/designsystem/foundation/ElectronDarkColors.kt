package com.electron.designsystem.foundation

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color
import com.electron.designsystem.tokens.ElectronPalette

/**
 * Dark mapping of the Electron palette onto semantic roles.
 *
 * Dark is a remapping, not an inversion:
 * - Surfaces step up in lightness as they come forward (canvas 950,
 *   surface 900, raised 800). The sunken step (850) sits between canvas
 *   and surface so tracks and wells stay visible on both.
 * - Status colors move to their lighter 400/500 steps for text contrast,
 *   and every subtle container keeps its hue (900 step) instead of
 *   collapsing to the same grey.
 * - Fills stay deep (volt500, green700, red600, ...) with white content:
 *   the light text steps would need near-black text on a filled button,
 *   which reads as washed out and unexpected in a dark UI.
 */
public val ElectronDarkColors: ElectronColors = ElectronColors(
    isDark = true,
    brand = ElectronColors.Brand(
        primary = ElectronPalette.volt400,
        primaryFill = ElectronPalette.volt500,
        primaryStrong = ElectronPalette.volt300,
        primarySubtle = ElectronPalette.volt900,
        onPrimary = ElectronPalette.graphite000,
        accent = ElectronPalette.ion400,
        accentFill = ElectronPalette.ion800,
        accentSubtle = ElectronPalette.ion900,
        onAccent = ElectronPalette.graphite000
    ),
    background = ElectronColors.Background(
        canvas = ElectronPalette.graphite950,
        surface = ElectronPalette.graphite900,
        surfaceRaised = ElectronPalette.graphite800,
        surfaceSunken = ElectronPalette.graphite850,
        scrim = ElectronPalette.blackAlpha64
    ),
    content = ElectronColors.Content(
        primary = ElectronPalette.graphite050,
        secondary = ElectronPalette.graphite300,
        muted = ElectronPalette.graphite400,
        disabled = ElectronPalette.graphite500,
        onBrand = ElectronPalette.graphite000,
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
        success = ElectronPalette.green400,
        successFill = ElectronPalette.green700,
        successSubtle = ElectronPalette.green900,
        onSuccess = ElectronPalette.graphite000,
        warning = ElectronPalette.amber500,
        warningFill = ElectronPalette.amber700,
        warningSubtle = ElectronPalette.amber900,
        onWarning = ElectronPalette.graphite000,
        error = ElectronPalette.red400,
        errorFill = ElectronPalette.red600,
        errorSubtle = ElectronPalette.red900,
        onError = ElectronPalette.graphite000,
        info = ElectronPalette.blue400,
        infoFill = ElectronPalette.blue600,
        infoSubtle = ElectronPalette.blue900,
        onInfo = ElectronPalette.graphite000
    ),
    interaction = ElectronColors.Interaction(
        hover = ElectronPalette.whiteAlpha10,
        pressed = ElectronPalette.whiteAlpha16,
        selected = ElectronPalette.volt900,
        disabledBackground = ElectronPalette.graphite800,
        disabledContent = ElectronPalette.graphite500
    )
)

/** Material 3 bridge; see the light scheme for why every role is mapped. */
internal val electronDarkMaterialScheme = darkColorScheme(
    primary = ElectronPalette.volt400,
    onPrimary = ElectronPalette.graphite950,
    primaryContainer = ElectronPalette.volt900,
    onPrimaryContainer = ElectronPalette.volt100,
    inversePrimary = ElectronPalette.volt600,
    secondary = ElectronPalette.ion400,
    onSecondary = ElectronPalette.graphite950,
    secondaryContainer = ElectronPalette.volt900,
    onSecondaryContainer = ElectronPalette.volt100,
    tertiary = ElectronPalette.ion400,
    onTertiary = ElectronPalette.graphite950,
    tertiaryContainer = ElectronPalette.ion900,
    onTertiaryContainer = ElectronPalette.ion300,
    background = ElectronPalette.graphite950,
    onBackground = ElectronPalette.graphite050,
    surface = ElectronPalette.graphite900,
    onSurface = ElectronPalette.graphite050,
    surfaceVariant = ElectronPalette.graphite800,
    onSurfaceVariant = ElectronPalette.graphite300,
    surfaceTint = ElectronPalette.graphite900,
    surfaceBright = ElectronPalette.graphite800,
    surfaceDim = ElectronPalette.graphite950,
    surfaceContainerLowest = ElectronPalette.graphite950,
    surfaceContainerLow = ElectronPalette.graphite900,
    surfaceContainer = ElectronPalette.graphite900,
    surfaceContainerHigh = ElectronPalette.graphite800,
    surfaceContainerHighest = ElectronPalette.graphite800,
    inverseSurface = ElectronPalette.graphite050,
    inverseOnSurface = ElectronPalette.graphite900,
    outline = ElectronPalette.graphite700,
    outlineVariant = ElectronPalette.graphite800,
    error = ElectronPalette.red400,
    onError = ElectronPalette.graphite950,
    errorContainer = ElectronPalette.red900,
    onErrorContainer = ElectronPalette.red400,
    scrim = Color.Black
)
