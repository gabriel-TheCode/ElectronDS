package com.electron.designsystem.tokens

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * ElectronDS component dimension tokens.
 *
 * Every value sits on the 4dp grid. Controls that appear side by side in a
 * form share one height: a Large button and an input field are both 48dp,
 * so a field and its submit button line up without per-screen fixes.
 */
public object ElectronDimens {
    // Control heights
    public val controlHeightSm: Dp = 32.dp
    public val controlHeightMd: Dp = 40.dp
    public val controlHeightLg: Dp = 48.dp
    public val fieldHeight: Dp = 48.dp
    public val chipHeight: Dp = 32.dp
    public val sheetHeaderHeight: Dp = 56.dp
    public val topBarHeight: Dp = 56.dp
    public val listItemMinHeight: Dp = 56.dp

    // Icon sizes
    public val iconXs: Dp = 12.dp
    public val iconSm: Dp = 16.dp
    public val iconMd: Dp = 20.dp
    public val iconLg: Dp = 24.dp
    public val iconXl: Dp = 32.dp
    public val iconXxl: Dp = 48.dp

    // Avatar sizes
    public val avatarSm: Dp = 24.dp
    public val avatarMd: Dp = 40.dp
    public val avatarLg: Dp = 64.dp

    // Badge sizes
    public val badgeDot: Dp = 8.dp
    public val badgeHeight: Dp = 16.dp

    // Progress indicators
    public val progressTrackHeight: Dp = 4.dp
    public val progressStrokeWidth: Dp = 4.dp
    public val progressCircularSm: Dp = 24.dp
    public val progressCircularMd: Dp = 40.dp
    public val progressCircularLg: Dp = 64.dp

    // Layout
    /** Maximum width of centered reading content (empty states, messages) on tablets and TV. */
    public val readableWidth: Dp = 400.dp

    // Misc
    public val borderWidth: Dp = 1.dp
    public val borderWidthFocus: Dp = 2.dp
    public val separatorHeight: Dp = 1.dp
    public val minTouchTarget: Dp = 48.dp
}
