package com.electron.designsystem.components.button.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.button.primitives.ButtonPrimitive

/** Outlined button for secondary actions alongside a primary one. */
@Composable
internal fun ButtonSecondary(
    uiModel: ButtonUiModel.Secondary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = secondaryButtonColors(uiModel.state, uiModel.isEnabled)
    ButtonPrimitive(
        text = uiModel.text,
        icon = uiModel.icon,
        backgroundColor = colors.background,
        contentColor = colors.content,
        borderColor = colors.border,
        height = uiModel.size.height(),
        contentPadding = uiModel.size.contentPadding(),
        isEnabled = uiModel.isEnabled,
        isFullWidth = uiModel.isFullWidth,
        isUnderlined = false,
        isLoading = false,
        testTag = uiModel.testTag,
        onClick = onClick,
        modifier = modifier
    )
}
