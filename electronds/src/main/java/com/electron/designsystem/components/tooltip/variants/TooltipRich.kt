package com.electron.designsystem.components.tooltip.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.tooltip.models.TooltipUiModel
import com.electron.designsystem.components.tooltip.primitives.RichTooltipSurface
import com.electron.designsystem.components.tooltip.primitives.TooltipAnchorPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/** Rich surface, shared by the anchored tooltip and by screenshot tests. */
@Composable
internal fun TooltipRichSurface(
    uiModel: TooltipUiModel.Rich,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = ElectronTheme.colors
    val actionLabel = uiModel.actionLabel
    RichTooltipSurface(
        text = uiModel.text,
        title = uiModel.title,
        containerColor = c.background.surfaceRaised,
        borderColor = c.border.subtle,
        titleStyle = ElectronTheme.typography.titleSmall,
        titleColor = c.content.primary,
        textStyle = ElectronTheme.typography.bodyMedium,
        textColor = c.content.secondary,
        testTag = uiModel.testTag,
        modifier = modifier,
        action = if (actionLabel != null) {
            {
                ElectronButton(
                    uiModel = ButtonUiModel.Link(text = actionLabel, size = ButtonSize.Small),
                    onClick = onActionClick
                )
            }
        } else {
            null
        }
    )
}

@Composable
internal fun TooltipRich(
    uiModel: TooltipUiModel.Rich,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TooltipAnchorPrimitive(
        isRich = true,
        tooltip = { TooltipRichSurface(uiModel, onActionClick) },
        modifier = modifier,
        content = content
    )
}
