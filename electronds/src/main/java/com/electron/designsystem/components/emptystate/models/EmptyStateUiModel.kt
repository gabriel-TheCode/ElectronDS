package com.electron.designsystem.components.emptystate.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.avatar.models.AvatarTone

/**
 * Empty state UI models: a centered message shown when a list, a search
 * or a screen has no content, or failed to load.
 *
 * Replaces the pattern of feature-specific error cards with embedded
 * illustrations: the frame is generic, the wording is feature data.
 */
public sealed class EmptyStateUiModel {

    /** Nothing to show yet; optional call to action. */
    @Immutable
    public data class Default(
        val icon: ImageVector,
        val title: String,
        val message: String? = null,
        val primaryActionLabel: String? = null,
        val secondaryActionLabel: String? = null,
        val tone: AvatarTone = AvatarTone.Brand,
        val testTag: String = "electron_empty_state"
    ) : EmptyStateUiModel()

    /** Loading failed; the primary action is typically "Retry". */
    @Immutable
    public data class Error(
        val icon: ImageVector,
        val title: String,
        val message: String? = null,
        val retryLabel: String? = null,
        val testTag: String = "electron_empty_state_error"
    ) : EmptyStateUiModel()
}
