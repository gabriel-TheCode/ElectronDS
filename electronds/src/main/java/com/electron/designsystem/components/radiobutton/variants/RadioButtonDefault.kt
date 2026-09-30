package com.electron.designsystem.components.radiobutton.variants

import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.radiobutton.models.RadioButtonUiModel
import com.electron.designsystem.components.radiobutton.primitives.RadioButtonPrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun electronRadioButtonColors(): RadioButtonColors {
    val c = ElectronTheme.colors
    return RadioButtonDefaults.colors(
        selectedColor = c.brand.primary,
        unselectedColor = c.border.strong,
        disabledSelectedColor = c.interaction.disabledContent,
        disabledUnselectedColor = c.interaction.disabledContent
    )
}

@Composable
internal fun RadioButtonDefault(
    uiModel: RadioButtonUiModel.Default,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = ElectronTheme.colors
    RadioButtonPrimitive(
        isSelected = uiModel.isSelected,
        isEnabled = uiModel.isEnabled,
        colors = electronRadioButtonColors(),
        label = uiModel.label,
        labelStyle = ElectronTheme.typography.bodyLarge,
        labelColor = if (uiModel.isEnabled) c.content.primary else c.content.disabled,
        testTag = uiModel.testTag,
        onClick = onClick,
        modifier = modifier
    )
}
