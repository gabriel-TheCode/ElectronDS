package com.electron.designsystem.components.emptystate.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel

/** Load failure: critical tone and an optional secondary "Retry" button. */
@Composable
internal fun EmptyStateError(
    uiModel: EmptyStateUiModel.Error,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val retry = uiModel.retryLabel
    EmptyStateFrame(
        icon = uiModel.icon,
        tone = AvatarTone.Critical,
        title = uiModel.title,
        message = uiModel.message,
        testTag = uiModel.testTag,
        modifier = modifier,
        actions = if (retry != null) {
            {
                ElectronButton(
                    uiModel = ButtonUiModel.Secondary(text = retry, size = ButtonSize.Medium),
                    onClick = onRetryClick
                )
            }
        } else {
            null
        }
    )
}
