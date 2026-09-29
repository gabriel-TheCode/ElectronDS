package com.electron.designsystem.components.chip.primitives

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.utils.focusRing
import com.electron.designsystem.utils.pressScale

/**
 * Pill-shaped chip block built on Material 3 FilterChip, restyled with
 * Electron tokens. Trailing content is a slot decided by the variant.
 *
 * Selection is carried by the tinted container and the brand label only:
 * a selected chip drops its outline instead of adding a second, stronger
 * one, so a row of chips stays calm and the selected one still stands out.
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
    val interactionSource = remember { MutableInteractionSource() }
    FilterChip(
        selected = isSelected,
        enabled = isEnabled,
        onClick = onClick,
        interactionSource = interactionSource,
        label = {
            Text(
                text = text,
                style = ElectronTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
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
                if (onTrailingIconClick != null) {
                    // 24dp hit area around the 16dp glyph: the largest target that
                    // fits a 32dp chip, instead of a tap area the size of the glyph.
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(ElectronDimens.iconLg)
                            .clip(ElectronShapes.pill)
                            .clickable(
                                enabled = isEnabled,
                                role = Role.Button,
                                onClickLabel = trailingIconContentDescription,
                                onClick = onTrailingIconClick
                            )
                    ) {
                        Icon(
                            imageVector = it,
                            contentDescription = trailingIconContentDescription,
                            modifier = Modifier.size(ElectronDimens.iconSm)
                        )
                    }
                } else {
                    Icon(
                        imageVector = it,
                        contentDescription = trailingIconContentDescription,
                        modifier = Modifier.size(ElectronDimens.iconSm)
                    )
                }
            }
        },
        shape = ElectronShapes.pill,
        border = BorderStroke(
            width = ElectronDimens.borderWidth,
            color = when {
                !isEnabled -> colors.border.subtle
                isSelected -> colors.interaction.selected
                else -> colors.border.default
            }
        ),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = colors.background.surface,
            labelColor = colors.content.primary,
            iconColor = colors.content.secondary,
            selectedContainerColor = colors.interaction.selected,
            selectedLabelColor = colors.brand.primaryStrong,
            selectedLeadingIconColor = colors.brand.primaryStrong,
            selectedTrailingIconColor = colors.brand.primaryStrong,
            disabledContainerColor = colors.interaction.disabledBackground,
            disabledLabelColor = colors.interaction.disabledContent,
            disabledLeadingIconColor = colors.interaction.disabledContent,
            disabledTrailingIconColor = colors.interaction.disabledContent
        ),
        modifier = modifier
            .height(ElectronDimens.chipHeight)
            .pressScale(interactionSource, enabled = isEnabled)
            .focusRing(interactionSource, ElectronShapes.pill)
            .testTag(testTag)
    )
}
