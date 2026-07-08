package com.electron.designsystem.components.icon.primitives

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp

/**
 * Lowest-level icon block: stateless, receives resolved raw values only.
 * Never used outside the design system module.
 */
@Composable
internal fun IconPrimitive(
    imageVector: ImageVector,
    contentDescription: String?,
    tint: Color,
    size: Dp,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .size(size)
            .testTag(testTag)
    )
}
