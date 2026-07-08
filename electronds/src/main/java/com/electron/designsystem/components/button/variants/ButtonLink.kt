package com.electron.designsystem.components.button.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.button.primitives.ButtonPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/** Inline underlined link-style button. */
@Composable
internal fun ButtonLink(
    uiModel: ButtonUiModel.Link,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentColor = if (uiModel.isEnabled) {
        ElectronTheme.colors.content.link
    } else {
        ElectronTheme.colors.interaction.disabledContent
    }
    ButtonPrimitive(
        text = uiModel.text,
        icon = uiModel.icon,
        backgroundColor = Color.Transparent,
        contentColor = contentColor,
        borderColor = null,
        height = uiModel.size.height(),
        contentPadding = uiModel.size.contentPadding(),
        isEnabled = uiModel.isEnabled,
        isFullWidth = false,
        isUnderlined = true,
        isLoading = false,
        testTag = uiModel.testTag,
        onClick = onClick,
        modifier = modifier
    )
}
