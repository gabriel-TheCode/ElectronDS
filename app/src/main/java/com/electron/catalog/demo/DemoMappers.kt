package com.electron.catalog.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.LinkOff
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Sell
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.badge.models.BadgeTone
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.dialog.models.DialogUiModel
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.metriccard.models.MetricCardUiModel
import com.electron.designsystem.components.metriccard.models.MetricDelta
import com.electron.designsystem.components.metriccard.models.MetricSentiment
import com.electron.designsystem.components.metriccard.models.MetricTrend
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.models.NavigationItem
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.slider.models.SliderUiModel
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarNavigation
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import kotlin.math.roundToInt

// Mappers: pure functions from the screen state to component UI models.
// The screen composables only place components; every decision about what
// a component shows lives here.

// Frame

internal fun DemoState.toTopBarUiModel() = TopBarUiModel.Large(
    title = tab.title,
    navigation = TopBarNavigation.Back,
    navigationContentDescription = "Back to home",
    action = TopBarAction(
        icon = Icons.Outlined.Notifications,
        contentDescription = "Alerts",
        badge = if (pendingAlerts > 0) BadgeUiModel.Dot(contentDescription = "$pendingAlerts new alerts") else null
    )
)

private fun DemoState.navigationItems() = DemoTab.entries.map { tab ->
    NavigationItem(
        label = tab.label,
        icon = tab.icon,
        selectedIcon = tab.selectedIcon,
        badge = if (tab == DemoTab.Charging && isCharging) BadgeUiModel.Dot(tone = BadgeTone.Success, contentDescription = "Charging") else null
    )
}

internal fun DemoState.toBottomBarUiModel() = NavigationBarUiModel.Bottom(items = navigationItems(), selectedIndex = tab.ordinal)

internal fun DemoState.toRailUiModel() = NavigationBarUiModel.Rail(items = navigationItems(), selectedIndex = tab.ordinal)

// Home tab

internal fun DemoState.toRangeUiModel() = SegmentedControlUiModel.Default(
    options = ConsumptionRange.entries.map { it.label },
    selectedIndex = range.ordinal
)

internal fun DemoState.toConsumptionUiModel() = MetricCardUiModel.Default(
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

internal fun DemoState.toBatteryUiModel() = MetricCardUiModel.Progress(
    label = "Battery",
    value = batteryLevel.percent().toString(),
    unit = "%",
    progress = batteryLevel,
    tone = if (batteryLevel < 0.2f) ProgressTone.Error else ProgressTone.Success,
    icon = IconUiModel.Default(Icons.Outlined.BatteryChargingFull, tone = IconTone.Success)
)

internal fun DemoState.toChargerStatusUiModel() = ListItemUiModel.Detail(
    title = "Home charger",
    subtitle = "Wallbox 22 kW",
    leading = ListItemLeading.Avatar(AvatarUiModel.Default(Icons.Outlined.EvStation, AvatarSize.Md, AvatarTone.Brand)),
    tag = chargingTag()
)

internal fun DemoState.toSmartChargingUiModel() = ListItemUiModel.Toggle(
    title = "Smart charging",
    subtitle = "Charge during off-peak hours",
    isChecked = isSmartChargingEnabled,
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Schedule, tone = IconTone.Muted))
)

internal fun DemoState.toAlertsUiModel() = ListItemUiModel.Navigation(
    title = "Alerts",
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Notifications, tone = IconTone.Muted)),
    badge = if (pendingAlerts > 0) BadgeUiModel.Count(pendingAlerts) else null
)

internal val DisconnectButtonUiModel = ButtonUiModel.Secondary(
    text = "Disconnect charger",
    state = ButtonState.Error,
    isFullWidth = true
)

internal val DisconnectDialogUiModel = DialogUiModel.Destructive(
    title = "Disconnect charger?",
    message = "Scheduled charging sessions will be cancelled.",
    confirmLabel = "Disconnect",
    dismissLabel = "Cancel",
    icon = Icons.Outlined.LinkOff
)

internal val OfflineUiModel = EmptyStateUiModel.Error(
    icon = Icons.Outlined.LinkOff,
    title = "Charger disconnected",
    message = "Reconnect it to see live consumption and control charging.",
    retryLabel = "Reconnect"
)

// Charging tab

internal fun DemoState.toSessionBatteryUiModel() = MetricCardUiModel.Progress(
    label = "Battery",
    value = batteryLevel.percent().toString(),
    unit = "%",
    progress = batteryLevel,
    tone = ProgressTone.Success,
    icon = IconUiModel.Default(Icons.Outlined.BatteryChargingFull, tone = IconTone.Success),
    caption = if (isCharging) "Reaches ${chargeLimit.percent()}% in ${minutesToLimit()} min" else "Paused at ${batteryLevel.percent()}%"
)

internal fun DemoState.toSessionRowsUiModels(): List<ListItemUiModel.Detail> = listOf(
    ListItemUiModel.Detail(title = "Status", tag = chargingTag()),
    ListItemUiModel.Detail(title = "Power", valueText = if (isCharging) "11 kW" else "0 kW"),
    ListItemUiModel.Detail(title = "Energy added", valueText = "18.4 kWh"),
    ListItemUiModel.Detail(title = "Cost so far", valueText = "€4.12")
)

internal fun DemoState.toChargeLimitUiModel() = SliderUiModel.Stepped(
    value = chargeLimit,
    steps = ChargeLimitSteps,
    valueRangeStart = ChargeLimitMin,
    valueRangeEnd = ChargeLimitMax,
    label = "Charge limit",
    valueText = "${chargeLimit.percent()}%"
)

internal fun DemoState.toChargingButtonUiModel(): ButtonUiModel = if (isCharging) {
    ButtonUiModel.Secondary(text = "Pause charging", isFullWidth = true)
} else {
    ButtonUiModel.Primary(text = "Resume charging", isFullWidth = true)
}

internal val ChargingOfflineUiModel = EmptyStateUiModel.Default(
    icon = Icons.Outlined.EvStation,
    title = "No charger connected",
    message = "Reconnect your charger from the dashboard to start a session.",
    primaryActionLabel = "Go to dashboard"
)

// Account tab

internal val ProfileAvatarUiModel = AvatarUiModel.Initials(initials = "AM", size = AvatarSize.Lg, tone = AvatarTone.Brand)

internal fun darkThemeRowUiModel(isDarkTheme: Boolean) = ListItemUiModel.Toggle(
    title = "Dark theme",
    isChecked = isDarkTheme,
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.DarkMode, tone = IconTone.Muted))
)

internal fun DemoState.toNotificationsRowUiModel() = ListItemUiModel.Toggle(
    title = "Charging notifications",
    subtitle = "Session start, end and faults",
    isChecked = areNotificationsEnabled,
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Notifications, tone = IconTone.Muted))
)

internal val PaymentRowUiModel = ListItemUiModel.Navigation(
    title = "Payment method",
    valueText = "Visa •• 4242",
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.CreditCard, tone = IconTone.Muted))
)

internal val TariffRowUiModel = ListItemUiModel.Navigation(
    title = "Tariff",
    valueText = "Off-peak",
    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Sell, tone = IconTone.Muted))
)

// Shared

private fun DemoState.chargingTag() = when {
    !isConnected -> TagUiModel.Text("Offline", tone = TagTone.Neutral)
    isCharging -> TagUiModel.Text("Charging", tone = TagTone.Success)
    else -> TagUiModel.Text("Paused", tone = TagTone.Warning)
}

private fun Float.percent(): Int = (this * 100).roundToInt()

/** 11 kW into a 60 kWh battery: about 0.3% per minute. */
private fun DemoState.minutesToLimit(): Int = ((chargeLimit - batteryLevel).coerceAtLeast(0f) * 100 / 0.3f).roundToInt()
