package com.electron.designsystem.components.icon

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.icon.models.IconSize
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.icon.variants.IconDefault
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronIcon
 *
 * Purpose: single entry point for iconography. The UI model selects the
 * variant; sizes and tints are semantic tokens, never raw values.
 *
 * Usage:
 * ```
 * ElectronIcon(
 *     uiModel = IconUiModel.Default(
 *         imageVector = Icons.Outlined.Bolt,
 *         tone = IconTone.Brand,
 *         size = IconSize.Lg
 *     )
 * )
 * ```
 */
@Composable
public fun ElectronIcon(
    uiModel: IconUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is IconUiModel.Default -> IconDefault(uiModel = uiModel, modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronIconPreview() {
    ElectronPreviewSurface {
        Row {
            ElectronIcon(IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand, size = IconSize.Xl))
            ElectronIcon(IconUiModel.Default(Icons.Outlined.CheckCircle, tone = IconTone.Success, size = IconSize.Lg))
            ElectronIcon(IconUiModel.Default(Icons.Outlined.ErrorOutline, tone = IconTone.Error, size = IconSize.Md))
        }
    }
}
