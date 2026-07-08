package com.electron.designsystem.components.fab.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.fab.models.FabUiModel
import com.electron.designsystem.components.fab.primitives.CompactFabPrimitive

@Composable
internal fun FabCompact(
    uiModel: FabUiModel.Compact,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompactFabPrimitive(
        icon = uiModel.icon,
        iconContentDescription = uiModel.iconContentDescription,
        testTag = uiModel.testTag,
        onClick = onClick,
        modifier = modifier
    )
}
