package com.electron.designsystem.components.listitem.variants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.electron.designsystem.components.badge.ElectronBadge
import com.electron.designsystem.components.icon.ElectronIcon
import com.electron.designsystem.components.icon.models.IconSize
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.foundation.ElectronTheme

/** Tappable row: optional value and badge, then a chevron. */
@Composable
internal fun ListItemNavigation(
    uiModel: ListItemUiModel.Navigation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItemRow(
        title = uiModel.title,
        subtitle = uiModel.subtitle,
        leading = uiModel.leading,
        isEnabled = uiModel.isEnabled,
        role = Role.Button,
        onClick = onClick,
        testTag = uiModel.testTag,
        modifier = modifier
    ) {
        if (uiModel.valueText != null) {
            Text(
                text = uiModel.valueText,
                style = ElectronTheme.typography.bodyMedium,
                color = ElectronTheme.colors.content.secondary
            )
        }
        if (uiModel.badge != null) {
            ElectronBadge(uiModel.badge)
        }
        ElectronIcon(
            IconUiModel.Default(
                imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                size = IconSize.Md,
                tone = IconTone.Muted
            )
        )
    }
}
