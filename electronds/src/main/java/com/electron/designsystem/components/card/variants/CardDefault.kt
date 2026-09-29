package com.electron.designsystem.components.card.variants

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.card.primitives.CardPrimitive
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Default content card: optional title header, content slot, optional
 * footer action. The action label is data; the click is a callback.
 */
@Composable
internal fun CardDefault(
    uiModel: CardUiModel.Default,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CardPrimitive(
        containerColor = ElectronTheme.colors.background.surface,
        borderColor = ElectronTheme.colors.border.subtle,
        testTag = uiModel.testTag,
        modifier = modifier
    ) {
        if (uiModel.title != null) {
            Text(
                text = uiModel.title,
                style = ElectronTheme.typography.titleMedium,
                color = ElectronTheme.colors.content.primary,
                modifier = Modifier.padding(
                    start = ElectronSpacing.lg,
                    end = ElectronSpacing.lg,
                    top = ElectronSpacing.lg,
                    bottom = ElectronSpacing.sm
                )
            )
        }

        content()

        if (uiModel.actionLabel != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ElectronSpacing.sm)
            ) {
                ElectronButton(
                    uiModel = ButtonUiModel.Tertiary(text = uiModel.actionLabel, isFullWidth = true),
                    onClick = onActionClick
                )
            }
        }
    }
}
