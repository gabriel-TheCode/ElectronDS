package com.electron.designsystem.components.inputfield.primitives

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

private data class SupportingState(val text: String?, val isError: Boolean)

/**
 * Text field block: static label above, 48dp field, supporting line below.
 *
 * Why not Material's OutlinedTextField: its floating label shrinks into the
 * border and competes with the value, and its 56dp height never lines up
 * with a 48dp button. Here the label is always readable, the field is the
 * same height and radius as a Large button, and state is carried by the
 * border alone (1dp idle, 2dp brand when focused, error color on error).
 *
 * Label, value and supporting text all live in the decoration box, so they
 * form one accessibility node and tapping the label focuses the field.
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
    val c = ElectronTheme.colors
    val typography = ElectronTheme.typography
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = when {
            !isEnabled -> Color.Transparent
            isError -> c.status.error
            isFocused -> c.border.focus
            isReadOnly -> c.border.subtle
            else -> c.border.default
        },
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "fieldBorder"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isFocused && isEnabled) ElectronDimens.borderWidthFocus else ElectronDimens.borderWidth,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "fieldBorderWidth"
    )
    // Three distinct looks: editable (white field, border), read-only (no fill,
    // hairline: a value you can read and copy), disabled (grey fill, no border).
    val containerColor = when {
        !isEnabled -> c.interaction.disabledBackground
        isReadOnly -> Color.Transparent
        else -> c.background.surface
    }
    val textColor = if (isEnabled) c.content.primary else c.interaction.disabledContent

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = isEnabled,
        readOnly = isReadOnly,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        textStyle = typography.bodyLarge.copy(color = textColor),
        cursorBrush = SolidColor(c.brand.primary),
        modifier = modifier
            .fillMaxWidth()
            .semantics { if (isError && supportingText != null) error(supportingText) }
            .testTag(testTag),
        decorationBox = { innerTextField ->
            Column {
                if (label != null) {
                    Text(
                        text = label,
                        style = typography.labelLarge,
                        color = if (isEnabled) c.content.secondary else c.content.disabled
                    )
                    Spacer(modifier = Modifier.height(ElectronSpacing.sm))
                }
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = ElectronDimens.fieldHeight)
                        .background(containerColor, ElectronShapes.field)
                        .border(borderWidth, borderColor, ElectronShapes.field)
                        .padding(horizontal = ElectronSpacing.lg, vertical = ElectronSpacing.sm)
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(text = placeholder, style = typography.bodyLarge, color = c.content.muted)
                    }
                    innerTextField()
                }
                AnimatedContent(
                    targetState = SupportingState(supportingText, isError),
                    transitionSpec = {
                        fadeIn(tween(ElectronMotion.quick, easing = ElectronMotion.easeEnter)) togetherWith
                            fadeOut(tween(ElectronMotion.instant, easing = ElectronMotion.easeExit)) using
                            SizeTransform(clip = false)
                    },
                    label = "fieldSupporting"
                ) { state ->
                    if (state.text != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs),
                            modifier = Modifier.padding(top = ElectronSpacing.sm)
                        ) {
                            if (state.isError) {
                                Icon(
                                    imageVector = Icons.Outlined.ErrorOutline,
                                    contentDescription = null,
                                    tint = c.status.error,
                                    modifier = Modifier.size(ElectronDimens.iconSm)
                                )
                            }
                            Text(
                                text = state.text,
                                style = typography.bodySmall,
                                color = if (state.isError) c.status.error else c.content.muted
                            )
                        }
                    }
                }
            }
        }
    )
}
