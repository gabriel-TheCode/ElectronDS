package com.electron.designsystem.components.chip.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.chip.primitives.ChipPrimitive

@Composable
internal fun ChipAssist(
    uiModel: ChipUiModel.Assist,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ChipPrimitive(
        text = uiModel.text,
        leadingIcon = uiModel.leadingIcon,
        isSelected = uiModel.isSelected,
        isEnabled = uiModel.isEnabled,
        onClick = onClick,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
