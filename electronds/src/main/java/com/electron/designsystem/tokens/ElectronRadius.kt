package com.electron.designsystem.tokens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ElectronDS corner radius tokens.
 *
 * The Electron silhouette is crisp: small radii on dense controls,
 * a single large radius reserved for floating surfaces, and full
 * rounding only on pills (chips, FAB).
 */
public object ElectronRadius {
    public val none: Dp = 0.dp
    public val xs: Dp = 4.dp
    public val sm: Dp = 8.dp
    public val md: Dp = 12.dp
    public val lg: Dp = 20.dp
    public val full: Dp = 1000.dp
}

/**
 * Pre-built shapes mapped to component families.
 * Primitives consume these instead of constructing shapes ad hoc.
 */
public object ElectronShapes {
    public val control: RoundedCornerShape = RoundedCornerShape(ElectronRadius.sm)
    public val segment: RoundedCornerShape = RoundedCornerShape(ElectronRadius.xs)
    public val field: RoundedCornerShape = RoundedCornerShape(ElectronRadius.sm)
    public val card: RoundedCornerShape = RoundedCornerShape(ElectronRadius.md)
    public val sheet: RoundedCornerShape =
        RoundedCornerShape(topStart = ElectronRadius.lg, topEnd = ElectronRadius.lg)
    public val dialog: RoundedCornerShape = RoundedCornerShape(ElectronRadius.lg)
    public val pill: RoundedCornerShape = RoundedCornerShape(ElectronRadius.full)
}
