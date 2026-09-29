package com.electron.designsystem.components.metriccard.models

import androidx.compose.runtime.Immutable
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.progress.models.ProgressTone

/** Direction of a metric change. */
public enum class MetricTrend { Up, Down, Flat }

/**
 * Whether the change is good news. Decoupled from [MetricTrend] because
 * "up" is positive for revenue but negative for consumption.
 */
public enum class MetricSentiment { Positive, Negative, Neutral }

@Immutable
public data class MetricDelta(
    val text: String,
    val trend: MetricTrend,
    val sentiment: MetricSentiment = MetricSentiment.Neutral
)

/**
 * Metric card UI models: a labelled key figure rendered in the data
 * typeface, for dashboards and instrument panels.
 */
public sealed class MetricCardUiModel {

    /** Key figure with an optional change indicator. */
    @Immutable
    public data class Default(
        val label: String,
        val value: String,
        val unit: String? = null,
        val icon: IconUiModel.Default? = null,
        val delta: MetricDelta? = null,
        val caption: String? = null,
        val testTag: String = "electron_metric_card"
    ) : MetricCardUiModel()

    /** Key figure measured against a target, with a progress bar. */
    @Immutable
    public data class Progress(
        val label: String,
        val value: String,
        val progress: Float,
        val unit: String? = null,
        val icon: IconUiModel.Default? = null,
        val tone: ProgressTone = ProgressTone.Brand,
        val caption: String? = null,
        val testTag: String = "electron_metric_card_progress"
    ) : MetricCardUiModel()
}
