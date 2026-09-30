package com.electron.designsystem.components.slider

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.slider.models.SliderUiModel
import com.electron.designsystem.components.slider.variants.SliderContinuous
import com.electron.designsystem.components.slider.variants.SliderStepped
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronSlider
 *
 * Purpose: pick a value on a range where the relative position matters
 * more than the exact number (charge limit, power, volume). For an exact
 * number, use ElectronInputField.
 *
 * API:
 * - [uiModel]: variant and value (sealed [SliderUiModel]).
 * - [onValueChange]: signal emitted continuously while dragging, with the new value.
 * - [onValueChangeFinished]: signal emitted once the drag or key press ends
 *   (commit expensive work here, not in onValueChange).
 *
 * Usage:
 * ```
 * ElectronSlider(
 *     uiModel = SliderUiModel.Stepped(value = uiState.chargeLimit, steps = 3, valueRangeStart = 0.6f, valueRangeEnd = 1f,
 *         label = "Charge limit", valueText = "${(uiState.chargeLimit * 100).toInt()}%"),
 *     onValueChange = viewModel::onChargeLimitChanged,
 *     onValueChangeFinished = viewModel::onChargeLimitCommitted
 * )
 * ```
 */
@Composable
public fun ElectronSlider(
    uiModel: SliderUiModel,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    onValueChangeFinished: () -> Unit = {}
) {
    when (uiModel) {
        is SliderUiModel.Continuous -> SliderContinuous(uiModel, onValueChange, onValueChangeFinished, modifier)
        is SliderUiModel.Stepped -> SliderStepped(uiModel, onValueChange, onValueChangeFinished, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronSliderPreview() {
    ElectronPreviewSurface {
        ElectronSlider(SliderUiModel.Continuous(value = 0.4f, label = "Max power", valueText = "4.4 kW"), onValueChange = {})
        ElectronSlider(
            SliderUiModel.Stepped(value = 0.8f, steps = 3, valueRangeStart = 0.6f, valueRangeEnd = 1f, label = "Charge limit", valueText = "80%"),
            onValueChange = {}
        )
        ElectronSlider(SliderUiModel.Continuous(value = 0.7f, label = "Disabled", isEnabled = false), onValueChange = {})
    }
}
