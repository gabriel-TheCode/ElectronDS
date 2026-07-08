package com.electron.designsystem.components.fab.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.fab.models.FabUiModel
import com.electron.designsystem.components.fab.primitives.ExtendedFabPrimitive

@Composable
internal fun FabExtended(
    uiModel: FabUiModel.Extended,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExtendedFabPrimitive(
        text = uiModel.text,
        icon = uiModel.icon,
        iconContentDescription = uiModel.iconContentDescription,
        isExpanded = uiModel.isExpanded,
        testTag = uiModel.testTag,
        onClick = onClick,
        modifier = modifier
    )
}
