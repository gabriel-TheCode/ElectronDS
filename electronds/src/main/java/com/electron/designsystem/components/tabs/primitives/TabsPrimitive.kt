package com.electron.designsystem.components.tabs.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import kotlin.math.min
import kotlin.math.roundToInt

internal data class TabColors(
    val indicator: Color,
    val divider: Color,
    val selectedContent: Color,
    val content: Color
)

private enum class TabSlot { Tabs, Indicator }

/** Tab start offsets from the last layout, used to scroll the selection into view. */
private class TabStarts {
    var values: IntArray = IntArray(0)
}

/**
 * Tab row: labels over a hairline divider, with one brand indicator that
 * underlines the selected label and slides to a new selection on the same
 * spring as the segmented control.
 *
 * Tabs are measured first and the indicator is placed from those
 * measurements in the same layout pass, so it is there on the first frame.
 * The animated value is the selected index, interpolated between two tabs,
 * so a size change re-places the indicator instead of sliding it.
 */
@Composable
internal fun TabsPrimitive(
    labels: List<String>,
    selectedIndex: Int,
    isScrollable: Boolean,
    colors: TabColors,
    textStyle: TextStyle,
    testTag: String,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    trailing: (@Composable (index: Int) -> Unit)? = null
) {
    if (labels.isEmpty()) return
    val lastIndex = labels.lastIndex
    val safeIndex = selectedIndex.coerceIn(0, lastIndex)
    val animatedIndex = animateFloatAsState(
        targetValue = safeIndex.toFloat(),
        animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow),
        label = "tabIndicator"
    )
    val scrollState = rememberScrollState()
    val tabStarts = remember { TabStarts() }
    val density = LocalDensity.current

    if (isScrollable) {
        LaunchedEffect(safeIndex) {
            withFrameNanos { } // wait for a layout so the tab offsets are known
            val start = tabStarts.values.getOrNull(safeIndex) ?: return@LaunchedEffect
            val edge = with(density) { ElectronSpacing.xl.roundToPx() }
            scrollState.animateScrollTo((start - edge).coerceAtLeast(0))
        }
    }

    Box(modifier = modifier.fillMaxWidth().testTag(testTag)) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .height(ElectronDimens.separatorHeight)
                .background(colors.divider)
        )
        SubcomposeLayout(
            modifier = (if (isScrollable) Modifier.horizontalScroll(scrollState) else Modifier.fillMaxWidth())
                .selectableGroup()
        ) { constraints ->
            val tabMeasurables = subcompose(TabSlot.Tabs) {
                labels.forEachIndexed { index, label ->
                    TabCell(
                        label = label,
                        isSelected = index == safeIndex,
                        colors = colors,
                        textStyle = textStyle,
                        testTag = "${testTag}_$index",
                        onClick = { onTabSelected(index) },
                        trailing = if (trailing != null) {
                            { trailing(index) }
                        } else {
                            null
                        }
                    )
                }
            }
            val tabConstraints = if (isScrollable) {
                Constraints(maxHeight = constraints.maxHeight)
            } else {
                val cellWidth = constraints.maxWidth / labels.size
                Constraints(minWidth = cellWidth, maxWidth = cellWidth, maxHeight = constraints.maxHeight)
            }
            val placeables = tabMeasurables.map { it.measure(tabConstraints) }
            val height = placeables.maxOf { it.height }
            val starts = IntArray(placeables.size)
            var x = 0
            placeables.forEachIndexed { index, placeable ->
                starts[index] = x
                x += placeable.width
            }
            tabStarts.values = starts
            val width = if (isScrollable) x else constraints.maxWidth

            // Indicator: under the tab content (cell minus its horizontal padding,
            // which is the label itself for scrollable tabs), interpolated
            // between the two tabs around the animated index.
            val position = animatedIndex.value.coerceIn(0f, lastIndex.toFloat())
            val from = position.toInt()
            val to = min(from + 1, lastIndex)
            val fraction = position - from
            fun lerp(start: Int, stop: Int): Int = (start + (stop - start) * fraction).roundToInt()
            val inset = ElectronSpacing.lg.roundToPx()
            val indicatorX = lerp(starts[from], starts[to]) + inset
            val indicatorWidth = (lerp(placeables[from].width, placeables[to].width) - inset * 2).coerceAtLeast(0)
            val indicatorHeight = ElectronDimens.tabIndicatorHeight.roundToPx()
            val indicator = subcompose(TabSlot.Indicator) {
                Box(modifier = Modifier.background(colors.indicator, ElectronShapes.indicator))
            }.first().measure(Constraints.fixed(indicatorWidth, indicatorHeight))

            layout(width, height) {
                placeables.forEachIndexed { index, placeable -> placeable.placeRelative(starts[index], 0) }
                indicator.placeRelative(indicatorX, height - indicatorHeight)
            }
        }
    }
}

@Composable
private fun TabCell(
    label: String,
    isSelected: Boolean,
    colors: TabColors,
    textStyle: TextStyle,
    testTag: String,
    onClick: () -> Unit,
    trailing: (@Composable () -> Unit)?
) {
    val interactionSource = remember { MutableInteractionSource() }
    val content by animateColorAsState(
        targetValue = if (isSelected) colors.selectedContent else colors.content,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "tabContent"
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs, Alignment.CenterHorizontally),
        modifier = Modifier
            .height(ElectronDimens.tabHeight)
            .clip(ElectronShapes.control)
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Tab,
                onClick = onClick
            )
            .padding(horizontal = ElectronSpacing.lg)
            .testTag(testTag)
    ) {
        Text(
            text = label,
            style = textStyle,
            color = content,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        trailing?.invoke()
    }
}
