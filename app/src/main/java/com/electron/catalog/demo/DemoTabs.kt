package com.electron.catalog.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.divider.ElectronDivider
import com.electron.designsystem.components.divider.models.DividerInset
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.emptystate.ElectronEmptyState
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.metriccard.ElectronMetricCard
import com.electron.designsystem.components.segmentedcontrol.ElectronSegmentedControl
import com.electron.designsystem.components.slider.ElectronSlider
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

// Tab contents. Each emits its components into the frame's column; every
// UI model comes from DemoMappers, every action goes up as a DemoIntent.

@Composable
internal fun DemoHomeTab(state: DemoState, onIntent: (DemoIntent) -> Unit) {
    if (!state.isConnected) {
        ElectronEmptyState(uiModel = OfflineUiModel, onRetryClick = { onIntent(DemoIntent.Reconnect) })
        return
    }
    ElectronSegmentedControl(
        uiModel = state.toRangeUiModel(),
        onOptionSelected = { onIntent(DemoIntent.SelectRange(it)) }
    )
    // Cards side by side share the tallest card's height.
    Row(
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
        modifier = Modifier.height(IntrinsicSize.Min)
    ) {
        ElectronMetricCard(state.toConsumptionUiModel(), modifier = Modifier.weight(1f).fillMaxHeight())
        ElectronMetricCard(state.toBatteryUiModel(), modifier = Modifier.weight(1f).fillMaxHeight())
    }
    ElectronCard(CardUiModel.Default(title = "Chargers")) {
        ElectronListItem(state.toChargerStatusUiModel())
        RowDivider()
        ElectronListItem(
            uiModel = state.toSmartChargingUiModel(),
            onCheckedChange = { onIntent(DemoIntent.SetSmartCharging(it)) }
        )
        RowDivider()
        ElectronListItem(uiModel = state.toAlertsUiModel(), onClick = { onIntent(DemoIntent.OpenAlerts) })
    }
    ElectronButton(uiModel = DisconnectButtonUiModel, onClick = { onIntent(DemoIntent.RequestDisconnect) })
}

@Composable
internal fun DemoChargingTab(state: DemoState, onIntent: (DemoIntent) -> Unit) {
    if (!state.isConnected) {
        ElectronEmptyState(
            uiModel = ChargingOfflineUiModel,
            onPrimaryActionClick = { onIntent(DemoIntent.SelectTab(DemoTab.Home.ordinal)) }
        )
        return
    }
    ElectronMetricCard(state.toSessionBatteryUiModel())
    ElectronCard(CardUiModel.Default(title = "Session")) {
        state.toSessionRowsUiModels().forEachIndexed { index, row ->
            if (index > 0) RowDivider(inset = DividerInset.Both)
            ElectronListItem(row)
        }
    }
    ElectronCard(CardUiModel.Default()) {
        Column(modifier = Modifier.padding(ElectronSpacing.lg)) {
            ElectronSlider(
                uiModel = state.toChargeLimitUiModel(),
                onValueChange = { onIntent(DemoIntent.SetChargeLimit(it)) }
            )
        }
    }
    ElectronButton(uiModel = state.toChargingButtonUiModel(), onClick = { onIntent(DemoIntent.ToggleCharging) })
}

@Composable
internal fun DemoAccountTab(
    state: DemoState,
    isDarkTheme: Boolean,
    onIntent: (DemoIntent) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.lg),
        modifier = Modifier.padding(vertical = ElectronSpacing.sm)
    ) {
        ElectronAvatar(ProfileAvatarUiModel)
        Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.xxs)) {
            Text(
                text = "Alex Martin",
                style = ElectronTheme.typography.titleLarge,
                color = ElectronTheme.colors.content.primary
            )
            Text(
                text = "alex.martin@volt.app",
                style = ElectronTheme.typography.bodyMedium,
                color = ElectronTheme.colors.content.secondary
            )
        }
    }
    ElectronCard(CardUiModel.Default(title = "Preferences")) {
        ElectronListItem(uiModel = darkThemeRowUiModel(isDarkTheme), onCheckedChange = onDarkThemeChange)
        RowDivider()
        ElectronListItem(
            uiModel = state.toNotificationsRowUiModel(),
            onCheckedChange = { onIntent(DemoIntent.SetNotifications(it)) }
        )
    }
    ElectronCard(CardUiModel.Default(title = "Billing")) {
        ElectronListItem(PaymentRowUiModel)
        RowDivider()
        ElectronListItem(TariffRowUiModel)
    }
}

@Composable
private fun RowDivider(inset: DividerInset = DividerInset.Start) {
    ElectronDivider(DividerUiModel.Horizontal(inset = inset))
}
