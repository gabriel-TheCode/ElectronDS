package com.electron.designsystem.components.divider.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import com.electron.designsystem.tokens.ElectronDimens

/** Hairline separator: resolved color, orientation and insets in. */
@Composable
internal fun DividerPrimitive(
    color: Color,
    isVertical: Boolean,
    startInset: Dp,
    endInset: Dp,
    testTag: String,
    modifier: Modifier = Modifier
) {
    val sizing = if (isVertical) {
        Modifier
            .fillMaxHeight()
            .width(ElectronDimens.separatorHeight)
    } else {
        Modifier
            .fillMaxWidth()
            .padding(start = startInset, end = endInset)
            .height(ElectronDimens.separatorHeight)
    }
    Box(
        modifier = modifier
            .then(sizing)
            .background(color)
            .testTag(testTag)
    )
}
