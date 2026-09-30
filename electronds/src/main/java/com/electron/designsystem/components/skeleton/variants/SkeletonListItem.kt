package com.electron.designsystem.components.skeleton.variants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.skeleton.primitives.SkeletonLine
import com.electron.designsystem.components.skeleton.primitives.rememberShimmerPhase
import com.electron.designsystem.components.skeleton.primitives.skeletonSemantics
import com.electron.designsystem.components.skeleton.primitives.skeletonShape
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Mirrors ElectronListItem: same min height, paddings, leading slot width
 * and gap, and one line per text line at the row's own line heights.
 */
@Composable
internal fun SkeletonListItem(uiModel: SkeletonUiModel.ListItem, modifier: Modifier = Modifier) {
    val colors = skeletonColors()
    val phase = rememberShimmerPhase()
    val typography = ElectronTheme.typography
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElectronDimens.listItemMinHeight)
            .padding(ElectronSpacing.lg)
            .skeletonSemantics(uiModel.contentDescription)
            .testTag(uiModel.testTag)
    ) {
        if (uiModel.hasLeading) {
            Box(
                modifier = Modifier
                    .size(ElectronDimens.avatarMd)
                    .skeletonShape(ElectronShapes.pill, colors, phase)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            SkeletonLine(typography.bodyLarge, widthFraction = 0.5f, colors = colors, phase = phase)
            if (uiModel.hasSubtitle) {
                SkeletonLine(typography.bodyMedium, widthFraction = 0.8f, colors = colors, phase = phase)
            }
        }
    }
}
