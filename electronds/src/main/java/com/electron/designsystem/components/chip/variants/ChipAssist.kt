package com.electron.designsystem.components.chip.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.chip.models.ChipSignal
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.chip.primitives.ChipPrimitive

@Composable
internal fun ChipAssist(
    uiModel: ChipUiModel.Assist,
    onSignal: (ChipSignal) -> Unit,
    modifier: Modifier = Modifier
) {
    ChipPrimitive(
        text = uiModel.text,
        leadingIcon = uiModel.leadingIcon,
        isSelected = uiModel.isSelected,
        isEnabled = uiModel.isEnabled,
        onClick = { onSignal(ChipSignal.Clicked) },
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
