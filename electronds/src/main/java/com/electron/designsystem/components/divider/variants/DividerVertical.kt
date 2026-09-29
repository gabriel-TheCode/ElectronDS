package com.electron.designsystem.components.divider.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.divider.primitives.DividerPrimitive
import com.electron.designsystem.tokens.ElectronSpacing

@Composable
internal fun DividerVertical(
    uiModel: DividerUiModel.Vertical,
    modifier: Modifier = Modifier
) {
    DividerPrimitive(
        color = uiModel.emphasis.resolve(),
        isVertical = true,
        startInset = ElectronSpacing.none,
        endInset = ElectronSpacing.none,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
