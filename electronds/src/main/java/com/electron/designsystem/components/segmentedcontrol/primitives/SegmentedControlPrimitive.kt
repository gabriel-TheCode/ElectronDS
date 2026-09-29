package com.electron.designsystem.components.segmentedcontrol.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.focusRing
import com.electron.designsystem.utils.pressScale

internal data class SegmentColors(
    val track: Color,
    val indicator: Color,
    val selectedContent: Color,
    val content: Color
)

/**
 * Sunken track with one raised indicator that slides to the selected
 * segment. A single moving indicator explains the change ("the value moved
 * here") better than two segments swapping backgrounds, and it is the only
 * spring in the system: a position change is physical, a color change is
 * not. The indicator radius is concentric with the track.
 */
@Composable
internal fun SegmentedControlPrimitive(
    options: List<String>,
    selectedIndex: Int,
    isEnabled: Boolean,
    colors: SegmentColors,
    textStyle: TextStyle,
    testTag: String,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (options.isEmpty()) return
    val safeIndex = selectedIndex.coerceIn(0, options.lastIndex)

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(ElectronDimens.controlHeightMd)
            .background(colors.track, ElectronShapes.control)
            .padding(ElectronSpacing.xxs)
            .testTag(testTag)
    ) {
        val gap = ElectronSpacing.xxs
        val segmentWidth = (maxWidth - gap * (options.size - 1)) / options.size
        val indicatorOffset by animateDpAsState(
            targetValue = (segmentWidth + gap) * safeIndex,
            animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow),
            label = "segmentIndicator"
        )

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(segmentWidth)
                .fillMaxHeight()
                .shadow(ElectronElevation.raised, ElectronShapes.segment)
                .background(colors.indicator, ElectronShapes.segment)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(gap),
            modifier = Modifier
                .fillMaxSize()
                .selectableGroup()
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = index == safeIndex
                val interactionSource = remember { MutableInteractionSource() }
                val content by animateColorAsState(
                    targetValue = if (isSelected) colors.selectedContent else colors.content,
                    animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
                    label = "segmentContent"
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .pressScale(interactionSource, enabled = isEnabled)
                        .clip(ElectronShapes.segment)
                        .focusRing(interactionSource, ElectronShapes.segment)
                        .selectable(
                            selected = isSelected,
                            enabled = isEnabled,
                            role = Role.Tab,
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onOptionSelected(index) }
                        )
                        .padding(horizontal = ElectronSpacing.sm)
                        .testTag("${testTag}_$index")
                ) {
                    Text(
                        text = option,
                        style = textStyle,
                        color = content,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
