package com.electron.designsystem.components.progress.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.progress.models.ProgressUiModel
import com.electron.designsystem.components.progress.primitives.LinearProgressPrimitive

@Composable
internal fun ProgressLinear(
    uiModel: ProgressUiModel.Linear,
    modifier: Modifier = Modifier
) {
    val colors = uiModel.tone.resolve()
    LinearProgressPrimitive(
        progress = uiModel.progress.clampProgress(),
        color = colors.indicator,
        trackColor = colors.track,
        contentDescription = uiModel.contentDescription,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
