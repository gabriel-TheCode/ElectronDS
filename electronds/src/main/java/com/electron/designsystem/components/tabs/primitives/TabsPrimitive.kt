package com.electron.designsystem.components.tabs.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.snap
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.focusRing

internal data class TabColors(
    val indicator: Color,
    val divider: Color,
    val selectedContent: Color,
    val content: Color
)

private data class TabBounds(val x: Dp, val width: Dp)

/**
 * Tab row: labels over a hairline divider, with one brand indicator that
 * slides to the selected tab. The indicator spans the label, not the whole
 * cell (it underlines what was chosen), moves on the same spring as the
 * segmented control, and snaps into place on first layout instead of
 * growing in from zero.
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
    val safeIndex = selectedIndex.coerceIn(0, labels.lastIndex)
    val density = LocalDensity.current
    val bounds = remember(labels.size) {
        mutableStateListOf<TabBounds?>().apply { repeat(labels.size) { add(null) } }
    }
    val scrollState = rememberScrollState()
    val target = bounds.getOrNull(safeIndex)
    var hasPlacedIndicator by remember { mutableStateOf(false) }
    val indicatorSpec: AnimationSpec<Dp> = if (hasPlacedIndicator) {
        spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow)
    } else {
        snap()
    }
    val indicatorX by animateDpAsState(
        targetValue = (target?.x ?: ElectronSpacing.none) + ElectronSpacing.lg,
        animationSpec = indicatorSpec,
        label = "tabIndicatorX"
    )
    val indicatorWidth by animateDpAsState(
        targetValue = ((target?.width ?: ElectronSpacing.none) - ElectronSpacing.lg * 2).coerceAtLeast(ElectronSpacing.none),
        animationSpec = indicatorSpec,
        label = "tabIndicatorWidth"
    )
    LaunchedEffect(target) {
        if (target != null) {
            hasPlacedIndicator = true
            if (isScrollable) {
                val start = with(density) { (target.x - ElectronSpacing.xl).toPx() }.toInt()
                scrollState.animateScrollTo(start.coerceAtLeast(0))
            }
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
        Box(
            modifier = if (isScrollable) {
                Modifier.horizontalScroll(scrollState)
            } else {
                Modifier.fillMaxWidth()
            }
        ) {
            Row(
                modifier = (if (isScrollable) Modifier else Modifier.fillMaxWidth()).selectableGroup()
            ) {
                labels.forEachIndexed { index, label ->
                    val isSelected = index == safeIndex
                    val interactionSource = remember { MutableInteractionSource() }
                    val content by animateColorAsState(
                        targetValue = if (isSelected) colors.selectedContent else colors.content,
                        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
                        label = "tabContent"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs, Alignment.CenterHorizontally),
                        modifier = (if (isScrollable) Modifier else Modifier.weight(1f))
                            .height(ElectronDimens.tabHeight)
                            .onPlaced { coordinates ->
                                val placed = with(density) {
                                    TabBounds(
                                        x = coordinates.positionInParent().x.toDp(),
                                        width = coordinates.size.width.toDp()
                                    )
                                }
                                // Write only on change: every write invalidates the indicator.
                                if (bounds.getOrNull(index) != placed) bounds[index] = placed
                            }
                            .clip(ElectronShapes.control)
                            .focusRing(interactionSource, ElectronShapes.control)
                            .selectable(
                                selected = isSelected,
                                interactionSource = interactionSource,
                                indication = LocalIndication.current,
                                role = Role.Tab,
                                onClick = { onTabSelected(index) }
                            )
                            .padding(horizontal = ElectronSpacing.lg)
                            .testTag("${testTag}_$index")
                    ) {
                        Text(
                            text = label,
                            style = textStyle,
                            color = content,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        trailing?.invoke(index)
                    }
                }
            }
            if (target != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .offset(x = indicatorX)
                        .width(indicatorWidth)
                        .height(ElectronDimens.tabIndicatorHeight)
                        .background(colors.indicator, ElectronShapes.indicator)
                )
            }
        }
    }
}
