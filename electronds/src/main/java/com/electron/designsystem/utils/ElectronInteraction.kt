package com.electron.designsystem.utils

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Shared press feedback for every tappable Electron control, so a button,
 * a chip and a segment answer a touch the same way: a 3% scale-down on a
 * critically damped spring. It confirms the touch under the finger (the
 * ripple alone is often hidden by it) and settles without overshoot.
 *
 * Focus keeps Material's own indication (a soft state layer clipped to the
 * control's shape): no extra outline is drawn on top of the components.
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
