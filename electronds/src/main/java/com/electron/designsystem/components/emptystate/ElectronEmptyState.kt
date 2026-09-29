package com.electron.designsystem.components.emptystate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel
import com.electron.designsystem.components.emptystate.variants.EmptyStateDefault
import com.electron.designsystem.components.emptystate.variants.EmptyStateError
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronEmptyState
 *
 * Purpose: placeholder for empty lists, empty search results and load
 * failures. Built from ElectronAvatar and ElectronButton; the screen
 * decides when to show it inside its own content.
 *
 * API:
 * - [uiModel]: variant (sealed [EmptyStateUiModel]).
 * - [onPrimaryActionClick]: signal emitted by the primary button of a
 *   [EmptyStateUiModel.Default]. Only relevant when `primaryActionLabel` is set.
 * - [onSecondaryActionClick]: signal emitted by the secondary link of a
 *   [EmptyStateUiModel.Default]. Only relevant when `secondaryActionLabel` is set.
 * - [onRetryClick]: signal emitted by the retry button of a
 *   [EmptyStateUiModel.Error]. Only relevant when `retryLabel` is set.
 *
 * Usage:
 * ```
 * ElectronEmptyState(
 *     uiModel = EmptyStateUiModel.Error(icon = Icons.Outlined.CloudOff, title = "Couldn't load stations", retryLabel = "Retry"),
 *     onRetryClick = viewModel::onRetryClicked
 * )
 * ```
 */
@Composable
public fun ElectronEmptyState(
    uiModel: EmptyStateUiModel,
    modifier: Modifier = Modifier,
    onPrimaryActionClick: () -> Unit = {},
    onSecondaryActionClick: () -> Unit = {},
    onRetryClick: () -> Unit = {}
) {
    when (uiModel) {
        is EmptyStateUiModel.Default -> EmptyStateDefault(uiModel, onPrimaryActionClick, onSecondaryActionClick, modifier)
        is EmptyStateUiModel.Error -> EmptyStateError(uiModel, onRetryClick, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronEmptyStatePreview() {
    ElectronPreviewSurface {
        ElectronEmptyState(
            EmptyStateUiModel.Default(
                icon = Icons.Outlined.EvStation,
                title = "No charging sessions yet",
                message = "Plug in your vehicle to start tracking consumption.",
                primaryActionLabel = "Find a station",
                secondaryActionLabel = "Learn more"
            )
        )
        ElectronEmptyState(
            EmptyStateUiModel.Error(
                icon = Icons.Outlined.CloudOff,
                title = "Couldn't load stations",
                message = "Check your connection and try again.",
                retryLabel = "Retry"
            )
        )
    }
}
