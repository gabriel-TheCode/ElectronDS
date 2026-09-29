package com.electron.designsystem.components.listitem.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.toggle.ElectronSwitch
import com.electron.designsystem.components.toggle.models.SwitchUiModel

/**
 * Setting row: the whole row is the toggle. The trailing switch is a
 * visual indicator: it stays tappable but is removed from focus and
 * accessibility so the row is announced and focused once.
 */
@Composable
internal fun ListItemToggle(
    uiModel: ListItemUiModel.Toggle,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ListItemRow(
        title = uiModel.title,
        subtitle = uiModel.subtitle,
        leading = uiModel.leading,
        isEnabled = uiModel.isEnabled,
        role = Role.Switch,
        toggleValue = uiModel.isChecked,
        onClick = { onCheckedChange(!uiModel.isChecked) },
        testTag = uiModel.testTag,
        modifier = modifier
    ) {
        ElectronSwitch(
            uiModel = SwitchUiModel.Default(isChecked = uiModel.isChecked, isEnabled = uiModel.isEnabled),
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .focusProperties { canFocus = false }
                .clearAndSetSemantics {}
        )
    }
}
