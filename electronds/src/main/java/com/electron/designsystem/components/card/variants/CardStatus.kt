package com.electron.designsystem.components.card.variants

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.card.models.CardSeverity
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.card.primitives.CardPrimitive
import com.electron.designsystem.components.icon.models.IconSize
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.icon.ElectronIcon
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

internal data class StatusCardStyle(
    val background: Color,
    val contentTint: IconTone,
    val icon: ImageVector
)

@Composable
internal fun CardSeverity.style(): StatusCardStyle {
    val c = ElectronTheme.colors
    return when (this) {
        CardSeverity.Info -> StatusCardStyle(c.status.infoSubtle, IconTone.Info, Icons.Outlined.Info)
        CardSeverity.Success -> StatusCardStyle(c.status.successSubtle, IconTone.Success, Icons.Outlined.CheckCircle)
        CardSeverity.Warning -> StatusCardStyle(c.status.warningSubtle, IconTone.Warning, Icons.Outlined.WarningAmber)
        CardSeverity.Error -> StatusCardStyle(c.status.errorSubtle, IconTone.Error, Icons.Outlined.ErrorOutline)
    }
}

/** Tinted message card with a severity icon. Replaces WarningMessageCard. */
@Composable
internal fun CardStatus(
    uiModel: CardUiModel.Status,
    modifier: Modifier = Modifier
) {
    val style = uiModel.severity.style()
    CardPrimitive(
        containerColor = style.background,
        borderColor = style.background,
        testTag = uiModel.testTag,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(ElectronSpacing.lg)
        ) {
            ElectronIcon(
                uiModel = IconUiModel.Default(
                    imageVector = style.icon,
                    tone = style.contentTint,
                    size = IconSize.Md
                )
            )
            Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            Text(
                text = uiModel.message,
                style = ElectronTheme.typography.bodyMedium,
                color = ElectronTheme.colors.content.primary
            )
        }
    }
}
