package com.electron.designsystem.foundation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.electron.designsystem.tokens.ElectronTypography

internal val LocalElectronColors = staticCompositionLocalOf { ElectronLightColors }

/**
 * ElectronDS theme entry point.
 *
 * Wraps [MaterialTheme] so that Material 3 primitives used internally pick
 * up Electron colors, while Electron components read the richer semantic
 * roles through [ElectronTheme.colors].
 *
 * Note: no dynamic color. A design system exists to guarantee brand
 * consistency, and Material You palettes defeat that guarantee.
 *
 * Usage:
 * ```
 * ElectronTheme {
 *     ElectronButton(
 *         uiModel = ButtonUiModel.Primary(text = "Charge"),
 *         onClick = viewModel::onChargeClicked
 *     )
 * }
 * ```
 */
@Composable
public fun ElectronTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) ElectronDarkColors else ElectronLightColors
    val materialScheme = if (darkTheme) electronDarkMaterialScheme else electronLightMaterialScheme

    CompositionLocalProvider(LocalElectronColors provides colors) {
        MaterialTheme(
            colorScheme = materialScheme,
            typography = ElectronTypography.toMaterial3Typography(),
            content = content
        )
    }
}

/** Accessor object mirroring the composable theme. */
public object ElectronTheme {
    public val colors: ElectronColors
        @Composable get() = LocalElectronColors.current

    public val typography: ElectronTypography
        get() = ElectronTypography
}
