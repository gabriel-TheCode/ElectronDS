package com.electron.designsystem.components.inputfield.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.inputfield.models.InputFieldUiModel
import com.electron.designsystem.components.inputfield.primitives.InputFieldPrimitive

/**
 * Default input field variant: chooses which supporting text to surface
 * (error wins over helper) before delegating to the primitive.
 */
@Composable
internal fun InputFieldDefault(
    uiModel: InputFieldUiModel.Default,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val supportingText = if (uiModel.isError) uiModel.errorText else uiModel.helperText
    InputFieldPrimitive(
        value = uiModel.value,
        label = uiModel.label,
        placeholder = uiModel.placeholder,
        supportingText = supportingText,
        isError = uiModel.isError,
        isEnabled = uiModel.isEnabled,
        isReadOnly = uiModel.isReadOnly,
        keyboardOptions = uiModel.keyboardOptions,
        testTag = uiModel.testTag,
        onValueChange = onValueChange,
        modifier = modifier
    )
}
