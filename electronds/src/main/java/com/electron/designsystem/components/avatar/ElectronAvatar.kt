package com.electron.designsystem.components.avatar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.avatar.variants.AvatarDefault
import com.electron.designsystem.components.avatar.variants.AvatarInitials
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronAvatar
 *
 * Purpose: circular identity or category marker.
 *
 * Usage:
 * ```
 * ElectronAvatar(
 *     uiModel = AvatarUiModel.Default(
 *         icon = Icons.Outlined.Payments,
 *         size = AvatarSize.Md,
 *         tone = AvatarTone.Brand
 *     )
 * )
 * ```
 */
@Composable
public fun ElectronAvatar(
    uiModel: AvatarUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is AvatarUiModel.Default -> AvatarDefault(uiModel, modifier)
        is AvatarUiModel.Initials -> AvatarInitials(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronAvatarPreview() {
    ElectronPreviewSurface {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Bolt, AvatarSize.Sm, AvatarTone.Brand))
            ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Payments, AvatarSize.Md, AvatarTone.Accent))
            ElectronAvatar(AvatarUiModel.Initials("GT", AvatarSize.Lg, AvatarTone.Neutral))
        }
    }
}
