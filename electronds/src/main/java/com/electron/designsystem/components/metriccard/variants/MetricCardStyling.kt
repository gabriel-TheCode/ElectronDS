package com.electron.designsystem.components.metriccard.variants

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.TrendingDown
import androidx.compose.material.icons.automirrored.outlined.TrendingFlat
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.icon.ElectronIcon
import com.electron.designsystem.components.icon.models.IconSize
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.metriccard.models.MetricSentiment
import com.electron.designsystem.components.metriccard.models.MetricTrend
import com.electron.designsystem.components.metriccard.primitives.MetricPrimitive
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.foundation.ElectronTheme

internal fun MetricTrend.icon(): ImageVector = when (this) {
    MetricTrend.Up -> Icons.AutoMirrored.Outlined.TrendingUp
    MetricTrend.Down -> Icons.AutoMirrored.Outlined.TrendingDown
    MetricTrend.Flat -> Icons.AutoMirrored.Outlined.TrendingFlat
}

internal fun MetricSentiment.tagTone(): TagTone = when (this) {
    MetricSentiment.Positive -> TagTone.Success
    MetricSentiment.Negative -> TagTone.Error
    MetricSentiment.Neutral -> TagTone.Neutral
}

/**
 * Shared frame of every metric card: an [ElectronCard] wrapping the
 * metric block, with an optional caption under the variant footer.
 */
@Composable
internal fun MetricCardFrame(
    label: String,
    value: String,
    unit: String?,
    icon: IconUiModel.Default?,
    caption: String?,
    testTag: String,
    modifier: Modifier,
    footer: (@Composable ColumnScope.() -> Unit)? = null
) {
    val c = ElectronTheme.colors
    val typography = ElectronTheme.typography
    ElectronCard(
        uiModel = CardUiModel.Default(testTag = testTag),
        modifier = modifier
    ) {
        MetricPrimitive(
            label = label,
            labelStyle = typography.labelMedium,
            labelColor = c.content.secondary,
            value = value,
            valueStyle = typography.dataDisplay,
            valueColor = c.content.primary,
            unit = unit,
            unitStyle = typography.titleMedium,
            unitColor = c.content.secondary,
            leading = if (icon != null) {
                { ElectronIcon(icon.copy(size = IconSize.Sm)) }
            } else {
                null
            },
            footer = if (footer != null || caption != null) {
                {
                    footer?.invoke(this)
                    if (caption != null) {
                        Text(text = caption, style = typography.bodySmall, color = c.content.muted)
                    }
                }
            } else {
                null
            }
        )
    }
}
