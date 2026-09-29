package com.electron.designsystem.components.divider.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.divider.primitives.DividerPrimitive

@Composable
internal fun DividerHorizontal(
    uiModel: DividerUiModel.Horizontal,
    modifier: Modifier = Modifier
) {
    val insets = uiModel.inset.resolve()
    DividerPrimitive(
        color = uiModel.emphasis.resolve(),
        isVertical = false,
        startInset = insets.start,
        endInset = insets.end,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
