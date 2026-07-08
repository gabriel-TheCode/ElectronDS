package com.electron.designsystem.components.button.models

import androidx.compose.runtime.Immutable
import com.electron.designsystem.components.icon.models.IconUiModel

/**
 * Button UI models.
 *
 * Rules enforced here:
 * - No lambdas: signals (onClick) are passed to the component, never stored.
 * - No raw styling values: sizes and states are semantic enums.
 * - One subclass per visual variant so the component can dispatch exhaustively.
 */
public sealed class ButtonUiModel {

    @Immutable
    public data class Primary(
        val text: String? = null,
        val icon: IconUiModel.Default? = null,
        val size: ButtonSize = ButtonSize.Large,
        val state: ButtonState = ButtonState.Default,
        val isEnabled: Boolean = true,
        val isFullWidth: Boolean = false,
        val testTag: String = "electron_button_primary"
    ) : ButtonUiModel()

    @Immutable
    public data class Secondary(
        val text: String? = null,
        val icon: IconUiModel.Default? = null,
        val size: ButtonSize = ButtonSize.Large,
        val state: ButtonState = ButtonState.Default,
        val isEnabled: Boolean = true,
        val isFullWidth: Boolean = false,
        val testTag: String = "electron_button_secondary"
    ) : ButtonUiModel()

    @Immutable
    public data class Tertiary(
        val text: String? = null,
        val icon: IconUiModel.Default? = null,
        val size: ButtonSize = ButtonSize.Large,
        val state: ButtonState = ButtonState.Default,
        val isEnabled: Boolean = true,
        val isFullWidth: Boolean = false,
        val testTag: String = "electron_button_tertiary"
    ) : ButtonUiModel()

    @Immutable
    public data class Link(
        val text: String,
        val icon: IconUiModel.Default? = null,
        val size: ButtonSize = ButtonSize.Medium,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_button_link"
    ) : ButtonUiModel()

    @Immutable
    public data class Loading(
        val text: String? = null,
        val size: ButtonSize = ButtonSize.Large,
        val isFullWidth: Boolean = false,
        val testTag: String = "electron_button_loading"
    ) : ButtonUiModel()
}

public enum class ButtonSize { Small, Medium, Large }

/** Feedback state applied by the feature after an action outcome. */
public enum class ButtonState { Default, Success, Error }
