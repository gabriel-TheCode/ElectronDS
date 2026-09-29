package com.electron.designsystem.components.skeleton.variants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.skeleton.primitives.SkeletonLine
import com.electron.designsystem.components.skeleton.primitives.rememberShimmerPhase
import com.electron.designsystem.components.skeleton.primitives.skeletonSemantics
import com.electron.designsystem.foundation.ElectronTheme

private const val LAST_LINE_FRACTION = 0.6f

@Composable
internal fun SkeletonText(uiModel: SkeletonUiModel.Text, modifier: Modifier = Modifier) {
    val colors = skeletonColors()
    val phase = rememberShimmerPhase()
    val lines = uiModel.lines.coerceAtLeast(1)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .skeletonSemantics(uiModel.contentDescription)
            .testTag(uiModel.testTag)
    ) {
        repeat(lines) { index ->
            SkeletonLine(
                style = ElectronTheme.typography.bodyLarge,
                widthFraction = if (index == lines - 1 && lines > 1) LAST_LINE_FRACTION else 1f,
                colors = colors,
                phase = phase
            )
        }
    }
}
