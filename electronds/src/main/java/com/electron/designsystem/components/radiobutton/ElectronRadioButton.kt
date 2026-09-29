package com.electron.designsystem.components.radiobutton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.radiobutton.models.RadioButtonUiModel
import com.electron.designsystem.components.radiobutton.variants.RadioButtonDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronRadioButton
 *
 * Purpose: single-selection control inside a group. The screen owns which
 * option is selected and reacts to [onClick].
 *
 * Usage:
 * ```
 * plans.forEach { plan ->
 *     ElectronRadioButton(
 *         uiModel = RadioButtonUiModel.Default(isSelected = plan.id == uiState.selectedPlanId, label = plan.name),
 *         onClick = { viewModel.onPlanSelected(plan.id) }
 *     )
 * }
 * ```
 */
@Composable
public fun ElectronRadioButton(
    uiModel: RadioButtonUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is RadioButtonUiModel.Default -> RadioButtonDefault(uiModel, onClick, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronRadioButtonPreview() {
    ElectronPreviewSurface {
        ElectronRadioButton(RadioButtonUiModel.Default(isSelected = true, label = "Monthly"), onClick = {})
        ElectronRadioButton(RadioButtonUiModel.Default(isSelected = false, label = "Yearly"), onClick = {})
        ElectronRadioButton(RadioButtonUiModel.Default(isSelected = false, label = "Lifetime", isEnabled = false), onClick = {})
    }
}
