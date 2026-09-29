package com.electron.designsystem.components.progress.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.progress.models.ProgressSize
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens

internal data class ProgressColorsResolved(val indicator: Color, val track: Color)

@Composable
internal fun ProgressTone.resolve(): ProgressColorsResolved {
    val c = ElectronTheme.colors
    return when (this) {
        ProgressTone.Brand -> ProgressColorsResolved(c.brand.primary, c.brand.primarySubtle)
        ProgressTone.Accent -> ProgressColorsResolved(c.brand.accent, c.brand.accentSubtle)
        ProgressTone.Success -> ProgressColorsResolved(c.status.success, c.status.successSubtle)
        ProgressTone.Warning -> ProgressColorsResolved(c.status.warning, c.status.warningSubtle)
        ProgressTone.Error -> ProgressColorsResolved(c.status.error, c.status.errorSubtle)
    }
}

internal fun ProgressSize.resolve(): Dp = when (this) {
    ProgressSize.Sm -> ElectronDimens.progressCircularSm
    ProgressSize.Md -> ElectronDimens.progressCircularMd
    ProgressSize.Lg -> ElectronDimens.progressCircularLg
}

internal fun Float?.clampProgress(): Float? = this?.coerceIn(0f, 1f)
