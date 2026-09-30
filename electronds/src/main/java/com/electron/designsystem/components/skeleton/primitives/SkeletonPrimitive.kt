package com.electron.designsystem.components.skeleton.primitives

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes

internal data class SkeletonColors(val base: Color, val highlight: Color)

/**
 * Shimmer phase: one clock per placeholder, shared by all of its shapes, so
 * the lines of a list row sweep together. Placeholders composed at the same
 * time start in phase, so a column of rows sweeps as one.
 */
@Composable
internal fun rememberShimmerPhase(): State<Float> =
    rememberInfiniteTransition(label = "skeleton").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(ElectronMotion.ambient, easing = LinearEasing), RepeatMode.Restart),
        label = "shimmerPhase"
    )

/**
 * Paints a placeholder shape with a slow, low-contrast highlight sweeping
 * left to right. The phase is read at draw time, so the shimmer never
 * recomposes the layout.
 */
internal fun Modifier.skeletonShape(shape: Shape, colors: SkeletonColors, phase: State<Float>): Modifier =
    drawWithCache {
        val outline = shape.createOutline(size, layoutDirection, this)
        onDrawBehind {
            val band = size.width.coerceAtLeast(1f)
            val start = -band + phase.value * (size.width + band * 2)
            drawOutline(
                outline = outline,
                brush = Brush.linearGradient(
                    colors = listOf(colors.base, colors.highlight, colors.base),
                    start = Offset(start, 0f),
                    end = Offset(start + band, 0f)
                )
            )
        }
    }

/** Replaces a group's semantics with one optional announcement. */
internal fun Modifier.skeletonSemantics(contentDescription: String?): Modifier =
    clearAndSetSemantics { if (contentDescription != null) this.contentDescription = contentDescription }

/**
 * One text line placeholder: occupies exactly the line height of [style]
 * (so it replaces the text 1:1) and draws a bar centered in it, half the
 * line height thick (never thinner than [ElectronDimens.skeletonLine]).
 */
@Composable
internal fun SkeletonLine(
    style: TextStyle,
    widthFraction: Float,
    colors: SkeletonColors,
    phase: State<Float>,
    modifier: Modifier = Modifier
) {
    val lineHeight: Dp = with(LocalDensity.current) { style.lineHeight.toDp() }
    val barHeight = max(ElectronDimens.skeletonLine, lineHeight / 2)
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(widthFraction)
                .height(barHeight)
                .skeletonShape(ElectronShapes.tag, colors, phase)
        )
    }
}
