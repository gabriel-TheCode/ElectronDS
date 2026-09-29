package com.electron.designsystem.components.metriccard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.metriccard.models.MetricCardUiModel
import com.electron.designsystem.components.metriccard.models.MetricDelta
import com.electron.designsystem.components.metriccard.models.MetricSentiment
import com.electron.designsystem.components.metriccard.models.MetricTrend
import com.electron.designsystem.components.metriccard.variants.MetricCardDefault
import com.electron.designsystem.components.metriccard.variants.MetricCardProgress
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronMetricCard
 *
 * Purpose: labelled key figure for dashboards. Built from ElectronCard,
 * ElectronIcon, ElectronTag and ElectronProgressIndicator. The value is
 * preformatted by the screen's mapper; the card only renders it.
 *
 * Usage:
 * ```
 * ElectronMetricCard(
 *     uiModel = MetricCardUiModel.Default(
 *         label = "Consumption",
 *         value = "342",
 *         unit = "kWh",
 *         delta = MetricDelta("-12%", MetricTrend.Down, MetricSentiment.Positive)
 *     )
 * )
 * ```
 */
@Composable
public fun ElectronMetricCard(
    uiModel: MetricCardUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is MetricCardUiModel.Default -> MetricCardDefault(uiModel, modifier)
        is MetricCardUiModel.Progress -> MetricCardProgress(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronMetricCardPreview() {
    ElectronPreviewSurface {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronMetricCard(
                uiModel = MetricCardUiModel.Default(
                    label = "Consumption",
                    value = "342",
                    unit = "kWh",
                    icon = IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand),
                    delta = MetricDelta("-12%", MetricTrend.Down, MetricSentiment.Positive),
                    caption = "vs last month"
                ),
                modifier = Modifier.weight(1f)
            )
            ElectronMetricCard(
                uiModel = MetricCardUiModel.Progress(
                    label = "Battery",
                    value = "78",
                    unit = "%",
                    progress = 0.78f,
                    tone = ProgressTone.Success,
                    icon = IconUiModel.Default(Icons.Outlined.BatteryChargingFull, tone = IconTone.Success),
                    caption = "Full in 1h 20m"
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
