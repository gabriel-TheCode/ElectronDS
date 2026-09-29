package com.electron.designsystem.components.metriccard.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.metriccard.models.MetricCardUiModel
import com.electron.designsystem.components.tag.ElectronTag
import com.electron.designsystem.components.tag.models.TagSize
import com.electron.designsystem.components.tag.models.TagUiModel

/** Key figure with an optional change tag (trend icon + delta text). */
@Composable
internal fun MetricCardDefault(
    uiModel: MetricCardUiModel.Default,
    modifier: Modifier = Modifier
) {
    val delta = uiModel.delta
    MetricCardFrame(
        label = uiModel.label,
        value = uiModel.value,
        unit = uiModel.unit,
        icon = uiModel.icon,
        caption = uiModel.caption,
        testTag = uiModel.testTag,
        modifier = modifier,
        footer = if (delta != null) {
            {
                ElectronTag(
                    TagUiModel.Text(
                        text = delta.text,
                        size = TagSize.Sm,
                        tone = delta.sentiment.tagTone(),
                        leadingIcon = delta.trend.icon()
                    )
                )
            }
        } else {
            null
        }
    )
}
