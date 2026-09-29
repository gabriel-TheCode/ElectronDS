package com.electron.designsystem.components.dialog.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.dialog.models.DialogUiModel

@Composable
internal fun DialogConfirmation(
    uiModel: DialogUiModel.Confirmation,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    DialogFrame(
        title = uiModel.title,
        message = uiModel.message,
        confirmLabel = uiModel.confirmLabel,
        dismissLabel = uiModel.dismissLabel,
        icon = uiModel.icon,
        isDestructive = false,
        testTag = uiModel.testTag,
        onConfirmClick = onConfirmClick,
        onDismissClick = onDismissClick,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    )
}
