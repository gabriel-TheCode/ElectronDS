package com.electron.designsystem.components.tooltip

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.tooltip.models.TooltipUiModel
import com.electron.designsystem.components.tooltip.variants.TooltipPlain
import com.electron.designsystem.components.tooltip.variants.TooltipPlainSurface
import com.electron.designsystem.components.tooltip.variants.TooltipRich
import com.electron.designsystem.components.tooltip.variants.TooltipRichSurface
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronTooltip
 *
 * Purpose: wrap an element ([content]) to explain it on long-press, hover
 * or focus. Use Plain to name icon-only controls, Rich for a short
 * explanation with an optional action.
 *
 * API:
 * - [uiModel]: variant (sealed [TooltipUiModel]).
 * - [onActionClick]: signal emitted by the action of a Rich tooltip. Only
 *   relevant when `actionLabel` is set.
 *
 * Usage:
 * ```
 * ElectronTooltip(TooltipUiModel.Plain("Settings")) {
 *     SettingsIconButton(onClick = viewModel::onSettingsClicked)
 * }
 * ```
 */
@Composable
public fun ElectronTooltip(
    uiModel: TooltipUiModel,
    modifier: Modifier = Modifier,
    onActionClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    when (uiModel) {
        is TooltipUiModel.Plain -> TooltipPlain(uiModel, modifier, content)
        is TooltipUiModel.Rich -> TooltipRich(uiModel, onActionClick, modifier, content)
    }
}

@Preview(showBackground = true)
@Composable
private fun ElectronTooltipPreview() {
    ElectronPreviewSurface {
        TooltipPlainSurface(TooltipUiModel.Plain("Charger settings"))
        TooltipRichSurface(
            TooltipUiModel.Rich(
                title = "Smart charging",
                text = "Charges when electricity is cheapest, and always finishes before your departure time.",
                actionLabel = "Learn more"
            ),
            onActionClick = {}
        )
    }
}
