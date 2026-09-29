package com.electron.designsystem.components.listitem.models

import androidx.compose.runtime.Immutable
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.tag.models.TagUiModel

/**
 * Leading visual of a list row. Reuses the UI models of existing Electron
 * components, so a row renders exactly like a standalone avatar or icon.
 */
public sealed class ListItemLeading {

    @Immutable
    public data class Avatar(val uiModel: AvatarUiModel) : ListItemLeading()

    @Immutable
    public data class Icon(val uiModel: IconUiModel.Default) : ListItemLeading()
}

/**
 * List item UI models. Each variant describes one row purpose; the
 * trailing area (chevron, switch, value, tag) is decided by the variant,
 * never by the feature.
 */
public sealed class ListItemUiModel {

    /** Tappable row leading to another screen: trailing chevron. */
    @Immutable
    public data class Navigation(
        val title: String,
        val subtitle: String? = null,
        val leading: ListItemLeading? = null,
        val valueText: String? = null,
        val badge: BadgeUiModel? = null,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_list_item_navigation"
    ) : ListItemUiModel()

    /** Setting row with a trailing switch; tapping the row toggles it. */
    @Immutable
    public data class Toggle(
        val title: String,
        val isChecked: Boolean,
        val subtitle: String? = null,
        val leading: ListItemLeading? = null,
        val isEnabled: Boolean = true,
        val testTag: String = "electron_list_item_toggle"
    ) : ListItemUiModel()

    /** Read-only row showing a value or a status tag. */
    @Immutable
    public data class Detail(
        val title: String,
        val subtitle: String? = null,
        val leading: ListItemLeading? = null,
        val valueText: String? = null,
        val tag: TagUiModel? = null,
        val testTag: String = "electron_list_item_detail"
    ) : ListItemUiModel()
}
