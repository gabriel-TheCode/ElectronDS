package com.electron.designsystem.components.card.variants

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.card.primitives.CardPrimitive
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Default content card: optional header, then the content slot.
 *
 * The header puts the title and its action on one line (title leading,
 * small tertiary action trailing), the pattern people already read as
 * "section + see all". The legacy full-width footer button spent a whole
 * row on a secondary action and pulled the eye to the bottom of the card.
 * The action's own padding is subtracted from the header's end padding so
 * its label aligns with the card's content edge.
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
        if (uiModel.title != null || uiModel.actionLabel != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = ElectronDimens.controlHeightLg)
                    .padding(
                        start = ElectronSpacing.lg,
                        end = if (uiModel.actionLabel != null) ElectronSpacing.xs else ElectronSpacing.lg,
                        top = ElectronSpacing.xs
                    )
            ) {
                if (uiModel.title != null) {
                    Text(
                        text = uiModel.title,
                        style = ElectronTheme.typography.titleMedium,
                        color = ElectronTheme.colors.content.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
                if (uiModel.actionLabel != null) {
                    ElectronButton(
                        uiModel = ButtonUiModel.Tertiary(text = uiModel.actionLabel, size = ButtonSize.Small),
                        onClick = onActionClick
                    )
                }
            }
        }

        content()
    }
}
