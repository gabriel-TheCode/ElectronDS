package com.electron.designsystem.components.skeleton.variants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.skeleton.primitives.rememberShimmerPhase
import com.electron.designsystem.components.skeleton.primitives.skeletonSemantics
import com.electron.designsystem.components.skeleton.primitives.skeletonShape
import com.electron.designsystem.tokens.ElectronShapes

@Composable
internal fun SkeletonCircle(uiModel: SkeletonUiModel.Circle, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(uiModel.size.skeletonSize())
            .skeletonShape(ElectronShapes.pill, skeletonColors(), rememberShimmerPhase())
            .skeletonSemantics(uiModel.contentDescription)
            .testTag(uiModel.testTag)
    )
}
