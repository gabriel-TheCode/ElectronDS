package com.electron.designsystem.components.fab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.fab.models.FabUiModel
import com.electron.designsystem.components.fab.variants.FabCompact
import com.electron.designsystem.components.fab.variants.FabExtended
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronFab
 *
 * Purpose: primary floating action of a screen.
 *
 * Usage:
 * ```
 * ElectronFab(
 *     uiModel = FabUiModel.Extended(text = "New transfer", icon = Icons.Outlined.Add, isExpanded = isTop),
 *     onClick = viewModel::onNewTransferClicked
 * )
 * ```
 */
@Composable
public fun ElectronFab(
    uiModel: FabUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is FabUiModel.Extended -> FabExtended(uiModel, onClick, modifier)
        is FabUiModel.Compact -> FabCompact(uiModel, onClick, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronFabPreview() {
    ElectronPreviewSurface {
        ElectronFab(FabUiModel.Extended(text = "New", icon = Icons.Outlined.Add), onClick = {})
        ElectronFab(FabUiModel.Extended(text = "New", icon = Icons.Outlined.Add, isExpanded = false), onClick = {})
        ElectronFab(FabUiModel.Compact(icon = Icons.Outlined.Add, iconContentDescription = "Add"), onClick = {})
    }
}
