package com.electron.designsystem.components.fab.primitives

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronShapes

@Composable
internal fun ExtendedFabPrimitive(
    text: String,
    icon: ImageVector,
    iconContentDescription: String?,
    isExpanded: Boolean,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = ElectronTheme.colors
    ExtendedFloatingActionButton(
        onClick = onClick,
        expanded = isExpanded,
        shape = ElectronShapes.pill,
        containerColor = colors.brand.primary,
        contentColor = colors.brand.onPrimary,
        elevation = FloatingActionButtonDefaults.elevation(ElectronElevation.floating),
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier.size(ElectronDimens.iconLg)
            )
        },
        text = { Text(text = text, style = ElectronTheme.typography.labelLarge) },
        modifier = modifier.testTag(testTag)
    )
}

@Composable
internal fun CompactFabPrimitive(
    icon: ImageVector,
    iconContentDescription: String?,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = ElectronTheme.colors
    FloatingActionButton(
        onClick = onClick,
        shape = ElectronShapes.pill,
        containerColor = colors.brand.primary,
        contentColor = colors.brand.onPrimary,
        elevation = FloatingActionButtonDefaults.elevation(ElectronElevation.floating),
        modifier = modifier.testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconContentDescription,
            modifier = Modifier.size(ElectronDimens.iconLg)
        )
    }
}
