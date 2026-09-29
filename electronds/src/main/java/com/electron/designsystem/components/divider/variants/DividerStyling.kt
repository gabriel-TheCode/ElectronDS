package com.electron.designsystem.components.divider.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.divider.models.DividerEmphasis
import com.electron.designsystem.components.divider.models.DividerInset
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

internal data class DividerInsets(val start: Dp, val end: Dp)

internal fun DividerInset.resolve(): DividerInsets = when (this) {
    DividerInset.None -> DividerInsets(ElectronSpacing.none, ElectronSpacing.none)
    DividerInset.Start -> DividerInsets(ElectronSpacing.lg, ElectronSpacing.none)
    DividerInset.Both -> DividerInsets(ElectronSpacing.lg, ElectronSpacing.lg)
}

@Composable
internal fun DividerEmphasis.resolve(): Color {
    val c = ElectronTheme.colors
    return when (this) {
        DividerEmphasis.Subtle -> c.border.subtle
        DividerEmphasis.Default -> c.border.default
        DividerEmphasis.Strong -> c.border.strong
    }
}
