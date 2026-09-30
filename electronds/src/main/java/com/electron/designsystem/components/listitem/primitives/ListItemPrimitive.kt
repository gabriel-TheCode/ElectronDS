package com.electron.designsystem.components.listitem.primitives

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Row layout for list items: leading slot, title/subtitle column, trailing
 * slot. Clickable only when [onClick] is provided; when [toggleValue] is
 * set the whole row is one toggleable node (a switch row is a single focus
 * stop for TalkBack, announced with its on/off state).
 *
 * The pressed/focused highlight is inset [ElectronSpacing.xs] from the
 * container edges and rounded, like menu items, instead of a square band
 * glued to the sides of the card. The content keeps its 16dp margins.
 *
 * The leading slot is at least one medium avatar wide, so titles line up
 * down a list whether a row starts with a 40dp avatar or a 20dp icon.
 */
@Composable
internal fun ListItemPrimitive(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color,
    subtitle: String?,
    subtitleStyle: TextStyle,
    subtitleColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    role: Role? = null,
    toggleValue: Boolean? = null,
    onClick: (() -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable RowScope.() -> Unit)? = null
) {
    val clickModifier = when {
        onClick != null && toggleValue != null -> Modifier.toggleable(
            value = toggleValue,
            enabled = isEnabled,
            role = role,
            onValueChange = { onClick() }
        )
        onClick != null -> Modifier.clickable(enabled = isEnabled, role = role, onClick = onClick)
        else -> Modifier
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = ElectronDimens.listItemMinHeight)
            .padding(horizontal = ElectronSpacing.xs)
            .clip(ElectronShapes.control)
            .then(clickModifier)
            .padding(horizontal = ElectronSpacing.md, vertical = ElectronSpacing.sm)
            .testTag(testTag)
    ) {
        if (leading != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.widthIn(min = ElectronDimens.avatarMd)
            ) {
                leading()
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = titleStyle,
                color = titleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = subtitleStyle,
                    color = subtitleColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (trailing != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
                content = trailing
            )
        }
    }
}
