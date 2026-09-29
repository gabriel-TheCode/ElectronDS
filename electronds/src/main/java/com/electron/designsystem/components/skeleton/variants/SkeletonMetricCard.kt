package com.electron.designsystem.components.skeleton.variants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.skeleton.primitives.SkeletonLine
import com.electron.designsystem.components.skeleton.primitives.rememberShimmerPhase
import com.electron.designsystem.components.skeleton.primitives.skeletonSemantics
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

/** Mirrors ElectronMetricCard: card frame, label, hero figure, caption. */
@Composable
internal fun SkeletonMetricCard(uiModel: SkeletonUiModel.MetricCard, modifier: Modifier = Modifier) {
    val colors = skeletonColors()
    val phase = rememberShimmerPhase()
    val typography = ElectronTheme.typography
    ElectronCard(
        uiModel = CardUiModel.Default(testTag = uiModel.testTag),
        modifier = modifier.skeletonSemantics(uiModel.contentDescription)
    ) {
        Column(modifier = Modifier.padding(ElectronSpacing.lg)) {
            SkeletonLine(typography.labelMedium, widthFraction = 0.4f, colors = colors, phase = phase)
            Spacer(modifier = Modifier.height(ElectronSpacing.xs))
            SkeletonLine(typography.dataDisplay, widthFraction = 0.55f, colors = colors, phase = phase)
            Spacer(modifier = Modifier.height(ElectronSpacing.md))
            SkeletonLine(typography.bodySmall, widthFraction = 0.3f, colors = colors, phase = phase)
        }
    }
}
