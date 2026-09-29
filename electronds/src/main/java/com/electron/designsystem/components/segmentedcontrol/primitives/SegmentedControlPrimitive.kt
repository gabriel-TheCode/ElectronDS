package com.electron.designsystem.components.segmentedcontrol.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

internal data class SegmentColors(
    val track: Color,
    val selectedContainer: Color,
    val selectedContent: Color,
    val content: Color
)

/**
 * Sunken track holding equal-width segments; the selected segment is a
 * raised surface. Color changes are animated with the quick motion token.
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
    Row(
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xxs),
        modifier = modifier
            .fillMaxWidth()
            .height(ElectronDimens.controlHeightMd)
            .background(colors.track, ElectronShapes.control)
            .padding(ElectronSpacing.xxs)
            .selectableGroup()
            .testTag(testTag)
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = index == selectedIndex
            val container by animateColorAsState(
                targetValue = if (isSelected) colors.selectedContainer else Color.Transparent,
                animationSpec = tween(ElectronMotion.quick),
                label = "segmentContainer"
            )
            val content by animateColorAsState(
                targetValue = if (isSelected) colors.selectedContent else colors.content,
                animationSpec = tween(ElectronMotion.quick),
                label = "segmentContent"
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(ElectronShapes.segment)
                    .background(container)
                    .selectable(
                        selected = isSelected,
                        enabled = isEnabled,
                        role = Role.Tab,
                        onClick = { onOptionSelected(index) }
                    )
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
