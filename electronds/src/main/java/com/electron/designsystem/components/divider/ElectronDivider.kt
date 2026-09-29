package com.electron.designsystem.components.divider

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.divider.models.DividerEmphasis
import com.electron.designsystem.components.divider.models.DividerInset
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.divider.variants.DividerHorizontal
import com.electron.designsystem.components.divider.variants.DividerVertical
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronDivider
 *
 * Purpose: hairline separator between content groups or list rows.
 *
 * Usage:
 * ```
 * ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
 * ```
 */
@Composable
public fun ElectronDivider(
    uiModel: DividerUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is DividerUiModel.Horizontal -> DividerHorizontal(uiModel, modifier)
        is DividerUiModel.Vertical -> DividerVertical(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronDividerPreview() {
    ElectronPreviewSurface {
        ElectronDivider(DividerUiModel.Horizontal())
        ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Both, emphasis = DividerEmphasis.Strong))
        Row(modifier = Modifier.height(ElectronDimens.controlHeightMd)) {
            ElectronDivider(DividerUiModel.Vertical(emphasis = DividerEmphasis.Default))
        }
    }
}
