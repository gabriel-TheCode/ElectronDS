package com.electron.designsystem.components.dialog.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Dialog UI models. Visibility is not part of the model: the screen shows
 * the dialog by composing it and hides it by removing it from composition.
 */
public sealed class DialogUiModel {

    /** Standard confirmation: brand confirm button. */
    @Immutable
    public data class Confirmation(
        val title: String,
        val message: String,
        val confirmLabel: String,
        val dismissLabel: String? = null,
        val icon: ImageVector? = null,
        val testTag: String = "electron_dialog_confirmation"
    ) : DialogUiModel()

    /** Irreversible action (delete, disconnect): error-toned confirm button. */
    @Immutable
    public data class Destructive(
        val title: String,
        val message: String,
        val confirmLabel: String,
        val dismissLabel: String,
        val icon: ImageVector? = null,
        val testTag: String = "electron_dialog_destructive"
    ) : DialogUiModel()
}
