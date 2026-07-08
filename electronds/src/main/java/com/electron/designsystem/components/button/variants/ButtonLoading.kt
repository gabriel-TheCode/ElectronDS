package com.electron.designsystem.components.button.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.button.primitives.ButtonPrimitive

/** In-progress button: filled, non-interactive, with a spinner. */
@Composable
internal fun ButtonLoading(
    uiModel: ButtonUiModel.Loading,
    modifier: Modifier = Modifier
) {
    val colors = primaryButtonColors(state = ButtonState.Default, isEnabled = true)
    ButtonPrimitive(
        text = uiModel.text,
        icon = null,
        backgroundColor = colors.background,
        contentColor = colors.content,
        borderColor = null,
        height = uiModel.size.height(),
        contentPadding = uiModel.size.contentPadding(),
        isEnabled = false,
        isFullWidth = uiModel.isFullWidth,
        isUnderlined = false,
        isLoading = true,
        testTag = uiModel.testTag,
        onClick = {},
        modifier = modifier
    )
}
