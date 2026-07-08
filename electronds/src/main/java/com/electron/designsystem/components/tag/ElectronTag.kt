package com.electron.designsystem.components.tag

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.tag.models.TagStyle
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.components.tag.variants.TagIcon
import com.electron.designsystem.components.tag.variants.TagText
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronTag
 *
 * Purpose: compact status or category marker.
 *
 * Usage:
 * ```
 * ElectronTag(
 *     uiModel = TagUiModel.Text(text = "Live", tone = TagTone.Success, style = TagStyle.Tinted)
 * )
 * ```
 */
@Composable
public fun ElectronTag(
    uiModel: TagUiModel,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is TagUiModel.Text -> TagText(uiModel, modifier)
        is TagUiModel.Icon -> TagIcon(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronTagPreview() {
    ElectronPreviewSurface {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronTag(TagUiModel.Text("Live", tone = TagTone.Success))
            ElectronTag(TagUiModel.Text("Pending", tone = TagTone.Warning, style = TagStyle.Outlined))
            ElectronTag(TagUiModel.Text("Failed", tone = TagTone.Error, style = TagStyle.Filled))
            ElectronTag(TagUiModel.Icon(Icons.Outlined.Bolt, "Charged", tone = TagTone.Brand))
        }
    }
}
