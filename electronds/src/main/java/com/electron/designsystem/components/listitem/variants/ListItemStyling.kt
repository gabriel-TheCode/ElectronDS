package com.electron.designsystem.components.listitem.variants

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.icon.ElectronIcon
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.primitives.ListItemPrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun ListItemLeading.Render() {
    when (this) {
        is ListItemLeading.Avatar -> ElectronAvatar(uiModel)
        is ListItemLeading.Icon -> ElectronIcon(uiModel)
    }
}

internal data class ListItemTextColors(val title: Color, val subtitle: Color)

@Composable
internal fun listItemTextColors(isEnabled: Boolean): ListItemTextColors {
    val c = ElectronTheme.colors
    return if (isEnabled) {
        ListItemTextColors(c.content.primary, c.content.secondary)
    } else {
        ListItemTextColors(c.content.disabled, c.content.disabled)
    }
}

/** Shared row rendering: every variant resolves the same typography. */
@Composable
internal fun ListItemRow(
    title: String,
    subtitle: String?,
    leading: ListItemLeading?,
    isEnabled: Boolean,
    testTag: String,
    modifier: Modifier,
    role: Role? = null,
    onClick: (() -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null
) {
    val colors = listItemTextColors(isEnabled)
    ListItemPrimitive(
        title = title,
        titleStyle = ElectronTheme.typography.bodyLarge,
        titleColor = colors.title,
        subtitle = subtitle,
        subtitleStyle = ElectronTheme.typography.bodySmall,
        subtitleColor = colors.subtitle,
        isEnabled = isEnabled,
        role = role,
        onClick = onClick,
        leading = if (leading != null) {
            { leading.Render() }
        } else {
            null
        },
        trailing = trailing,
        testTag = testTag,
        modifier = modifier
    )
}
