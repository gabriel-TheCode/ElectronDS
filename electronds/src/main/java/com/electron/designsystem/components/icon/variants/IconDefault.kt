package com.electron.designsystem.components.icon.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.icon.models.IconSize
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.icon.primitives.IconPrimitive
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens

/**
 * Default icon variant: resolves semantic size and tone tokens into raw
 * values before delegating to the primitive.
 */
@Composable
internal fun IconDefault(
    uiModel: IconUiModel.Default,
    modifier: Modifier = Modifier
) {
    IconPrimitive(
        imageVector = uiModel.imageVector,
        contentDescription = uiModel.contentDescription,
        tint = uiModel.tone.resolve(),
        size = uiModel.size.resolve(),
        testTag = uiModel.testTag,
        modifier = modifier
    )
}

internal fun IconSize.resolve(): Dp = when (this) {
    IconSize.Xs -> ElectronDimens.iconXs
    IconSize.Sm -> ElectronDimens.iconSm
    IconSize.Md -> ElectronDimens.iconMd
    IconSize.Lg -> ElectronDimens.iconLg
    IconSize.Xl -> ElectronDimens.iconXl
}

@Composable
internal fun IconTone.resolve(): Color {
    val colors = ElectronTheme.colors
    return when (this) {
        IconTone.Default -> colors.content.primary
        IconTone.Muted -> colors.content.muted
        IconTone.Brand -> colors.brand.primary
        IconTone.Accent -> colors.brand.accent
        IconTone.OnBrand -> colors.content.onBrand
        IconTone.Success -> colors.status.success
        IconTone.Warning -> colors.status.warning
        IconTone.Error -> colors.status.error
        IconTone.Info -> colors.status.info
    }
}
