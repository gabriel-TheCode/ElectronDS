package com.electron.designsystem.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ElectronDS spacing scale.
 *
 * A 4dp base grid. Every layout gap in the design system is expressed with
 * one of these tokens: no raw dp values inside primitives or variants.
 */
public object ElectronSpacing {
    public val none: Dp = 0.dp
    public val xxs: Dp = 2.dp
    public val xs: Dp = 4.dp
    public val sm: Dp = 8.dp
    public val md: Dp = 12.dp
    public val lg: Dp = 16.dp
    public val xl: Dp = 24.dp
    public val xxl: Dp = 32.dp
    public val xxxl: Dp = 48.dp
    public val huge: Dp = 64.dp
}
