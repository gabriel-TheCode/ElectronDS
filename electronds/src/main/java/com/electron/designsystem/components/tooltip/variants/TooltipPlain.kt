package com.electron.designsystem.components.tooltip.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.tooltip.models.TooltipUiModel
import com.electron.designsystem.components.tooltip.primitives.PlainTooltipSurface
import com.electron.designsystem.components.tooltip.primitives.TooltipAnchorPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/** Plain surface, shared by the anchored tooltip and by screenshot tests. */
@Composable
internal fun TooltipPlainSurface(uiModel: TooltipUiModel.Plain, modifier: Modifier = Modifier) {
    val c = ElectronTheme.colors
    PlainTooltipSurface(
        text = uiModel.text,
        containerColor = c.content.primary,
        contentColor = c.content.inverse,
        textStyle = ElectronTheme.typography.labelMedium,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}

@Composable
internal fun TooltipPlain(
    uiModel: TooltipUiModel.Plain,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TooltipAnchorPrimitive(
        isRich = false,
        tooltip = { TooltipPlainSurface(uiModel) },
        modifier = modifier,
        content = content
    )
}
