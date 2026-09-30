package com.electron.designsystem.components.slider.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.slider.models.SliderUiModel

@Composable
internal fun SliderContinuous(
    uiModel: SliderUiModel.Continuous,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    SliderRender(
        value = uiModel.value,
        valueRangeStart = uiModel.valueRangeStart,
        valueRangeEnd = uiModel.valueRangeEnd,
        steps = 0,
        label = uiModel.label,
        valueText = uiModel.valueText,
        isEnabled = uiModel.isEnabled,
        testTag = uiModel.testTag,
        onValueChange = onValueChange,
        onValueChangeFinished = onValueChangeFinished,
        modifier = modifier
    )
}
