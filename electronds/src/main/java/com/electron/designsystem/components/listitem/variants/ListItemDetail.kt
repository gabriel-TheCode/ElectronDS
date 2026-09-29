package com.electron.designsystem.components.listitem.variants

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.tag.ElectronTag
import com.electron.designsystem.foundation.ElectronTheme

/** Read-only row: value in the data typeface and/or a status tag. */
@Composable
internal fun ListItemDetail(
    uiModel: ListItemUiModel.Detail,
    modifier: Modifier = Modifier
) {
    val hasTrailing = uiModel.valueText != null || uiModel.tag != null
    ListItemRow(
        title = uiModel.title,
        subtitle = uiModel.subtitle,
        leading = uiModel.leading,
        isEnabled = true,
        testTag = uiModel.testTag,
        modifier = modifier,
        trailing = if (hasTrailing) {
            {
                if (uiModel.valueText != null) {
                    Text(
                        text = uiModel.valueText,
                        style = ElectronTheme.typography.dataMedium,
                        color = ElectronTheme.colors.content.primary
                    )
                }
                if (uiModel.tag != null) {
                    ElectronTag(uiModel.tag)
                }
            }
        } else {
            null
        }
    )
}
