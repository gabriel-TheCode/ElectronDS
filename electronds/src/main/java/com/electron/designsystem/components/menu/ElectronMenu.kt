package com.electron.designsystem.components.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.menu.models.MenuItem
import com.electron.designsystem.components.menu.models.MenuUiModel
import com.electron.designsystem.components.menu.variants.MenuDefault
import com.electron.designsystem.components.menu.variants.MenuDefaultPanel
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronMenu
 *
 * Purpose: short list of actions or choices anchored to an element (an
 * overflow button, a field). Place it in the same `Box` as its anchor; it
 * opens below the anchor and flips above when there is no room.
 *
 * API:
 * - [uiModel]: items and expansion (sealed [MenuUiModel]).
 * - [onItemClick]: signal emitted with the index of the tapped item. The
 *   menu does not close itself: set `isExpanded = false` in response.
 * - [onDismissRequest]: signal emitted on outside tap or back press.
 *
 * Usage:
 * ```
 * Box {
 *     ElectronButton(ButtonUiModel.Tertiary(icon = moreIcon), onClick = viewModel::onMoreClicked)
 *     ElectronMenu(
 *         uiModel = MenuUiModel.Default(items = uiState.actions, isExpanded = uiState.isMenuOpen),
 *         onItemClick = viewModel::onActionSelected,
 *         onDismissRequest = viewModel::onMenuDismissed
 *     )
 * }
 * ```
 */
@Composable
public fun ElectronMenu(
    uiModel: MenuUiModel,
    onItemClick: (Int) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is MenuUiModel.Default -> MenuDefault(uiModel, onItemClick, onDismissRequest, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronMenuPreview() {
    ElectronPreviewSurface {
        MenuDefaultPanel(
            uiModel = MenuUiModel.Default(
                items = listOf(
                    MenuItem("Rename", leadingIcon = Icons.Outlined.Edit),
                    MenuItem("Duplicate", leadingIcon = Icons.Outlined.ContentCopy, trailingText = "Ctrl+D"),
                    MenuItem("Delete", leadingIcon = Icons.Outlined.DeleteOutline, isDestructive = true)
                ),
                isExpanded = true
            ),
            onItemClick = {}
        )
    }
}
