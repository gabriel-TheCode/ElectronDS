package com.electron.designsystem.components.tag.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Stateless tag container: resolved colors and metrics in, pixels out.
 * Tags use the smallest radius (4dp): they are labels, not buttons, and a
 * crisp corner keeps them from being mistaken for tappable chips.
 */
@Composable
internal fun TagPrimitive(
    backgroundColor: Color,
    contentColor: Color,
    borderColor: Color,
    horizontalPadding: Dp,
    verticalPadding: Dp,
    iconSize: Dp,
    textStyle: TextStyle,
    testTag: String,
    modifier: Modifier = Modifier,
    text: String? = null,
    leadingIcon: ImageVector? = null,
    iconContentDescription: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = backgroundColor, shape = ElectronShapes.tag)
            .border(width = ElectronDimens.borderWidth, color = borderColor, shape = ElectronShapes.tag)
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .testTag(testTag)
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = iconContentDescription,
                tint = contentColor,
                modifier = Modifier.size(iconSize)
            )
            if (text != null) {
                Spacer(modifier = Modifier.width(ElectronSpacing.xs))
            }
        }
        if (text != null) {
            Text(text = text, style = textStyle, color = contentColor)
        }
    }
}
