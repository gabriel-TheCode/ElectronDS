package com.electron.designsystem.components.button.variants

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Internal styling resolution for button variants.
 *
 * In the legacy system this logic lived in the uimodel package and pattern-matched on
 * every subclass; here each variant asks for its own colors, keeping the
 * model layer free of color decisions.
 */
internal data class ButtonColorsResolved(
    val background: Color,
    val content: Color,
    val border: Color?
)

@Composable
internal fun primaryButtonColors(state: ButtonState, isEnabled: Boolean): ButtonColorsResolved {
    val c = ElectronTheme.colors
    if (!isEnabled) {
        return ButtonColorsResolved(c.interaction.disabledBackground, c.interaction.disabledContent, null)
    }
    return when (state) {
        ButtonState.Default -> ButtonColorsResolved(c.brand.primaryFill, c.brand.onPrimary, null)
        ButtonState.Success -> ButtonColorsResolved(c.status.successFill, c.status.onSuccess, null)
        ButtonState.Error -> ButtonColorsResolved(c.status.errorFill, c.status.onError, null)
    }
}

/**
 * Secondary is tonal, not outlined: a tinted container reads as a real
 * button on both canvas and surface without adding a border, and keeps
 * the whole button family border-free. Disabled matches Primary so every
 * disabled button looks the same regardless of its emphasis.
 */
@Composable
internal fun secondaryButtonColors(state: ButtonState, isEnabled: Boolean): ButtonColorsResolved {
    val c = ElectronTheme.colors
    if (!isEnabled) {
        return ButtonColorsResolved(c.interaction.disabledBackground, c.interaction.disabledContent, null)
    }
    return when (state) {
        ButtonState.Default -> ButtonColorsResolved(c.brand.primarySubtle, c.brand.primaryStrong, null)
        ButtonState.Success -> ButtonColorsResolved(c.status.successSubtle, c.status.success, null)
        ButtonState.Error -> ButtonColorsResolved(c.status.errorSubtle, c.status.error, null)
    }
}

@Composable
internal fun tertiaryButtonColors(state: ButtonState, isEnabled: Boolean): ButtonColorsResolved {
    val c = ElectronTheme.colors
    if (!isEnabled) {
        return ButtonColorsResolved(Color.Transparent, c.interaction.disabledContent, null)
    }
    return when (state) {
        ButtonState.Default -> ButtonColorsResolved(Color.Transparent, c.brand.primary, null)
        ButtonState.Success -> ButtonColorsResolved(Color.Transparent, c.status.success, null)
        ButtonState.Error -> ButtonColorsResolved(Color.Transparent, c.status.error, null)
    }
}

internal fun ButtonSize.height(): Dp = when (this) {
    ButtonSize.Small -> ElectronDimens.controlHeightSm
    ButtonSize.Medium -> ElectronDimens.controlHeightMd
    ButtonSize.Large -> ElectronDimens.controlHeightLg
}

/** Links sit inline with text: no horizontal padding, so their text aligns with the copy around them. */
internal val LinkContentPadding: PaddingValues = PaddingValues(horizontal = ElectronSpacing.none)

internal fun ButtonSize.contentPadding(): PaddingValues = when (this) {
    ButtonSize.Small -> PaddingValues(horizontal = ElectronSpacing.md)
    ButtonSize.Medium -> PaddingValues(horizontal = ElectronSpacing.lg)
    ButtonSize.Large -> PaddingValues(horizontal = ElectronSpacing.xl)
}
