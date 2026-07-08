package com.electron.designsystem.components.toggle.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.toggle.models.SwitchUiModel
import com.electron.designsystem.components.toggle.primitives.SwitchPrimitive

@Composable
internal fun SwitchDefault(
    uiModel: SwitchUiModel.Default,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    SwitchPrimitive(
        isChecked = uiModel.isChecked,
        isEnabled = uiModel.isEnabled,
        testTag = uiModel.testTag,
        onCheckedChange = onCheckedChange,
        modifier = modifier
    )
}
