package com.electron.designsystem.components.chip.primitives

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes

/**
 * Pill-shaped chip block built on Material 3 FilterChip, restyled with
 * Electron tokens. Trailing content is a slot decided by the variant.
 */
@Composable
internal fun ChipPrimitive(
    text: String,
    isSelected: Boolean,
    isEnabled: Boolean,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconContentDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    val colors = ElectronTheme.colors
    FilterChip(
        selected = isSelected,
        enabled = isEnabled,
        onClick = onClick,
        label = { Text(text = text, style = ElectronTheme.typography.labelLarge) },
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(ElectronDimens.iconSm)
                )
            }
        },
        trailingIcon = trailingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = trailingIconContentDescription,
                    modifier = Modifier
                        .size(ElectronDimens.iconSm)
                        .then(
                            if (onTrailingIconClick != null) {
                                Modifier.clickable(onClick = onTrailingIconClick)
                            } else {
                                Modifier
                            }
                        )
                )
            }
        },
        shape = ElectronShapes.pill,
        border = BorderStroke(
            width = ElectronDimens.borderWidth,
            color = when {
                !isEnabled -> colors.border.subtle
                isSelected -> colors.brand.primary
                else -> colors.border.default
            }
        ),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = colors.background.surface,
            labelColor = colors.content.primary,
            iconColor = colors.content.secondary,
            selectedContainerColor = colors.interaction.selected,
            selectedLabelColor = colors.brand.primary,
            selectedTrailingIconColor = colors.brand.primary,
            disabledContainerColor = colors.interaction.disabledBackground,
            disabledLabelColor = colors.interaction.disabledContent
        ),
        modifier = modifier
            .height(ElectronDimens.chipHeight)
            .testTag(testTag)
    )
}
