package com.electron.designsystem.components.progress.primitives

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronSpacing

private fun Modifier.progressSemantics(contentDescription: String?): Modifier =
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
 * Material 3 draws a gap between indicator and track plus a "stop" dot at
 * the end of the track; both are disabled: a continuous bar reads as one
 * gauge, which is what an instrument panel should show.
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
    val sizing = modifier
        .fillMaxWidth()
        .height(ElectronDimens.progressTrackHeight)
        .progressSemantics(contentDescription)
        .testTag(testTag)
    if (progress == null) {
        LinearProgressIndicator(
            modifier = sizing,
            color = color,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round,
            gapSize = ElectronSpacing.none
        )
    } else {
        val value = animatedProgress(progress)
        LinearProgressIndicator(
            progress = { value },
            modifier = sizing,
            color = color,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round,
            gapSize = ElectronSpacing.none,
            drawStopIndicator = {}
        )
    }
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
        .progressSemantics(contentDescription)
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
