package com.electron.designsystem.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ElectronDS elevation tokens.
 *
 * Electron surfaces prefer borders and tonal contrast over heavy shadows.
 * Elevation is therefore a short, intentional scale.
 */
public object ElectronElevation {
    public val flat: Dp = 0.dp
    public val raised: Dp = 2.dp
    public val floating: Dp = 6.dp
    public val overlay: Dp = 12.dp
}
