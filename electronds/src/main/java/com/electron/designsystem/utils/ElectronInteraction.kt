package com.electron.designsystem.utils

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion

/**
 * Shared interaction feedback for every tappable Electron control, so a
 * button, a chip and a segment answer a press or a focus the same way.
 *
 * - Press: a 3% scale-down on a critically damped spring. It confirms the
 *   touch under the finger (the ripple alone is often hidden by it) and
 *   settles without overshoot.
 * - Focus: a 2dp ring in `border.focus`. Material's focus state layer is a
 *   faint tint, too weak for keyboard users and unreadable from a sofa on
 *   Android TV; the ring makes the focused element obvious.
 */
internal object ElectronInteraction {
    const val PressedScale: Float = 0.97f
}

@Composable
internal fun Modifier.pressScale(
    interactionSource: InteractionSource,
    enabled: Boolean = true
): Modifier {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enabled) ElectronInteraction.PressedScale else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium),
        label = "pressScale"
    )
    return graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

@Composable
internal fun Modifier.focusRing(
    interactionSource: InteractionSource,
    shape: Shape
): Modifier {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val color by animateColorAsState(
        targetValue = if (isFocused) ElectronTheme.colors.border.focus else Color.Transparent,
        animationSpec = tween(ElectronMotion.instant),
        label = "focusRing"
    )
    return border(width = ElectronDimens.borderWidthFocus, color = color, shape = shape)
}
