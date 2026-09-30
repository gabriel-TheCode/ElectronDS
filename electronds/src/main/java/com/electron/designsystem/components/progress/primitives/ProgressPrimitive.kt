package com.electron.designsystem.components.progress.primitives

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

private fun Modifier.labelSemantics(contentDescription: String?): Modifier =
    if (contentDescription != null) semantics { this.contentDescription = contentDescription } else this

/**
 * Animates value updates so a jump from 40% to 70% reads as progress
 * being made rather than a redraw.
 */
@Composable
private fun animatedProgress(progress: Float): Float {
    val value by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(ElectronMotion.standard, easing = ElectronMotion.easeStandard),
        label = "progress"
    )
    return value
}

/**
 * Full-width bar. A null [progress] renders the indeterminate animation.
 *
 * Drawn by hand rather than with Material's LinearProgressIndicator, which
 * adds a gap between indicator and track and a "stop" dot at the end of the
 * track. Here the bar is one pill-shaped gauge: the track, and the indicator
 * clipped to it. A small value shows as a sliver that follows the rounded
 * start of the track, never as a detached dot.
 */
@Composable
internal fun LinearProgressPrimitive(
    progress: Float?,
    color: Color,
    trackColor: Color,
    contentDescription: String?,
    testTag: String,
    modifier: Modifier = Modifier
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val sizing = modifier
        .fillMaxWidth()
        .height(ElectronDimens.progressTrackHeight)
        .labelSemantics(contentDescription)
        .testTag(testTag)
        .clip(ElectronShapes.pill)
    if (progress == null) {
        val transition = rememberInfiniteTransition(label = "progressIndeterminate")
        val head by transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f + IndeterminateSegment,
            animationSpec = infiniteRepeatable(tween(ElectronMotion.ambient, easing = ElectronMotion.easeStandard)),
            label = "progressIndeterminateHead"
        )
        Canvas(sizing.progressSemantics()) {
            drawGauge(trackColor)
            drawIndicator(color, start = head - IndeterminateSegment, end = head, isRtl = isRtl)
        }
    } else {
        val value = animatedProgress(progress)
        Canvas(sizing.progressSemantics(progress)) {
            drawGauge(trackColor)
            drawIndicator(color, start = 0f, end = value, isRtl = isRtl)
        }
    }
}

/** Share of the track covered by the moving indeterminate segment. */
private const val IndeterminateSegment = 0.4f

private fun DrawScope.drawGauge(trackColor: Color) {
    drawRoundRect(color = trackColor, cornerRadius = CornerRadius(size.height / 2))
}

/**
 * Draws the indicator between two fractions of the track. The pill is at
 * least as wide as the track is tall, extended past the start edge when
 * needed, so the clip keeps its leading end aligned with the track curve.
 */
private fun DrawScope.drawIndicator(color: Color, start: Float, end: Float, isRtl: Boolean) {
    val left = start.coerceIn(0f, 1f) * size.width
    val right = end.coerceIn(0f, 1f) * size.width
    if (right <= left) return
    val width = maxOf(right - left, size.height)
    val x = if (start <= 0f) right - width else left
    val topLeft = Offset(if (isRtl) size.width - x - width else x, 0f)
    drawRoundRect(
        color = color,
        topLeft = topLeft,
        size = Size(width, size.height),
        cornerRadius = CornerRadius(size.height / 2)
    )
}

/** Ring indicator. A null [progress] renders the indeterminate animation. */
@Composable
internal fun CircularProgressPrimitive(
    progress: Float?,
    size: Dp,
    color: Color,
    trackColor: Color,
    contentDescription: String?,
    testTag: String,
    modifier: Modifier = Modifier
) {
    val sizing = modifier
        .size(size)
        .labelSemantics(contentDescription)
        .testTag(testTag)
    if (progress == null) {
        CircularProgressIndicator(
            modifier = sizing,
            color = color,
            strokeWidth = ElectronDimens.progressStrokeWidth,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round
        )
    } else {
        val value = animatedProgress(progress)
        CircularProgressIndicator(
            progress = { value },
            modifier = sizing,
            color = color,
            strokeWidth = ElectronDimens.progressStrokeWidth,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round,
            gapSize = ElectronSpacing.none
        )
    }
}
