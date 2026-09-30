package com.electron.designsystem.components.button.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.focusRing
import com.electron.designsystem.utils.pressScale

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
 *
 * Feedback: colors cross-fade when the state changes (Default to Success
 * after a save reads as a transformation, not a flash), the button scales
 * down under the finger and shows a focus ring for keyboard and TV.
 *
 * The icon always takes the button's content color (the model's tone is
 * ignored here): an icon and its label are one action and must never be
 * two colors. An icon-only button is square (width = height, no padding)
 * instead of inheriting the text padding and looking stretched.
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
    val interactionSource = remember { MutableInteractionSource() }
    val isInteractive = isEnabled && !isLoading
    val isIconOnly = text.isNullOrEmpty() && icon != null && !isLoading
    val widthModifier = when {
        isFullWidth -> modifier.fillMaxWidth()
        isIconOnly -> modifier.widthIn(min = height)
        else -> modifier
    }

    val background by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "buttonBackground"
    )
    val content by animateColorAsState(
        targetValue = contentColor,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "buttonContent"
    )

    Button(
        onClick = onClick,
        enabled = isInteractive,
        shape = ElectronShapes.control,
        border = borderColor?.let { BorderStroke(ElectronDimens.borderWidth, it) },
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            contentColor = content,
            disabledContainerColor = background,
            disabledContentColor = content
        ),
        elevation = null,
        contentPadding = if (isIconOnly) PaddingValues(ElectronSpacing.none) else contentPadding,
        interactionSource = interactionSource,
        modifier = widthModifier
            .heightIn(min = height)
            .pressScale(interactionSource, enabled = isInteractive)
            .focusRing(interactionSource, ElectronShapes.control)
            .testTag(testTag)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = content,
                strokeWidth = ElectronDimens.borderWidthFocus,
                modifier = Modifier.size(ElectronDimens.iconMd)
            )
            if (!text.isNullOrEmpty()) {
                Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            }
        } else if (icon != null) {
            Icon(
                imageVector = icon.imageVector,
                contentDescription = icon.contentDescription,
                tint = content,
                modifier = Modifier.size(ElectronDimens.iconMd)
            )
            if (!text.isNullOrEmpty()) {
                Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            }
        }
        if (!text.isNullOrEmpty()) {
            Text(
                text = text,
                style = ElectronTheme.typography.labelLarge,
                textDecoration = if (isUnderlined) TextDecoration.Underline else TextDecoration.None,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
