package com.electron.designsystem.components.metriccard.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.metriccard.models.MetricCardUiModel
import com.electron.designsystem.components.progress.ElectronProgressIndicator
import com.electron.designsystem.components.progress.models.ProgressUiModel

/** Key figure measured against a target: linear progress bar footer. */
@Composable
internal fun MetricCardProgress(
    uiModel: MetricCardUiModel.Progress,
    modifier: Modifier = Modifier
) {
    MetricCardFrame(
        label = uiModel.label,
        value = uiModel.value,
        unit = uiModel.unit,
        icon = uiModel.icon,
        caption = uiModel.caption,
        testTag = uiModel.testTag,
        modifier = modifier,
        footer = {
            ElectronProgressIndicator(
                ProgressUiModel.Linear(progress = uiModel.progress, tone = uiModel.tone)
            )
        }
    )
}
