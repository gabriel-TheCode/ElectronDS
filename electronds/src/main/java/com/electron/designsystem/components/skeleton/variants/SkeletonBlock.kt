package com.electron.designsystem.components.skeleton.variants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.skeleton.primitives.rememberShimmerPhase
import com.electron.designsystem.components.skeleton.primitives.skeletonSemantics
import com.electron.designsystem.components.skeleton.primitives.skeletonShape
import com.electron.designsystem.tokens.ElectronShapes

@Composable
internal fun SkeletonBlock(uiModel: SkeletonUiModel.Block, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(uiModel.aspectRatio.coerceAtLeast(0.1f))
            .skeletonShape(ElectronShapes.card, skeletonColors(), rememberShimmerPhase())
            .skeletonSemantics(uiModel.contentDescription)
            .testTag(uiModel.testTag)
    )
}
