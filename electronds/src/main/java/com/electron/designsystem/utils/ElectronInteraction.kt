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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Shared press feedback for tappable Electron controls, so a button, a
 * chip and the FAB answer a touch the same way: a 3% scale-down on a
 * critically damped spring. It confirms the touch under the finger (the
 * ripple alone is often hidden by it) and settles without overshoot.
 *
 * Focus keeps Material's own indication (a soft state layer clipped to the
 * control's shape): no extra outline is drawn on top of the components.
 */
internal object ElectronInteraction {
    const val PressedScale: Float = 0.97f

    /**
     * Breathing room of a pressed/focused highlight past content that sits
     * flush with the screen gutter (radio and checkbox rows, links).
     */
    val HighlightOutset: Dp = ElectronSpacing.sm
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

/**
 * Lets a row's highlight overflow [ElectronInteraction.HighlightOutset] on
 * both sides without moving its content: the row is measured that much
 * wider on each side but reports its original width, so the extra space
 * spills into the surrounding gutter. Pair it with the same horizontal
 * padding inside the clickable area.
 */
internal fun Modifier.highlightOutset(): Modifier = layout { measurable, constraints ->
    val px = ElectronInteraction.HighlightOutset.roundToPx()
    val placeable = measurable.measure(
        constraints.copy(
            minWidth = constraints.minWidth + 2 * px,
            maxWidth = if (constraints.hasBoundedWidth) constraints.maxWidth + 2 * px else Constraints.Infinity
        )
    )
    layout(placeable.width - 2 * px, placeable.height) {
        placeable.place(-px, 0)
    }
}
