package com.electron.designsystem.tokens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ElectronDS corner radius tokens.
 *
 * The Electron silhouette is crisp, and radius grows with the size and
 * elevation of an element:
 * - xs (4): small labels (tags).
 * - sm (8): interactive controls (buttons, fields, segmented tracks).
 * - md (12): containers (cards).
 * - lg (20): floating surfaces (sheets, dialogs).
 * - full: only where roundness carries meaning: chips (removable tokens),
 *   the FAB (floating), badges, avatars and switches.
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
    public val tag: RoundedCornerShape = RoundedCornerShape(ElectronRadius.xs)
    public val control: RoundedCornerShape = RoundedCornerShape(ElectronRadius.sm)
    public val field: RoundedCornerShape = RoundedCornerShape(ElectronRadius.sm)

    /**
     * Shape of an element nested inside a [control] with [ElectronSpacing.xxs]
     * padding (the selected segment of a segmented control). Concentric:
     * inner radius = outer radius - inset, so both curves stay parallel.
     */
    public val segment: RoundedCornerShape = RoundedCornerShape(ElectronRadius.sm - ElectronSpacing.xxs)

    public val card: RoundedCornerShape = RoundedCornerShape(ElectronRadius.md)

    /**
     * Small floating surfaces anchored to an element (menus, rich tooltips).
     * They float, but are compact: container radius, not the 20dp of sheets
     * and dialogs, which would look bubbly at menu size.
     */
    public val popover: RoundedCornerShape = RoundedCornerShape(ElectronRadius.md)

    /** Tab indicator: rounded on top, flat where it meets the divider. */
    public val indicator: RoundedCornerShape =
        RoundedCornerShape(topStart = ElectronRadius.xs, topEnd = ElectronRadius.xs)

    public val sheet: RoundedCornerShape =
        RoundedCornerShape(topStart = ElectronRadius.lg, topEnd = ElectronRadius.lg)
    public val dialog: RoundedCornerShape = RoundedCornerShape(ElectronRadius.lg)
    public val pill: RoundedCornerShape = RoundedCornerShape(ElectronRadius.full)
}
