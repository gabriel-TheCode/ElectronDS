package com.electron.designsystem.components.card

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.card.models.CardSeverity
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.card.variants.CardDefault
import com.electron.designsystem.components.card.variants.CardStatus
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronCard
 *
 * Purpose: surface container for grouped content.
 *
 * The body is a slot because card content is feature territory; the frame
 * (title, action, severity styling) is design-system territory and is
 * described by the UI model.
 *
 * Usage:
 * ```
 * ElectronCard(
 *     uiModel = CardUiModel.Default(title = "Positions", actionLabel = "See all"),
 *     onActionClick = viewModel::onSeeAllClicked
 * ) {
 *     PositionsList(uiState.positions)
 * }
 * ```
 */
@Composable
public fun ElectronCard(
    uiModel: CardUiModel,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    when (uiModel) {
        is CardUiModel.Default -> CardDefault(uiModel, onActionClick, modifier, content)
        is CardUiModel.Status -> CardStatus(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronCardPreview() {
    ElectronPreviewSurface {
        ElectronCard(
            uiModel = CardUiModel.Default(title = "Consumption", actionLabel = "See all"),
            onActionClick = {}
        ) {
            Text(
                text = "3 devices connected",
                style = ElectronTheme.typography.bodyMedium,
                color = ElectronTheme.colors.content.secondary,
                modifier = Modifier.padding(horizontal = ElectronSpacing.lg)
            )
        }
        ElectronCard(uiModel = CardUiModel.Status(message = "Your session expires in 5 minutes.", severity = CardSeverity.Warning))
        ElectronCard(uiModel = CardUiModel.Status(message = "Transfer completed.", severity = CardSeverity.Success))
    }
}
