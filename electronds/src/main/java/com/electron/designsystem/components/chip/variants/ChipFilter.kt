package com.electron.designsystem.components.chip.variants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.chip.primitives.ChipPrimitive

/**
 * Filter chip: swaps label and trailing affordance based on selection.
 * The selection itself is data provided by the screen, never local state.
 * The idle affordance is a chevron (same glyph family as list rows), not
 * Material's filled drop-down triangle.
 */
@Composable
internal fun ChipFilter(
    uiModel: ChipUiModel.Filter,
    onClick: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    ChipPrimitive(
        // A selected chip without a value falls back to its default label rather than rendering empty.
        text = if (uiModel.isSelected && uiModel.valueText.isNotBlank()) uiModel.valueText else uiModel.defaultText,
        isSelected = uiModel.isSelected,
        isEnabled = uiModel.isEnabled,
        trailingIcon = if (uiModel.isSelected) Icons.Filled.Cancel else Icons.Outlined.KeyboardArrowDown,
        trailingIconContentDescription = if (uiModel.isSelected) {
            uiModel.clearIconContentDescription
        } else {
            uiModel.expandIconContentDescription
        },
        onTrailingIconClick = if (uiModel.isSelected) {
            onClear
        } else {
            null
        },
        onClick = onClick,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
