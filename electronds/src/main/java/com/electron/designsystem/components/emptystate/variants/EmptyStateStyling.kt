package com.electron.designsystem.components.emptystate.variants

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.emptystate.primitives.EmptyStatePrimitive
import com.electron.designsystem.foundation.ElectronTheme

/** Shared frame: large avatar visual and the Electron text styles. */
@Composable
internal fun EmptyStateFrame(
    icon: ImageVector,
    tone: AvatarTone,
    title: String,
    message: String?,
    testTag: String,
    modifier: Modifier,
    actions: (@Composable ColumnScope.() -> Unit)?
) {
    val c = ElectronTheme.colors
    EmptyStatePrimitive(
        title = title,
        titleStyle = ElectronTheme.typography.titleLarge,
        titleColor = c.content.primary,
        message = message,
        messageStyle = ElectronTheme.typography.bodyMedium,
        messageColor = c.content.secondary,
        visual = { ElectronAvatar(AvatarUiModel.Default(icon = icon, size = AvatarSize.Lg, tone = tone)) },
        actions = actions,
        testTag = testTag,
        modifier = modifier
    )
}
