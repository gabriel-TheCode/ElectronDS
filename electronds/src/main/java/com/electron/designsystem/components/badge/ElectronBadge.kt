package com.electron.designsystem.components.badge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.badge.models.BadgeTone
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.badge.variants.BadgeCount
import com.electron.designsystem.components.badge.variants.BadgeDot
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronBadge
 *
 * Purpose: notification marker (dot or counter) attached to another
 * element such as an icon, a list row or a top bar action.
 *
 * Usage:
 * ```
 * ElectronBadge(BadgeUiModel.Count(count = uiState.unreadCount, contentDescription = "3 unread"))
 * ```
 */
@Composable
public fun ElectronBadge(
    uiModel: BadgeUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is BadgeUiModel.Dot -> BadgeDot(uiModel, modifier)
        is BadgeUiModel.Count -> BadgeCount(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronBadgePreview() {
    ElectronPreviewSurface {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElectronBadge(BadgeUiModel.Dot())
            ElectronBadge(BadgeUiModel.Count(count = 3))
            ElectronBadge(BadgeUiModel.Count(count = 128, tone = BadgeTone.Brand))
            ElectronBadge(BadgeUiModel.Count(count = 12, tone = BadgeTone.Neutral))
        }
    }
}
