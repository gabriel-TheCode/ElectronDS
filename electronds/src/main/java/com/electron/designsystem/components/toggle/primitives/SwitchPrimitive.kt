package com.electron.designsystem.components.toggle.primitives

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.utils.focusRing

/**
 * Off is an outlined, sunken track with a muted thumb; on is a filled
 * brand track. The two states differ in fill, thumb size and the check
 * glyph, never in color alone.
 */
@Composable
internal fun electronSwitchColors(): SwitchColors {
    val c = ElectronTheme.colors
    return SwitchDefaults.colors(
        checkedThumbColor = c.brand.onPrimary,
        checkedTrackColor = c.brand.primaryFill,
        checkedBorderColor = c.brand.primaryFill,
        checkedIconColor = c.brand.primaryFill,
        uncheckedThumbColor = c.content.muted,
        uncheckedTrackColor = c.background.surfaceSunken,
        uncheckedBorderColor = c.border.strong,
        disabledCheckedThumbColor = c.background.surface,
        disabledCheckedTrackColor = c.interaction.disabledContent,
        disabledCheckedBorderColor = c.interaction.disabledContent,
        disabledCheckedIconColor = c.interaction.disabledContent,
        disabledUncheckedThumbColor = c.interaction.disabledContent,
        disabledUncheckedTrackColor = c.interaction.disabledBackground,
        disabledUncheckedBorderColor = c.interaction.disabledContent
    )
}

@Composable
internal fun SwitchPrimitive(
    isChecked: Boolean,
    isEnabled: Boolean,
    testTag: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Switch(
        checked = isChecked,
        enabled = isEnabled,
        onCheckedChange = onCheckedChange,
        colors = electronSwitchColors(),
        interactionSource = interactionSource,
        thumbContent = if (isChecked) {
            {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    modifier = Modifier.size(ElectronDimens.iconXs)
                )
            }
        } else {
            null
        },
        modifier = modifier
            .focusRing(interactionSource, ElectronShapes.pill)
            .testTag(testTag)
    )
}
