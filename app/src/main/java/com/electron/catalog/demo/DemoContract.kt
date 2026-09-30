package com.electron.catalog.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.electron.designsystem.components.metriccard.models.MetricTrend

/**
 * Volt, the demo app: an EV charging companion built only from Electron
 * components. This file is its MVI contract: state, intents and reducer.
 */
enum class DemoTab(
    val label: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    Home("Home", "Dashboard", Icons.Outlined.Home, Icons.Filled.Home),
    Charging("Charging", "Charging", Icons.Outlined.Bolt, Icons.Filled.Bolt),
    Account("Account", "Account", Icons.Outlined.Person, Icons.Filled.Person)
}

enum class ConsumptionRange(val label: String, val kwh: Int, val delta: String, val trend: MetricTrend) {
    Day("Day", 14, "+3%", MetricTrend.Up),
    Week("Week", 96, "-8%", MetricTrend.Down),
    Month("Month", 342, "-12%", MetricTrend.Down)
}

data class DemoState(
    val tab: DemoTab = DemoTab.Home,
    val isConnected: Boolean = true,
    val range: ConsumptionRange = ConsumptionRange.Week,
    val batteryLevel: Float = 0.78f,
    val isCharging: Boolean = true,
    /** Target charge, from 50% to 100% in steps of 10. */
    val chargeLimit: Float = 0.8f,
    val isSmartChargingEnabled: Boolean = true,
    val areNotificationsEnabled: Boolean = true,
    val pendingAlerts: Int = 2,
    val isDisconnectDialogVisible: Boolean = false
)

sealed interface DemoIntent {
    data class SelectTab(val index: Int) : DemoIntent
    data class SelectRange(val index: Int) : DemoIntent
    data class SetSmartCharging(val isEnabled: Boolean) : DemoIntent
    data class SetNotifications(val isEnabled: Boolean) : DemoIntent
    data class SetChargeLimit(val limit: Float) : DemoIntent
    data object ToggleCharging : DemoIntent
    data object OpenAlerts : DemoIntent
    data object RequestDisconnect : DemoIntent
    data object ConfirmDisconnect : DemoIntent
    data object DismissDisconnect : DemoIntent
    data object Reconnect : DemoIntent
}

internal fun DemoState.reduce(intent: DemoIntent): DemoState = when (intent) {
    is DemoIntent.SelectTab -> copy(tab = DemoTab.entries[intent.index.coerceIn(0, DemoTab.entries.lastIndex)])
    is DemoIntent.SelectRange -> copy(range = ConsumptionRange.entries[intent.index.coerceIn(0, ConsumptionRange.entries.lastIndex)])
    is DemoIntent.SetSmartCharging -> copy(isSmartChargingEnabled = intent.isEnabled)
    is DemoIntent.SetNotifications -> copy(areNotificationsEnabled = intent.isEnabled)
    is DemoIntent.SetChargeLimit -> copy(chargeLimit = intent.limit.coerceIn(ChargeLimitMin, ChargeLimitMax))
    DemoIntent.ToggleCharging -> if (isConnected) copy(isCharging = !isCharging) else this
    DemoIntent.OpenAlerts -> copy(pendingAlerts = 0)
    DemoIntent.RequestDisconnect -> copy(isDisconnectDialogVisible = true)
    DemoIntent.ConfirmDisconnect -> copy(isConnected = false, isCharging = false, isDisconnectDialogVisible = false)
    DemoIntent.DismissDisconnect -> copy(isDisconnectDialogVisible = false)
    DemoIntent.Reconnect -> copy(isConnected = true)
}

internal const val ChargeLimitMin = 0.5f
internal const val ChargeLimitMax = 1f

/** 60, 70, 80, 90 between the 50 and 100 ends. */
internal const val ChargeLimitSteps = 4
