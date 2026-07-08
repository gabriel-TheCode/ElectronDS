package com.electron.designsystem.components.inputfield.primitives

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes

@Composable
internal fun electronTextFieldColors(): TextFieldColors {
    val c = ElectronTheme.colors
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = c.border.focus,
        unfocusedBorderColor = c.border.default,
        disabledBorderColor = c.border.subtle,
        errorBorderColor = c.status.error,
        focusedLabelColor = c.brand.primary,
        unfocusedLabelColor = c.content.muted,
        errorLabelColor = c.status.error,
        cursorColor = c.brand.primary,
        focusedTextColor = c.content.primary,
        unfocusedTextColor = c.content.primary,
        disabledTextColor = c.interaction.disabledContent,
        focusedContainerColor = c.background.surface,
        unfocusedContainerColor = c.background.surface,
        disabledContainerColor = c.interaction.disabledBackground,
        errorContainerColor = c.background.surface
    )
}

/**
 * Outlined text field block: raw inputs, resolved theme colors, no
 * knowledge of what the field means.
 */
@Composable
internal fun InputFieldPrimitive(
    value: String,
    label: String?,
    placeholder: String?,
    supportingText: String?,
    isError: Boolean,
    isEnabled: Boolean,
    isReadOnly: Boolean,
    keyboardOptions: KeyboardOptions,
    testTag: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = ElectronTheme.colors
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it, color = colors.content.muted) } },
        supportingText = supportingText?.let {
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isError) {
                        Icon(
                            imageVector = Icons.Outlined.ErrorOutline,
                            contentDescription = null,
                            tint = colors.status.error,
                            modifier = Modifier.size(ElectronDimens.iconXs)
                        )
                        Spacer(modifier = Modifier.width(ElectronDimens.iconXs / 2))
                    }
                    Text(
                        text = it,
                        style = ElectronTheme.typography.bodySmall,
                        color = if (isError) colors.status.error else colors.content.muted
                    )
                }
            }
        },
        isError = isError,
        enabled = isEnabled,
        readOnly = isReadOnly,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        shape = ElectronShapes.field,
        colors = electronTextFieldColors(),
        textStyle = ElectronTheme.typography.bodyLarge,
        modifier = modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}
