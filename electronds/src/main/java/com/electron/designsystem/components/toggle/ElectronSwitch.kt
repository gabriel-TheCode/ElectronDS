package com.electron.designsystem.components.toggle

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.toggle.models.SwitchUiModel
import com.electron.designsystem.components.toggle.variants.SwitchDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronSwitch
 *
 * Purpose: on/off control. State is hoisted: the screen owns `isChecked`
 * and reacts to [onCheckedChange].
 *
 * Usage:
 * ```
 * ElectronSwitch(
 *     uiModel = SwitchUiModel.Default(isChecked = uiState.notificationsEnabled),
 *     onCheckedChange = viewModel::onNotificationsToggled
 * )
 * ```
 */
@Composable
public fun ElectronSwitch(
    uiModel: SwitchUiModel,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is SwitchUiModel.Default -> SwitchDefault(uiModel, onCheckedChange, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronSwitchPreview() {
    ElectronPreviewSurface {
        ElectronSwitch(SwitchUiModel.Default(isChecked = true), onCheckedChange = {})
        ElectronSwitch(SwitchUiModel.Default(isChecked = false), onCheckedChange = {})
        ElectronSwitch(SwitchUiModel.Default(isChecked = true, isEnabled = false), onCheckedChange = {})
    }
}
