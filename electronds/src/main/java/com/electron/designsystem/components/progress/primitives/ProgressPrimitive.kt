package com.electron.designsystem.components.progress.primitives

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.tokens.ElectronDimens

private fun Modifier.progressSemantics(contentDescription: String?): Modifier =
    if (contentDescription != null) semantics { this.contentDescription = contentDescription } else this

/** Full-width bar. A null [progress] renders the indeterminate animation. */
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
            strokeCap = StrokeCap.Round
        )
    } else {
        LinearProgressIndicator(
            progress = { progress },
            modifier = sizing,
            color = color,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round
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
        CircularProgressIndicator(
            progress = { progress },
            modifier = sizing,
            color = color,
            strokeWidth = ElectronDimens.progressStrokeWidth,
            trackColor = trackColor,
            strokeCap = StrokeCap.Round
        )
    }
}
