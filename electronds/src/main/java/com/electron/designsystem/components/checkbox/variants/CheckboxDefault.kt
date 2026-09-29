package com.electron.designsystem.components.checkbox.variants

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.checkbox.models.CheckboxUiModel
import com.electron.designsystem.components.checkbox.primitives.CheckboxPrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun electronCheckboxColors(isError: Boolean): CheckboxColors {
    val c = ElectronTheme.colors
    val accent = if (isError) c.status.error else c.brand.primary
    return CheckboxDefaults.colors(
        checkedColor = accent,
        uncheckedColor = if (isError) c.status.error else c.border.strong,
        checkmarkColor = c.brand.onPrimary,
        disabledCheckedColor = c.interaction.disabledContent,
        disabledUncheckedColor = c.interaction.disabledContent
    )
}

@Composable
internal fun CheckboxDefault(
    uiModel: CheckboxUiModel.Default,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val c = ElectronTheme.colors
    CheckboxPrimitive(
        isChecked = uiModel.isChecked,
        isEnabled = uiModel.isEnabled,
        colors = electronCheckboxColors(uiModel.isError),
        label = uiModel.label,
        labelStyle = ElectronTheme.typography.bodyMedium,
        labelColor = if (uiModel.isEnabled) c.content.primary else c.content.disabled,
        testTag = uiModel.testTag,
        onCheckedChange = onCheckedChange,
        modifier = modifier
    )
}
