package com.electron.designsystem.components.listitem

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.divider.ElectronDivider
import com.electron.designsystem.components.divider.models.DividerInset
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.listitem.variants.ListItemDetail
import com.electron.designsystem.components.listitem.variants.ListItemNavigation
import com.electron.designsystem.components.listitem.variants.ListItemToggle
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronListItem
 *
 * Purpose: standard row for settings, menus and detail lists. Composes
 * existing Electron components (avatar, icon, badge, tag, switch).
 *
 * API:
 * - [uiModel]: row variant (sealed [ListItemUiModel]).
 * - [onClick]: signal emitted when a [ListItemUiModel.Navigation] row is tapped.
 * - [onCheckedChange]: signal emitted when a [ListItemUiModel.Toggle] row or
 *   its switch is toggled, with the new value.
 *
 * [ListItemUiModel.Detail] rows are read-only and emit no signal.
 *
 * Usage:
 * ```
 * ElectronListItem(
 *     uiModel = ListItemUiModel.Toggle(title = "Notifications", isChecked = uiState.notificationsOn),
 *     onCheckedChange = viewModel::onNotificationsToggled
 * )
 * ```
 */
@Composable
public fun ElectronListItem(
    uiModel: ListItemUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    onCheckedChange: (Boolean) -> Unit = {}
) {
    when (uiModel) {
        is ListItemUiModel.Navigation -> ListItemNavigation(uiModel, onClick, modifier)
        is ListItemUiModel.Toggle -> ListItemToggle(uiModel, onCheckedChange, modifier)
        is ListItemUiModel.Detail -> ListItemDetail(uiModel, modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronListItemPreview() {
    ElectronPreviewSurface {
        Column {
            ElectronListItem(
                ListItemUiModel.Navigation(
                    title = "Payment methods",
                    subtitle = "2 cards linked",
                    leading = ListItemLeading.Avatar(AvatarUiModel.Default(Icons.Outlined.Payments, AvatarSize.Md, AvatarTone.Brand)),
                    badge = BadgeUiModel.Count(count = 1)
                ),
                onClick = {}
            )
            ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
            ElectronListItem(
                ListItemUiModel.Toggle(
                    title = "Push notifications",
                    isChecked = true,
                    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Notifications, tone = IconTone.Muted))
                ),
                onCheckedChange = {}
            )
            ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
            ElectronListItem(
                ListItemUiModel.Detail(
                    title = "Charger status",
                    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand)),
                    tag = TagUiModel.Text("Charging", tone = TagTone.Success)
                )
            )
        }
    }
}
