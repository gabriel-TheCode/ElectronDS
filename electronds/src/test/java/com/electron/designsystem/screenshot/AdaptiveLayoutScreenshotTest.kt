package com.electron.designsystem.screenshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LinkOff
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.bottomsheet.models.BottomSheetUiModel
import com.electron.designsystem.components.bottomsheet.variants.BottomSheetDefaultBody
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.dialog.variants.DialogBody
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
import com.electron.designsystem.components.navigationbar.ElectronNavigationBar
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.models.NavigationItem
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.components.segmentedcontrol.ElectronSegmentedControl
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.components.topbar.ElectronTopBar
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.layout.ElectronScaffold
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Full screens on phone, tablet and TV, in both themes. These snapshots
 * guard the adaptive decisions: bottom bar vs rail, readable widths,
 * capped dialogs and sheets. They do not repeat component states (the
 * gallery test covers those).
 */
@RunWith(Parameterized::class)
internal class AdaptiveLayoutScreenshotTest(
    private val theme: ThemeVariant,
    private val device: DeviceVariant
) {

    companion object {
        /** Platform dialog width on large screens (Material's default max). */
        private val DialogMaxWidth = 560.dp

        /** ModalBottomSheet's default max width on large screens. */
        private val SheetMaxWidth = 640.dp

        @JvmStatic
        @Parameterized.Parameters(name = "{0}-{1}")
        fun parameters(): List<Array<Any>> =
            ThemeVariant.entries.flatMap { theme -> DeviceVariant.entries.map { device -> arrayOf<Any>(theme, device) } }
    }

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = device.config,
        renderingMode = SessionParams.RenderingMode.NORMAL,
        showSystemUi = false,
        maxPercentDifference = MAX_PERCENT_DIFFERENCE
    )

    private val destinations = listOf(
        NavigationItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
        NavigationItem("Charging", Icons.Outlined.Bolt, Icons.Filled.Bolt, badge = BadgeUiModel.Dot()),
        NavigationItem("Account", Icons.Outlined.Person, Icons.Filled.Person)
    )

    @Test
    fun dashboard() = paparazzi.electronScreen(theme) {
        AdaptiveFrame { DashboardContent() }
    }

    @Test
    fun emptyState() = paparazzi.electronScreen(theme) {
        AdaptiveFrame {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                ElectronEmptyState(
                    EmptyStateUiModel.Default(
                        icon = Icons.Outlined.EvStation,
                        title = "No charging sessions yet",
                        message = "Plug in your vehicle to start tracking your consumption and costs.",
                        primaryActionLabel = "Find a station",
                        secondaryActionLabel = "Learn more"
                    )
                )
            }
        }
    }

    /** Dialog and sheet over the scrim, at the widths the platform gives them. */
    @Test
    fun overlays() = paparazzi.electronScreen(theme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ElectronTheme.colors.background.canvas)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ElectronTheme.colors.background.scrim)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ElectronSpacing.xl)
            ) {
                DialogBody(
                    title = "Disconnect charger?",
                    message = "Scheduled charging sessions will be cancelled.",
                    confirmLabel = "Disconnect",
                    dismissLabel = "Cancel",
                    icon = Icons.Outlined.LinkOff,
                    isDestructive = true,
                    testTag = "dialog",
                    onConfirmClick = {},
                    onDismissClick = {},
                    modifier = Modifier.widthIn(max = DialogMaxWidth)
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .widthIn(max = SheetMaxWidth)
                    .background(ElectronTheme.colors.background.surfaceRaised, ElectronShapes.sheet)
            ) {
                BottomSheetDefaultBody(
                    uiModel = BottomSheetUiModel.Default(title = "Charging schedule", resetLabel = "Reset"),
                    onCloseClick = {},
                    onResetClick = {}
                ) {
                    ElectronListItem(
                        ListItemUiModel.Toggle(title = "Off-peak only", subtitle = "22:00 to 06:00", isChecked = true)
                    )
                }
            }
        }
    }

    /** Bottom bar on compact screens, rail beside the content on tablet and TV. */
    @Composable
    private fun AdaptiveFrame(content: @Composable () -> Unit) {
        val topBar = @Composable {
            ElectronTopBar(
                TopBarUiModel.Large(
                    title = "Dashboard",
                    action = TopBarAction(Icons.Outlined.Notifications, "Notifications", badge = BadgeUiModel.Dot())
                )
            )
        }
        if (device.isCompact) {
            ElectronScaffold(
                topBar = topBar,
                bottomBar = {
                    ElectronNavigationBar(NavigationBarUiModel.Bottom(destinations, selectedIndex = 0), onItemSelected = {})
                }
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) { content() }
            }
        } else {
            Row(modifier = Modifier.background(ElectronTheme.colors.background.canvas)) {
                ElectronNavigationBar(NavigationBarUiModel.Rail(destinations, selectedIndex = 0), onItemSelected = {})
                ElectronScaffold(topBar = topBar) { padding ->
                    Box(modifier = Modifier.padding(padding)) { content() }
                }
            }
        }
    }

    @Composable
    private fun DashboardContent() {
        Column(
            verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
            modifier = Modifier.padding(horizontal = ElectronSpacing.lg)
        ) {
            ElectronSegmentedControl(
                SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = 1),
                onOptionSelected = {}
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
                modifier = Modifier.height(IntrinsicSize.Min)
            ) {
                ElectronMetricCard(
                    MetricCardUiModel.Default(
                        label = "Consumption",
                        value = "96",
                        unit = "kWh",
                        icon = IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand),
                        delta = MetricDelta("-8%", MetricTrend.Down, MetricSentiment.Positive)
                    ),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                ElectronMetricCard(
                    MetricCardUiModel.Progress(
                        label = "Battery",
                        value = "78",
                        unit = "%",
                        progress = 0.78f,
                        tone = ProgressTone.Success,
                        icon = IconUiModel.Default(Icons.Outlined.BatteryChargingFull, tone = IconTone.Success)
                    ),
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
            ElectronCard(CardUiModel.Default(title = "Chargers", actionLabel = "See all")) {
                ElectronListItem(
                    ListItemUiModel.Detail(
                        title = "Home charger",
                        subtitle = "Wallbox 22 kW",
                        leading = ListItemLeading.Avatar(AvatarUiModel.Default(Icons.Outlined.EvStation, AvatarSize.Md, AvatarTone.Brand)),
                        tag = TagUiModel.Text("Charging", tone = TagTone.Success)
                    )
                )
                ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
                ElectronListItem(
                    ListItemUiModel.Toggle(
                        title = "Smart charging",
                        subtitle = "Charge during off-peak hours",
                        isChecked = true,
                        leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Schedule, tone = IconTone.Muted))
                    )
                )
            }
        }
    }
}
