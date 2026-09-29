package com.electron.designsystem.components.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.progress.models.ProgressSize
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.components.progress.models.ProgressUiModel
import com.electron.designsystem.components.progress.variants.ProgressCircular
import com.electron.designsystem.components.progress.variants.ProgressLinear
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronProgressIndicator
 *
 * Purpose: determinate or indeterminate progress, as a bar or a ring.
 * The progress value is data computed by the screen.
 *
 * Usage:
 * ```
 * ElectronProgressIndicator(
 *     uiModel = ProgressUiModel.Linear(progress = uiState.chargeLevel, tone = ProgressTone.Success)
 * )
 * ```
 */
@Composable
public fun ElectronProgressIndicator(
    uiModel: ProgressUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is ProgressUiModel.Linear -> ProgressLinear(uiModel, modifier)
        is ProgressUiModel.Circular -> ProgressCircular(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronProgressIndicatorPreview() {
    ElectronPreviewSurface {
        ElectronProgressIndicator(ProgressUiModel.Linear(progress = 0.64f))
        ElectronProgressIndicator(ProgressUiModel.Linear(progress = 0.2f, tone = ProgressTone.Error))
        ElectronProgressIndicator(ProgressUiModel.Linear())
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.lg)) {
            ElectronProgressIndicator(ProgressUiModel.Circular(size = ProgressSize.Sm))
            ElectronProgressIndicator(ProgressUiModel.Circular(progress = 0.8f, tone = ProgressTone.Success))
            ElectronProgressIndicator(ProgressUiModel.Circular(progress = 0.35f, size = ProgressSize.Lg, tone = ProgressTone.Accent))
        }
    }
}
