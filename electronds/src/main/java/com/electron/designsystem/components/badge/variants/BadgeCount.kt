package com.electron.designsystem.components.badge.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.badge.primitives.BadgePrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun BadgeCount(
    uiModel: BadgeUiModel.Count,
    modifier: Modifier = Modifier
) {
    val colors = uiModel.tone.resolve()
    BadgePrimitive(
        text = formatBadgeCount(uiModel.count, uiModel.max),
        backgroundColor = colors.background,
        contentColor = colors.content,
        textStyle = ElectronTheme.typography.labelSmall,
        contentDescription = uiModel.contentDescription,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
