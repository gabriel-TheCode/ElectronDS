package com.electron.designsystem.components.emptystate.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel

/** Empty content: optional primary button and secondary link. */
@Composable
internal fun EmptyStateDefault(
    uiModel: EmptyStateUiModel.Default,
    onPrimaryActionClick: () -> Unit,
    onSecondaryActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primary = uiModel.primaryActionLabel
    val secondary = uiModel.secondaryActionLabel
    EmptyStateFrame(
        icon = uiModel.icon,
        tone = uiModel.tone,
        title = uiModel.title,
        message = uiModel.message,
        testTag = uiModel.testTag,
        modifier = modifier,
        actions = if (primary != null || secondary != null) {
            {
                if (primary != null) {
                    ElectronButton(
                        uiModel = ButtonUiModel.Primary(text = primary, size = ButtonSize.Medium),
                        onClick = onPrimaryActionClick
                    )
                }
                if (secondary != null) {
                    ElectronButton(
                        uiModel = ButtonUiModel.Link(text = secondary),
                        onClick = onSecondaryActionClick
                    )
                }
            }
        } else {
            null
        }
    )
}
