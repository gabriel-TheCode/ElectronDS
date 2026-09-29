package com.electron.designsystem.components.metriccard.primitives

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Metric content block: header (icon + label), key figure with unit,
 * then an optional footer slot. The surrounding card is added by the
 * variant.
 *
 * Rhythm: label and figure are one unit (xs apart), the footer is context
 * and sits clearly below (md). When the figure changes it rolls in from
 * below, so a new reading is noticed without a count-up gimmick.
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
        modifier = modifier
            .fillMaxWidth()
            .padding(ElectronSpacing.lg)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs)
        ) {
            leading?.invoke()
            Text(
                text = label,
                style = labelStyle,
                color = labelColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(ElectronSpacing.xs))
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs)) {
            AnimatedContent(
                targetState = value,
                transitionSpec = {
                    (slideInVertically(tween(ElectronMotion.standard, easing = ElectronMotion.easeEnter)) { it / 2 } +
                        fadeIn(tween(ElectronMotion.standard))) togetherWith
                        (slideOutVertically(tween(ElectronMotion.quick, easing = ElectronMotion.easeExit)) { -it / 2 } +
                            fadeOut(tween(ElectronMotion.quick)))
                },
                label = "metricValue",
                modifier = Modifier.alignByBaseline()
            ) { figure ->
                Text(text = figure, style = valueStyle, color = valueColor, maxLines = 1)
            }
            if (unit != null) {
                Text(
                    text = unit,
                    style = unitStyle,
                    color = unitColor,
                    modifier = Modifier.alignByBaseline()
                )
            }
        }
        if (footer != null) {
            Spacer(modifier = Modifier.height(ElectronSpacing.md))
            Column(
                verticalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
                content = footer
            )
        }
    }
}
