package com.electron.designsystem.components.metriccard.primitives

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Metric content block: header (icon + label), key figure with unit,
 * then an optional footer slot. The surrounding card is added by the
 * variant.
 */
@Composable
internal fun MetricPrimitive(
    label: String,
    labelStyle: TextStyle,
    labelColor: Color,
    value: String,
    valueStyle: TextStyle,
    valueColor: Color,
    unit: String?,
    unitStyle: TextStyle,
    unitColor: Color,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    footer: (@Composable ColumnScope.() -> Unit)? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
        modifier = modifier
            .fillMaxWidth()
            .padding(ElectronSpacing.lg)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs)
        ) {
            leading?.invoke()
            Text(text = label, style = labelStyle, color = labelColor)
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs)
        ) {
            Text(
                text = value,
                style = valueStyle,
                color = valueColor,
                modifier = Modifier.alignByBaseline()
            )
            if (unit != null) {
                Text(
                    text = unit,
                    style = unitStyle,
                    color = unitColor,
                    modifier = Modifier.alignByBaseline()
                )
            }
        }
        footer?.invoke(this)
    }
}
