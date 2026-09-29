package com.electron.designsystem.components.progress.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.progress.models.ProgressUiModel
import com.electron.designsystem.components.progress.primitives.CircularProgressPrimitive

@Composable
internal fun ProgressCircular(
    uiModel: ProgressUiModel.Circular,
    modifier: Modifier = Modifier
) {
    val colors = uiModel.tone.resolve()
    CircularProgressPrimitive(
        progress = uiModel.progress.clampProgress(),
        size = uiModel.size.resolve(),
        color = colors.indicator,
        trackColor = colors.track,
        contentDescription = uiModel.contentDescription,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
