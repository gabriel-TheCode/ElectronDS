package com.electron.catalog.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.LinkOff
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.dialog.ElectronDialog
import com.electron.designsystem.components.dialog.models.DialogUiModel
import com.electron.designsystem.components.divider.ElectronDivider
import com.electron.designsystem.components.divider.models.DividerInset
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.emptystate.ElectronEmptyState
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.metriccard.ElectronMetricCard
import com.electron.designsystem.components.metriccard.models.MetricCardUiModel
import com.electron.designsystem.components.metriccard.models.MetricDelta
import com.electron.designsystem.components.metriccard.models.MetricSentiment
import com.electron.designsystem.components.metriccard.models.MetricTrend
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.components.segmentedcontrol.ElectronSegmentedControl
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Feature-level example composing the complex components:
 * Screen UI state -> UI mappers -> component UI models -> signals back up.
 *
 * As in PayeeFormSection, a local state holder stands in for a ViewModel.
 */
private enum class ConsumptionRange(val label: String, val kwh: Int, val delta: String, val trend: MetricTrend) {
    Day("Day", 14, "+3%", MetricTrend.Up),
    Week("Week", 96, "-8%", MetricTrend.Down),
    Month("Month", 342, "-12%", MetricTrend.Down)
}

private data class ChargerDashboardUiState(
    val isConnected: Boolean = true,
    val range: ConsumptionRange = ConsumptionRange.Week,
    val batteryLevel: Float = 0.78f,
    val smartChargingEnabled: Boolean = true,
    val pendingAlerts: Int = 2,
    val showDisconnectDialog: Boolean = false
)

// Mappers: pure functions from screen state to component UI models.

private fun ChargerDashboardUiState.toRangeUiModel() = SegmentedControlUiModel.Default(
    options = ConsumptionRange.entries.map { it.label },
    selectedIndex = range.ordinal
)

private fun ChargerDashboardUiState.toConsumptionUiModel() = MetricCardUiModel.Default(
    label = "Consumption",
    value = range.kwh.toString(),
    unit = "kWh",
    icon = IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand),
    delta = MetricDelta(
        text = range.delta,
        trend = range.trend,
        // Lower consumption is good news.
        sentiment = if (range.trend == MetricTrend.Down) MetricSentiment.Positive else MetricSentiment.Negative
    )
)

private fun ChargerDashboardUiState.toBatteryUiModel() = MetricCardUiModel.Progress(
    label = "Battery",
    value = (batteryLevel * 100).toInt().toString(),
    unit = "%",
    progress = batteryLevel,
    tone = if (batteryLevel < 0.2f) ProgressTone.Error else ProgressTone.Success,
    icon = IconUiModel.Default(Icons.Outlined.BatteryChargingFull, tone = IconTone.Success)
)

private fun ChargerDashboardUiState.toStatusItemUiModel() = ListItemUiModel.Detail(
    title = "Home charger",
    subtitle = "Wallbox 22 kW",
    leading = ListItemLeading.Avatar(AvatarUiModel.Default(Icons.Outlined.EvStation, AvatarSize.Md, AvatarTone.Brand)),
    tag = if (isConnected) {
        TagUiModel.Text("Charging", tone = TagTone.Success)
    } else {
        TagUiModel.Text("Offline", tone = TagTone.Neutral)
    }
)

private fun ChargerDashboardUiState.toSmartChargingUiModel() = ListItemUiModel.Toggle(
    title = "Smart charging",
    subtitle = "Charge during off-peak hours",
    isChecked = smartChargingEnabled,
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Schedule, tone = IconTone.Muted))
)

private fun ChargerDashboardUiState.toAlertsUiModel() = ListItemUiModel.Navigation(
    title = "Alerts",
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Muted)),
    badge = if (pendingAlerts > 0) BadgeUiModel.Count(pendingAlerts) else null
)

@Composable
internal fun ChargerDashboardSection() {
    var state by rememberSaveable(stateSaver = ChargerDashboardSaver) { mutableStateOf(ChargerDashboardUiState()) }

    if (!state.isConnected) {
        ElectronEmptyState(
            uiModel = EmptyStateUiModel.Error(
                icon = Icons.Outlined.LinkOff,
                title = "Charger disconnected",
                message = "Reconnect it to see live consumption.",
                retryLabel = "Reconnect"
            ),
            onRetryClick = { state = state.copy(isConnected = true) }
        )
        return
    }

    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
        ElectronSegmentedControl(
            uiModel = state.toRangeUiModel(),
            onOptionSelected = { state = state.copy(range = ConsumptionRange.entries[it]) }
        )
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronMetricCard(state.toConsumptionUiModel(), modifier = Modifier.weight(1f))
            ElectronMetricCard(state.toBatteryUiModel(), modifier = Modifier.weight(1f))
        }
        ElectronCard(CardUiModel.Default()) {
            ElectronListItem(state.toStatusItemUiModel())
            ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
            ElectronListItem(
                uiModel = state.toSmartChargingUiModel(),
                onCheckedChange = { state = state.copy(smartChargingEnabled = it) }
            )
            ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
            ElectronListItem(
                uiModel = state.toAlertsUiModel(),
                onClick = { state = state.copy(pendingAlerts = 0) }
            )
        }
        ElectronButton(
            uiModel = ButtonUiModel.Secondary(text = "Disconnect charger", state = ButtonState.Error, isFullWidth = true),
            onClick = { state = state.copy(showDisconnectDialog = true) }
        )
    }

    if (state.showDisconnectDialog) {
        ElectronDialog(
            uiModel = DialogUiModel.Destructive(
                title = "Disconnect charger?",
                message = "Scheduled charging sessions will be cancelled.",
                confirmLabel = "Disconnect",
                dismissLabel = "Cancel",
                icon = Icons.Outlined.LinkOff
            ),
            onConfirmClick = { state = state.copy(isConnected = false, showDisconnectDialog = false) },
            onDismissRequest = { state = state.copy(showDisconnectDialog = false) }
        )
    }
}

private val ChargerDashboardSaver = listSaver<ChargerDashboardUiState, Any>(
    save = {
        listOf(it.isConnected, it.range.ordinal, it.batteryLevel, it.smartChargingEnabled, it.pendingAlerts, it.showDisconnectDialog)
    },
    restore = {
        ChargerDashboardUiState(
            isConnected = it[0] as Boolean,
            range = ConsumptionRange.entries[it[1] as Int],
            batteryLevel = it[2] as Float,
            smartChargingEnabled = it[3] as Boolean,
            pendingAlerts = it[4] as Int,
            showDisconnectDialog = it[5] as Boolean
        )
    }
)
