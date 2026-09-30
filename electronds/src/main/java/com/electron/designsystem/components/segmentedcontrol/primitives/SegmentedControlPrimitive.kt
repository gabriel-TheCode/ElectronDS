package com.electron.designsystem.components.segmentedcontrol.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import kotlin.math.abs

internal data class SegmentColors(
    val track: Color,
    val indicator: Color,
    val selectedContent: Color,
    val content: Color
)

/**
 * Sunken track with one tinted indicator that slides to the selected
 * segment. A single moving indicator explains the change ("the value moved
 * here") better than two segments swapping backgrounds; a position change
 * is physical, so it moves on a spring. The indicator radius is concentric
 * with the track.
 *
 * Touch is one gesture for the whole control, not one click per segment:
 * - The whole height is the touch target (48dp, the 40dp track centered in
 *   it), and so are the track padding and the gaps between segments: every
 *   point maps to the nearest segment, there is no dead zone.
 * - The segment under the finger shows a pressed tint, and it follows the
 *   finger when it slides to another segment. Pressing the selected segment
 *   grabs the indicator itself, which follows the finger segment by segment.
 * - The selection is committed on release, with a light haptic tick.
 *   Sliding far off the control cancels; a vertical drag is left to the
 *   scrolling parent.
 *
 * The animated value is the displayed index, not a position: a new selection
 * slides, while a size change (rotation, split screen) re-places the
 * indicator instantly from the current width instead of sliding it.
 */
@Composable
internal fun SegmentedControlPrimitive(
    options: List<String>,
    selectedIndex: Int,
    isEnabled: Boolean,
    colors: SegmentColors,
    pressedColor: Color,
    textStyle: TextStyle,
    testTag: String,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (options.isEmpty()) return
    val safeIndex = selectedIndex.coerceIn(0, options.lastIndex)
    val currentOnOptionSelected by rememberUpdatedState(onOptionSelected)
    val haptics = LocalHapticFeedback.current
    val currentSelectedIndex by rememberUpdatedState(safeIndex)

    // Segment under the finger while pressed (null: no press, or cancelled),
    // and whether that press grabbed the indicator.
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    var isDraggingIndicator by remember { mutableStateOf(false) }
    // Last pressed segment, kept after release so the tint fades out in place.
    var pressedPosition by remember { mutableIntStateOf(0) }
    val displayedIndex = if (isDraggingIndicator) targetIndex ?: safeIndex else safeIndex

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(ElectronDimens.minTouchTarget)
            .testTag(testTag)
            .then(
                if (isEnabled) {
                    Modifier.pointerInput(options.size) {
                        val inset = ElectronSpacing.xxs.toPx()
                        val pitch = (size.width - 2 * inset + inset) / options.size
                        val cancelDistance = ElectronDimens.minTouchTarget.toPx()
                        fun indexAt(position: Offset): Int? {
                            if (position.y < -cancelDistance || position.y > size.height + cancelDistance) return null
                            return ((position.x - inset + inset / 2) / pitch).toInt().coerceIn(0, options.lastIndex)
                        }
                        awaitEachGesture {
                            val down = awaitFirstDown(requireUnconsumed = false)
                            val start = indexAt(down.position) ?: return@awaitEachGesture
                            targetIndex = start
                            pressedPosition = start
                            isDraggingIndicator = start == currentSelectedIndex
                            var ownsGesture = false
                            var released = false
                            try {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    val change = event.changes.firstOrNull { it.id == down.id } ?: break
                                    if (change.changedToUp()) {
                                        released = !change.isConsumed
                                        change.consume()
                                        break
                                    }
                                    // A scrolling parent took over the gesture: cancel.
                                    if (change.isConsumed) break
                                    val moved = change.position - down.position
                                    if (!ownsGesture && abs(moved.x) > viewConfiguration.touchSlop && abs(moved.x) > abs(moved.y)) {
                                        ownsGesture = true
                                    }
                                    if (ownsGesture) change.consume()
                                    val next = indexAt(change.position)
                                    if (next != targetIndex) {
                                        if (next != null && isDraggingIndicator) {
                                            haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        }
                                        targetIndex = next
                                        if (next != null) pressedPosition = next
                                    }
                                }
                                val committed = targetIndex
                                if (released && committed != null && committed != currentSelectedIndex) {
                                    haptics.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    currentOnOptionSelected(committed)
                                }
                            } finally {
                                targetIndex = null
                                isDraggingIndicator = false
                            }
                        }
                    }
                } else {
                    Modifier
                }
            )
    ) {
        val gap = ElectronSpacing.xxs
        val segmentWidth = (maxWidth - ElectronSpacing.xxs * 2 - gap * (options.size - 1)) / options.size
        val animatedIndex by animateFloatAsState(
            targetValue = displayedIndex.toFloat(),
            animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMediumLow),
            label = "segmentIndicator"
        )
        val pressedAlpha by animateFloatAsState(
            targetValue = if (targetIndex != null && !isDraggingIndicator && targetIndex != safeIndex) 1f else 0f,
            animationSpec = tween(ElectronMotion.instant),
            label = "segmentPressed"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(ElectronDimens.controlHeightMd)
                .background(colors.track, ElectronShapes.control)
                .padding(ElectronSpacing.xxs)
        ) {
            // Pressed tint under the finger; it stays in place while fading out.
            Box(
                modifier = Modifier
                    .offset(x = (segmentWidth + gap) * pressedPosition)
                    .width(segmentWidth)
                    .fillMaxHeight()
                    .graphicsLayer { alpha = pressedAlpha }
                    .background(pressedColor, ElectronShapes.segment)
            )
            Box(
                modifier = Modifier
                    .offset(x = (segmentWidth + gap) * animatedIndex)
                    .width(segmentWidth)
                    .fillMaxHeight()
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
                    val isHighlighted = index == displayedIndex
                    val content by animateColorAsState(
                        targetValue = if (isHighlighted) colors.selectedContent else colors.content,
                        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
                        label = "segmentContent"
                    )
                    // Touch is handled by the control; each segment keeps its
                    // own semantics so TalkBack reads and activates it.
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .semantics(mergeDescendants = true) {
                                role = Role.Tab
                                selected = isSelected
                                if (isEnabled) {
                                    onClick {
                                        if (!isSelected) currentOnOptionSelected(index)
                                        true
                                    }
                                } else {
                                    disabled()
                                }
                            }
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
}
