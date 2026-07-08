package com.electron.designsystem.components.toggle.primitives

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun electronSwitchColors(): SwitchColors {
    val c = ElectronTheme.colors
    return SwitchDefaults.colors(
        checkedThumbColor = c.brand.onPrimary,
        checkedTrackColor = c.brand.primary,
        uncheckedThumbColor = c.background.surface,
        uncheckedTrackColor = c.border.strong,
        uncheckedBorderColor = c.border.strong,
        disabledCheckedThumbColor = c.interaction.disabledContent,
        disabledCheckedTrackColor = c.interaction.disabledBackground,
        disabledUncheckedThumbColor = c.interaction.disabledContent,
        disabledUncheckedTrackColor = c.interaction.disabledBackground
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
    Switch(
        checked = isChecked,
        enabled = isEnabled,
        onCheckedChange = onCheckedChange,
        colors = electronSwitchColors(),
        modifier = modifier.testTag(testTag)
    )
}
