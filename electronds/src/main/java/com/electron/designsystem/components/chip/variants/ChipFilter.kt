package com.electron.designsystem.components.chip.variants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.chip.models.ChipSignal
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.chip.primitives.ChipPrimitive

/**
 * Filter chip: swaps label and trailing affordance based on selection.
 * The selection itself is data provided by the screen, never local state.
 */
@Composable
internal fun ChipFilter(
    uiModel: ChipUiModel.Filter,
    onSignal: (ChipSignal) -> Unit,
    modifier: Modifier = Modifier
) {
    ChipPrimitive(
        text = if (uiModel.isSelected) uiModel.valueText else uiModel.defaultText,
        isSelected = uiModel.isSelected,
        isEnabled = uiModel.isEnabled,
        trailingIcon = if (uiModel.isSelected) Icons.Filled.Cancel else Icons.Outlined.ArrowDropDown,
        trailingIconContentDescription = if (uiModel.isSelected) {
            uiModel.clearIconContentDescription
        } else {
            uiModel.expandIconContentDescription
        },
        onTrailingIconClick = if (uiModel.isSelected) {
            { onSignal(ChipSignal.Cleared) }
        } else {
            null
        },
        onClick = { onSignal(ChipSignal.Clicked) },
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
