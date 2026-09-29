package com.electron.designsystem.components.dialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LinkOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.dialog.models.DialogUiModel
import com.electron.designsystem.components.dialog.variants.DialogConfirmation
import com.electron.designsystem.components.dialog.variants.DialogDestructive
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronDialog
 *
 * Purpose: modal confirmation. Built from ElectronAvatar and ElectronButton.
 * The screen shows it by composing it (typically behind a state flag) and
 * hides it by removing it from composition.
 *
 * API:
 * - [uiModel]: variant (sealed [DialogUiModel]).
 * - [onConfirmClick]: signal emitted by the confirm button.
 * - [onDismissRequest]: signal emitted on back press or scrim tap.
 * - [onDismissClick]: signal emitted by the dismiss button. Defaults to
 *   [onDismissRequest]; only relevant when the UI model has a `dismissLabel`.
 *
 * Usage:
 * ```
 * if (uiState.showDisconnectDialog) {
 *     ElectronDialog(
 *         uiModel = DialogUiModel.Destructive(
 *             title = "Disconnect charger?",
 *             message = "Scheduled sessions will be cancelled.",
 *             confirmLabel = "Disconnect",
 *             dismissLabel = "Cancel"
 *         ),
 *         onConfirmClick = viewModel::onDisconnectConfirmed,
 *         onDismissRequest = viewModel::onDisconnectDialogDismissed
 *     )
 * }
 * ```
 */
@Composable
public fun ElectronDialog(
    uiModel: DialogUiModel,
    onConfirmClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onDismissClick: () -> Unit = onDismissRequest
) {
    when (uiModel) {
        is DialogUiModel.Confirmation ->
            DialogConfirmation(uiModel, onConfirmClick, onDismissClick, onDismissRequest, modifier)
        is DialogUiModel.Destructive ->
            DialogDestructive(uiModel, onConfirmClick, onDismissClick, onDismissRequest, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronDialogPreview() {
    ElectronPreviewSurface {
        ElectronDialog(
            uiModel = DialogUiModel.Destructive(
                title = "Disconnect charger?",
                message = "Scheduled charging sessions will be cancelled.",
                confirmLabel = "Disconnect",
                dismissLabel = "Cancel",
                icon = Icons.Outlined.LinkOff
            ),
            onConfirmClick = {},
            onDismissRequest = {}
        )
    }
}
