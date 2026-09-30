package com.electron.designsystem.components.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.skeleton.variants.SkeletonBlock
import com.electron.designsystem.components.skeleton.variants.SkeletonCircle
import com.electron.designsystem.components.skeleton.variants.SkeletonListItem
import com.electron.designsystem.components.skeleton.variants.SkeletonMetricCard
import com.electron.designsystem.components.skeleton.variants.SkeletonText
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronSkeleton
 *
 * Purpose: loading placeholder for content whose shape is known. Prefer it
 * over a spinner when loading replaces a screen or a list: the layout
 * appears immediately and does not shift when the data arrives. Use a
 * spinner (ElectronProgressIndicator) for actions (saving, sending).
 *
 * Usage:
 * ```
 * if (uiState.isLoading) {
 *     repeat(3) { ElectronSkeleton(SkeletonUiModel.ListItem()) }
 * } else {
 *     uiState.sessions.forEach { ElectronListItem(it.toUiModel()) }
 * }
 * ```
 */
@Composable
public fun ElectronSkeleton(
    uiModel: SkeletonUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is SkeletonUiModel.Text -> SkeletonText(uiModel, modifier)
        is SkeletonUiModel.Circle -> SkeletonCircle(uiModel, modifier)
        is SkeletonUiModel.Block -> SkeletonBlock(uiModel, modifier)
        is SkeletonUiModel.ListItem -> SkeletonListItem(uiModel, modifier)
        is SkeletonUiModel.MetricCard -> SkeletonMetricCard(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronSkeletonPreview() {
    ElectronPreviewSurface {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronSkeleton(SkeletonUiModel.MetricCard(), modifier = Modifier.weight(1f))
            ElectronSkeleton(SkeletonUiModel.MetricCard(), modifier = Modifier.weight(1f))
        }
        ElectronSkeleton(SkeletonUiModel.ListItem())
        ElectronSkeleton(SkeletonUiModel.ListItem(hasSubtitle = false))
        ElectronSkeleton(SkeletonUiModel.Circle(AvatarSize.Lg))
        ElectronSkeleton(SkeletonUiModel.Text(lines = 3))
        ElectronSkeleton(SkeletonUiModel.Block())
    }
}
