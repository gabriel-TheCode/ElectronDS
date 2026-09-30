package com.electron.designsystem.components.slider.primitives

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

internal data class SliderColors(
    val activeTrack: Color,
    val inactiveTrack: Color,
    val thumb: Color,
    val thumbRing: Color,
    val activeTick: Color,
    val inactiveTick: Color,
    val label: Color,
    val value: Color
)

/**
 * Continuous 4dp track: filled up to the value, sunken beyond. Material's
 * gap around the thumb and its stop dot are not drawn, so the slider reads
 * as the same gauge as ElectronProgressIndicator. Steps are small dots.
 */
@Composable
private fun SliderTrack(fraction: Float, steps: Int, colors: SliderColors) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(ElectronDimens.sliderTrackHeight)
    ) {
        val radius = CornerRadius(size.height / 2, size.height / 2)
        drawRoundRect(color = colors.inactiveTrack, cornerRadius = radius)
        drawRoundRect(color = colors.activeTrack, size = Size(size.width * fraction, size.height), cornerRadius = radius)
        if (steps > 0) {
            val tickRadius = ElectronDimens.sliderTick.toPx() / 2
            val intervals = steps + 1
            for (i in 1 until intervals) {
                val tickFraction = i.toFloat() / intervals
                drawCircle(
                    color = if (tickFraction <= fraction) colors.activeTick else colors.inactiveTick,
                    radius = tickRadius,
                    center = Offset(size.width * tickFraction, size.height / 2)
                )
            }
        }
    }
}

/**
 * Thumb: a raised disc with a brand ring, distinct from both halves of
 * the track. It grows while pressed or dragged (the finger covers it, the
 * growth confirms the grab).
 */
@Composable
private fun SliderThumb(isActive: Boolean, colors: SliderColors) {
    val size by animateDpAsState(
        targetValue = if (isActive) ElectronDimens.iconLg else ElectronDimens.sliderThumb,
        animationSpec = tween(ElectronMotion.instant, easing = ElectronMotion.easeStandard),
        label = "sliderThumb"
    )
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(ElectronDimens.iconLg)) {
        Box(
            modifier = Modifier
                .size(size)
                .shadow(ElectronElevation.raised, ElectronShapes.pill)
                .background(colors.thumb, ElectronShapes.pill)
                .border(ElectronDimens.borderWidthFocus, colors.thumbRing, ElectronShapes.pill)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SliderPrimitive(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int,
    isEnabled: Boolean,
    label: String?,
    valueText: String?,
    colors: SliderColors,
    labelStyle: TextStyle,
    valueStyle: TextStyle,
    testTag: String,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isDragged by interactionSource.collectIsDraggedAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val span = (valueRange.endInclusive - valueRange.start).takeIf { it > 0f } ?: 1f
    val fraction = ((value - valueRange.start) / span).coerceIn(0f, 1f)

    Column(modifier = modifier.fillMaxWidth()) {
        if (label != null || valueText != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = ElectronSpacing.xs)
            ) {
                Text(
                    text = label.orEmpty(),
                    style = labelStyle,
                    color = colors.label,
                    modifier = Modifier.weight(1f)
                )
                if (valueText != null) {
                    Text(text = valueText, style = valueStyle, color = colors.value)
                }
            }
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            enabled = isEnabled,
            onValueChangeFinished = onValueChangeFinished,
            interactionSource = interactionSource,
            steps = steps,
            valueRange = valueRange,
            thumb = { SliderThumb(isActive = isDragged || isPressed, colors = colors) },
            track = { SliderTrack(fraction = fraction, steps = steps, colors = colors) },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag)
        )
    }
}
