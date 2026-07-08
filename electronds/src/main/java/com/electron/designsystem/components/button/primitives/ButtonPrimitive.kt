package com.electron.designsystem.components.button.primitives

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.icon.ElectronIcon
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Single button primitive.
 *
 * A deliberate simplification of the legacy design, which fanned out into
 * eight permutation composables (icon/no icon x text button/filled x ...).
 * One primitive with optional slots and resolved colors covers every variant
 * and keeps the render path in one place.
 *
 * Stateless: it does not decide colors, sizes or meaning. Disabled state is
 * delegated to Material's `enabled` so semantics and ripple behave correctly
 * (the legacy code swallowed onClick instead, which kept the button looking
 * and announcing as enabled to accessibility services).
 */
@Composable
internal fun ButtonPrimitive(
    text: String?,
    icon: IconUiModel.Default?,
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color?,
    height: Dp,
    contentPadding: PaddingValues,
    isEnabled: Boolean,
    isFullWidth: Boolean,
    isUnderlined: Boolean,
    isLoading: Boolean,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val widthModifier = if (isFullWidth) modifier.fillMaxWidth() else modifier

    Button(
        onClick = onClick,
        enabled = isEnabled && !isLoading,
        shape = ElectronShapes.pill,
        border = borderColor?.let { BorderStroke(ElectronDimens.borderWidth, it) },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = backgroundColor,
            disabledContentColor = contentColor
        ),
        elevation = null,
        contentPadding = contentPadding,
        modifier = widthModifier
            .heightIn(min = height)
            .testTag(testTag)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = ElectronDimens.borderWidthFocus,
                modifier = Modifier.size(ElectronDimens.iconMd)
            )
            if (!text.isNullOrEmpty()) {
                Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            }
        } else if (icon != null) {
            ElectronIcon(uiModel = icon)
            if (!text.isNullOrEmpty()) {
                Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            }
        }
        if (!text.isNullOrEmpty()) {
            Text(
                text = text,
                style = ElectronTheme.typography.labelLarge,
                textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None
            )
        }
    }
}
