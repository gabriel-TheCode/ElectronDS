package com.electron.designsystem.components.dialog.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.dialog.primitives.DialogPrimitive
import com.electron.designsystem.foundation.ElectronTheme

/**
 * Shared dialog rendering: Electron typography, an avatar visual and a
 * tertiary dismiss button next to a primary confirm button.
 */
@Composable
internal fun DialogFrame(
    title: String,
    message: String,
    confirmLabel: String,
    dismissLabel: String?,
    icon: ImageVector?,
    isDestructive: Boolean,
    testTag: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier
) {
    val c = ElectronTheme.colors
    DialogPrimitive(
        title = title,
        titleStyle = ElectronTheme.typography.headlineSmall,
        titleColor = c.content.primary,
        message = message,
        messageStyle = ElectronTheme.typography.bodyMedium,
        messageColor = c.content.secondary,
        containerColor = c.background.surfaceRaised,
        onDismissRequest = onDismissRequest,
        visual = if (icon != null) {
            {
                ElectronAvatar(
                    AvatarUiModel.Default(
                        icon = icon,
                        size = AvatarSize.Md,
                        tone = if (isDestructive) AvatarTone.Critical else AvatarTone.Brand
                    )
                )
            }
        } else {
            null
        },
        testTag = testTag,
        modifier = modifier
    ) {
        if (dismissLabel != null) {
            ElectronButton(
                uiModel = ButtonUiModel.Tertiary(
                    text = dismissLabel,
                    size = ButtonSize.Medium,
                    testTag = "${testTag}_dismiss"
                ),
                onClick = onDismissClick
            )
        }
        ElectronButton(
            uiModel = ButtonUiModel.Primary(
                text = confirmLabel,
                size = ButtonSize.Medium,
                state = if (isDestructive) ButtonState.Error else ButtonState.Default,
                testTag = "${testTag}_confirm"
            ),
            onClick = onConfirmClick
        )
    }
}
