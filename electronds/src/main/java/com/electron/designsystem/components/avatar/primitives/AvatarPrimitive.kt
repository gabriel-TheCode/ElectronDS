package com.electron.designsystem.components.avatar.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import com.electron.designsystem.tokens.ElectronShapes

/**
 * Circular avatar block. Receives fully resolved values: the container size
 * and icon size arrive together, so no size inference happens down here.
 */
@Composable
internal fun AvatarPrimitive(
    containerSize: Dp,
    backgroundColor: Color,
    contentColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    iconSize: Dp? = null,
    initials: String? = null,
    initialsStyle: TextStyle? = null,
    contentDescription: String? = null
) {
    Box(
        modifier = modifier
            .size(containerSize)
            .background(color = backgroundColor, shape = ElectronShapes.pill)
            .testTag(testTag),
        contentAlignment = Alignment.Center
    ) {
        when {
            icon != null && iconSize != null -> Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = contentColor,
                modifier = Modifier.size(iconSize)
            )
            initials != null && initialsStyle != null -> Text(
                text = initials,
                style = initialsStyle,
                color = contentColor
            )
        }
    }
}
