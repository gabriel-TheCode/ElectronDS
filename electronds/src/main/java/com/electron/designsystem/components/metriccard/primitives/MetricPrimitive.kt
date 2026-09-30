package com.electron.designsystem.components.metriccard.primitives

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
 * below (see [RollingFigure]), so a new reading is noticed without a
 * count-up gimmick.
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
            RollingFigure(
                value = value,
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
        if (footer != null) {
            Spacer(modifier = Modifier.height(ElectronSpacing.md))
            Column(
                verticalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
                content = footer
            )
        }
    }
}

/** Share of the figure's height travelled by the roll. */
private const val RollDistance = 0.35f

/**
 * Figure that rolls to a new value: the old one rises and fades out while
 * the new one rises into place, on one shared timeline.
 *
 * The motion is drawn with graphicsLayer only, never with layout offsets:
 * both figures stay placed at the same spot, so the row's baseline (and the
 * unit aligned to it) never moves, and nothing is clipped mid-roll. The
 * width follows the new figure from the first frame; the outgoing one may
 * overflow it while it fades.
 */
@Composable
private fun RollingFigure(
    value: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier
) {
    var current by remember { mutableStateOf(value) }
    var previous by remember { mutableStateOf<String?>(null) }
    val progress = remember { Animatable(1f) }

    LaunchedEffect(value) {
        if (value == current) return@LaunchedEffect
        previous = current
        current = value
        progress.snapTo(0f)
        progress.animateTo(1f, tween(ElectronMotion.standard, easing = ElectronMotion.easeStandard))
        previous = null
    }

    Box(modifier = modifier) {
        previous?.let { outgoing ->
            Text(
                text = outgoing,
                style = style,
                color = color,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier
                    .matchParentSize()
                    .wrapContentWidth(align = Alignment.Start, unbounded = true)
                    .graphicsLayer {
                        val p = progress.value
                        alpha = (1f - p * 2f).coerceIn(0f, 1f)
                        translationY = -size.height * RollDistance * p
                    }
            )
        }
        Text(
            text = current,
            style = style,
            color = color,
            maxLines = 1,
            softWrap = false,
            modifier = Modifier.graphicsLayer {
                val p = progress.value
                alpha = p
                translationY = size.height * RollDistance * (1f - p)
            }
        )
    }
}
