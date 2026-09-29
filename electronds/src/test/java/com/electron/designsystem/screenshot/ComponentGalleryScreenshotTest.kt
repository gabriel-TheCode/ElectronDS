package com.electron.designsystem.screenshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BatteryChargingFull
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LinkOff
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.badge.ElectronBadge
import com.electron.designsystem.components.badge.models.BadgeTone
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.bottomsheet.models.BottomSheetUiModel
import com.electron.designsystem.components.bottomsheet.variants.BottomSheetDefaultBody
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardSeverity
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.checkbox.ElectronCheckbox
import com.electron.designsystem.components.checkbox.models.CheckboxUiModel
import com.electron.designsystem.components.chip.ElectronChip
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.dialog.variants.DialogBody
import com.electron.designsystem.components.divider.ElectronDivider
import com.electron.designsystem.components.divider.models.DividerInset
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.dropdown.ElectronDropdown
import com.electron.designsystem.components.dropdown.models.DropdownUiModel
import com.electron.designsystem.components.emptystate.ElectronEmptyState
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel
import com.electron.designsystem.components.fab.ElectronFab
import com.electron.designsystem.components.fab.models.FabUiModel
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.inputfield.ElectronInputField
import com.electron.designsystem.components.inputfield.models.InputFieldUiModel
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.menu.models.MenuItem
import com.electron.designsystem.components.menu.models.MenuUiModel
import com.electron.designsystem.components.menu.variants.MenuDefaultPanel
import com.electron.designsystem.components.metriccard.ElectronMetricCard
import com.electron.designsystem.components.metriccard.models.MetricCardUiModel
import com.electron.designsystem.components.metriccard.models.MetricDelta
import com.electron.designsystem.components.metriccard.models.MetricSentiment
import com.electron.designsystem.components.metriccard.models.MetricTrend
import com.electron.designsystem.components.navigationbar.ElectronNavigationBar
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.models.NavigationItem
import com.electron.designsystem.components.progress.ElectronProgressIndicator
import com.electron.designsystem.components.progress.models.ProgressSize
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.components.progress.models.ProgressUiModel
import com.electron.designsystem.components.radiobutton.ElectronRadioButton
import com.electron.designsystem.components.radiobutton.models.RadioButtonUiModel
import com.electron.designsystem.components.segmentedcontrol.ElectronSegmentedControl
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.sheetheader.ElectronSheetHeader
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.components.skeleton.ElectronSkeleton
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.slider.ElectronSlider
import com.electron.designsystem.components.slider.models.SliderUiModel
import com.electron.designsystem.components.tabs.ElectronTabs
import com.electron.designsystem.components.tabs.models.TabItem
import com.electron.designsystem.components.tabs.models.TabsUiModel
import com.electron.designsystem.components.tag.ElectronTag
import com.electron.designsystem.components.tag.models.TagSize
import com.electron.designsystem.components.tag.models.TagStyle
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.components.toggle.ElectronSwitch
import com.electron.designsystem.components.toggle.models.SwitchUiModel
import com.electron.designsystem.components.tooltip.models.TooltipUiModel
import com.electron.designsystem.components.tooltip.variants.TooltipPlainSurface
import com.electron.designsystem.components.tooltip.variants.TooltipRichSurface
import com.electron.designsystem.components.topbar.ElectronTopBar
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarNavigation
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * One gallery per component family, every state that has a distinct
 * appearance (default, pressed-free states: disabled, error, loading,
 * selected, success), in light and dark. Rendered on a phone in SHRINK
 * mode so each image is exactly as tall as its content.
 *
 * Overlays (dialog, sheet, menu, tooltip) are captured through their
 * internal surfaces: windows and popups cannot be rendered off-device.
 */
@RunWith(Parameterized::class)
internal class ComponentGalleryScreenshotTest(private val theme: ThemeVariant) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun parameters(): List<ThemeVariant> = ThemeVariant.entries.toList()
    }

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceVariant.Phone.config,
        renderingMode = SessionParams.RenderingMode.SHRINK,
        showSystemUi = false,
        maxPercentDifference = MAX_PERCENT_DIFFERENCE
    )

    @Test
    fun buttons() = paparazzi.electronGallery(theme) {
        Caption("Emphasis")
        ElectronButton(ButtonUiModel.Primary(text = "Primary", isFullWidth = true), onClick = {})
        ElectronButton(ButtonUiModel.Secondary(text = "Secondary", isFullWidth = true), onClick = {})
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronButton(ButtonUiModel.Tertiary(text = "Tertiary"), onClick = {})
            ElectronButton(ButtonUiModel.Link(text = "Link"), onClick = {})
            ElectronButton(ButtonUiModel.Tertiary(icon = IconUiModel.Default(Icons.Outlined.Settings, "Settings")), onClick = {})
        }
        Caption("States")
        ElectronButton(ButtonUiModel.Primary(text = "Saved", state = ButtonState.Success), onClick = {})
        ElectronButton(ButtonUiModel.Primary(text = "Delete", state = ButtonState.Error), onClick = {})
        ElectronButton(ButtonUiModel.Secondary(text = "Disconnect", state = ButtonState.Error), onClick = {})
        ElectronButton(ButtonUiModel.Loading(text = "Saving", isFullWidth = true), onClick = {})
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronButton(ButtonUiModel.Primary(text = "Disabled", isEnabled = false), onClick = {})
            ElectronButton(ButtonUiModel.Secondary(text = "Disabled", isEnabled = false), onClick = {})
        }
        Caption("Sizes and icons")
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronButton(ButtonUiModel.Primary(text = "Small", size = ButtonSize.Small), onClick = {})
            ElectronButton(ButtonUiModel.Primary(text = "Medium", size = ButtonSize.Medium), onClick = {})
            ElectronButton(
                ButtonUiModel.Secondary(text = "Add", icon = IconUiModel.Default(Icons.Outlined.Add)),
                onClick = {}
            )
        }
        Caption("FAB")
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronFab(FabUiModel.Extended(text = "New session", icon = Icons.Outlined.Add), onClick = {})
            ElectronFab(FabUiModel.Compact(icon = Icons.Outlined.Add, iconContentDescription = "Add"), onClick = {})
        }
    }

    @Test
    fun inputs() = paparazzi.electronGallery(theme) {
        ElectronInputField(
            InputFieldUiModel.Default(value = "CH93 0076 2011 6238 5295 7", label = "IBAN", helperText = "International format"),
            onValueChange = {}
        )
        ElectronInputField(InputFieldUiModel.Default(label = "Payee name", placeholder = "Jane Doe"), onValueChange = {})
        ElectronInputField(
            InputFieldUiModel.Default(value = "12", label = "IBAN", isError = true, errorText = "IBAN looks too short"),
            onValueChange = {}
        )
        ElectronInputField(InputFieldUiModel.Default(value = "Home", label = "Reference", isReadOnly = true), onValueChange = {})
        ElectronInputField(InputFieldUiModel.Default(label = "Disabled", placeholder = "Not editable", isEnabled = false), onValueChange = {})
        ElectronDropdown(
            DropdownUiModel.Default(label = "Tariff", options = listOf("Standard", "Off-peak"), selectedIndex = 1),
            onFieldClick = {}, onOptionSelected = {}, onDismissRequest = {}
        )
        ElectronDropdown(
            DropdownUiModel.Default(
                label = "Vehicle",
                options = listOf("Model 3"),
                placeholder = "Choose a vehicle",
                isError = true,
                errorText = "Select a vehicle to continue"
            ),
            onFieldClick = {}, onOptionSelected = {}, onDismissRequest = {}
        )
        ElectronSlider(SliderUiModel.Continuous(value = 0.4f, label = "Max power", valueText = "4.4 kW"), onValueChange = {})
        ElectronSlider(
            SliderUiModel.Stepped(value = 0.8f, steps = 3, valueRangeStart = 0.6f, valueRangeEnd = 1f, label = "Charge limit", valueText = "80%"),
            onValueChange = {}
        )
        ElectronSlider(SliderUiModel.Continuous(value = 0.6f, label = "Disabled", isEnabled = false), onValueChange = {})
    }

    @Test
    fun selectionControls() = paparazzi.electronGallery(theme) {
        ElectronSegmentedControl(
            SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = 1),
            onOptionSelected = {}
        )
        ElectronSegmentedControl(
            SegmentedControlUiModel.Default(options = listOf("kWh", "€"), selectedIndex = 0, isEnabled = false),
            onOptionSelected = {}
        )
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronChip(ChipUiModel.Filter(defaultText = "Period"), onClick = {})
            ElectronChip(ChipUiModel.Filter(defaultText = "Period", valueText = "30 days", isSelected = true), onClick = {})
            ElectronChip(ChipUiModel.Assist(text = "Filters", leadingIcon = Icons.Outlined.Tune), onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronChip(ChipUiModel.Filter(defaultText = "Disabled", isEnabled = false), onClick = {})
        }
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = true, label = "Monthly energy report"), onCheckedChange = {})
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = false, label = "I accept the terms", isError = true), onCheckedChange = {})
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = true, label = "Disabled", isEnabled = false), onCheckedChange = {})
        ElectronRadioButton(RadioButtonUiModel.Default(isSelected = true, label = "Off-peak"), onClick = {})
        ElectronRadioButton(RadioButtonUiModel.Default(isSelected = false, label = "Standard"), onClick = {})
        ElectronRadioButton(RadioButtonUiModel.Default(isSelected = false, label = "Disabled", isEnabled = false), onClick = {})
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.lg)) {
            ElectronSwitch(SwitchUiModel.Default(isChecked = true), onCheckedChange = {})
            ElectronSwitch(SwitchUiModel.Default(isChecked = false), onCheckedChange = {})
            ElectronSwitch(SwitchUiModel.Default(isChecked = true, isEnabled = false), onCheckedChange = {})
            ElectronSwitch(SwitchUiModel.Default(isChecked = false, isEnabled = false), onCheckedChange = {})
        }
    }

    @Test
    fun display() = paparazzi.electronGallery(theme) {
        Caption("Tags: every tone, tinted")
        TagTone.entries.chunked(4).forEach { tones ->
            Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs)) {
                tones.forEach { tone -> ElectronTag(TagUiModel.Text(tone.name, tone = tone, size = TagSize.Sm)) }
            }
        }
        Caption("Tags: styles")
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
            ElectronTag(TagUiModel.Text("Live", tone = TagTone.Success, style = TagStyle.Filled))
            ElectronTag(TagUiModel.Text("Pending", tone = TagTone.Warning, style = TagStyle.Outlined))
            ElectronTag(TagUiModel.Text("Failed", tone = TagTone.Error))
            ElectronTag(TagUiModel.Icon(Icons.Outlined.Bolt, "Charging", tone = TagTone.Brand))
        }
        Caption("Avatars and badges")
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Bolt, AvatarSize.Sm, AvatarTone.Brand))
            ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Payments, AvatarSize.Md, AvatarTone.Accent))
            ElectronAvatar(AvatarUiModel.Initials("GT", AvatarSize.Md, AvatarTone.Neutral))
            ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.LinkOff, AvatarSize.Lg, AvatarTone.Critical))
            ElectronBadge(BadgeUiModel.Dot())
            ElectronBadge(BadgeUiModel.Count(count = 4))
            ElectronBadge(BadgeUiModel.Count(count = 150, tone = BadgeTone.Brand))
        }
        Caption("Progress")
        ElectronProgressIndicator(ProgressUiModel.Linear(progress = 0.64f))
        ElectronProgressIndicator(ProgressUiModel.Linear(progress = 0.15f, tone = ProgressTone.Error))
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.lg)) {
            ElectronProgressIndicator(ProgressUiModel.Circular(progress = 0.8f, size = ProgressSize.Sm, tone = ProgressTone.Success))
            ElectronProgressIndicator(ProgressUiModel.Circular(progress = 0.35f, tone = ProgressTone.Accent))
            ElectronProgressIndicator(ProgressUiModel.Circular(progress = 0.5f, size = ProgressSize.Lg))
        }
        Caption("Dividers")
        ElectronDivider(DividerUiModel.Horizontal())
        ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Both))
    }

    @Test
    fun cardsAndLists() = paparazzi.electronGallery(theme) {
        ElectronCard(CardUiModel.Default(title = "Consumption", actionLabel = "See all")) {
            Text(
                text = "3 devices connected to the grid",
                style = ElectronTheme.typography.bodyMedium,
                color = ElectronTheme.colors.content.secondary,
                modifier = Modifier.padding(start = ElectronSpacing.lg, end = ElectronSpacing.lg, bottom = ElectronSpacing.lg)
            )
        }
        CardSeverity.entries.forEach { severity ->
            ElectronCard(CardUiModel.Status("Status message for ${severity.name.lowercase()}.", severity))
        }
        ElectronCard(CardUiModel.Default()) {
            ElectronListItem(
                ListItemUiModel.Navigation(
                    title = "Payment methods",
                    subtitle = "2 cards linked",
                    leading = ListItemLeading.Avatar(AvatarUiModel.Default(Icons.Outlined.Payments, AvatarSize.Md, AvatarTone.Brand)),
                    badge = BadgeUiModel.Count(1)
                ),
                onClick = {}
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
            ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
            ElectronListItem(
                ListItemUiModel.Detail(
                    title = "Charger status",
                    leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand)),
                    tag = TagUiModel.Text("Charging", tone = TagTone.Success)
                )
            )
            ElectronDivider(DividerUiModel.Horizontal(inset = DividerInset.Start))
            ElectronListItem(ListItemUiModel.Navigation(title = "Disabled row", isEnabled = false), onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronMetricCard(
                MetricCardUiModel.Default(
                    label = "Consumption",
                    value = "342",
                    unit = "kWh",
                    icon = IconUiModel.Default(Icons.Outlined.Bolt, tone = IconTone.Brand),
                    delta = MetricDelta("-12%", MetricTrend.Down, MetricSentiment.Positive),
                    caption = "vs last month"
                ),
                modifier = Modifier.weight(1f)
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
                modifier = Modifier.weight(1f)
            )
        }
    }

    @Test
    fun navigation() = paparazzi.electronGallery(theme) {
        val destinations = listOf(
            NavigationItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
            NavigationItem("Charging", Icons.Outlined.Bolt, Icons.Filled.Bolt, badge = BadgeUiModel.Dot()),
            NavigationItem("Account", Icons.Outlined.Person, Icons.Filled.Person)
        )
        ElectronTopBar(
            TopBarUiModel.Default(
                title = "Home charger",
                subtitle = "Connected",
                navigation = TopBarNavigation.Back,
                action = TopBarAction(Icons.Outlined.Settings, "Settings")
            )
        )
        ElectronTopBar(
            TopBarUiModel.Large(
                title = "Dashboard",
                action = TopBarAction(Icons.Outlined.Notifications, "Notifications", badge = BadgeUiModel.Dot())
            )
        )
        ElectronTabs(
            TabsUiModel.Fixed(
                tabs = listOf(TabItem("Overview"), TabItem("Sessions"), TabItem("Alerts", BadgeUiModel.Count(2))),
                selectedIndex = 0
            ),
            onTabSelected = {}
        )
        ElectronTabs(
            TabsUiModel.Scrollable(
                tabs = listOf("All", "Home", "Work", "Public stations", "Favorites").map { TabItem(it) },
                selectedIndex = 1
            ),
            onTabSelected = {}
        )
        ElectronNavigationBar(NavigationBarUiModel.Bottom(destinations, selectedIndex = 1), onItemSelected = {})
        Row(modifier = Modifier.height(ElectronDimens.readableWidth)) {
            ElectronNavigationBar(NavigationBarUiModel.Rail(destinations, selectedIndex = 0), onItemSelected = {})
        }
        ElectronSheetHeader(SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"), onCloseClick = {})
    }

    @Test
    fun overlays() = paparazzi.electronGallery(theme) {
        Caption("Dialog")
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
            modifier = Modifier
        )
        Caption("Bottom sheet")
        Column(modifier = Modifier.background(ElectronTheme.colors.background.surfaceRaised, ElectronShapes.sheet)) {
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
        Caption("Menu")
        MenuDefaultPanel(
            uiModel = MenuUiModel.Default(
                items = listOf(
                    MenuItem("Rename", leadingIcon = Icons.Outlined.Edit),
                    MenuItem("Duplicate", leadingIcon = Icons.Outlined.ContentCopy, trailingText = "Ctrl+D"),
                    MenuItem("Standard tariff", isSelected = true),
                    MenuItem("Unavailable", isEnabled = false),
                    MenuItem("Delete", leadingIcon = Icons.Outlined.DeleteOutline, isDestructive = true)
                ),
                isExpanded = true
            ),
            onItemClick = {}
        )
        Caption("Tooltips")
        Box { TooltipPlainSurface(TooltipUiModel.Plain("Charger settings")) }
        TooltipRichSurface(
            TooltipUiModel.Rich(
                title = "Smart charging",
                text = "Charges when electricity is cheapest, and always finishes before your departure time.",
                actionLabel = "Learn more"
            ),
            onActionClick = {}
        )
    }

    @Test
    fun loadingAndEmptyStates() = paparazzi.electronGallery(theme) {
        Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            ElectronSkeleton(SkeletonUiModel.MetricCard(), modifier = Modifier.weight(1f))
            ElectronSkeleton(SkeletonUiModel.MetricCard(), modifier = Modifier.weight(1f))
        }
        ElectronSkeleton(SkeletonUiModel.ListItem())
        ElectronSkeleton(SkeletonUiModel.ListItem(hasLeading = false, hasSubtitle = false))
        ElectronSkeleton(SkeletonUiModel.Text(lines = 3))
        ElectronSkeleton(SkeletonUiModel.Block(aspectRatio = 3f))
        ElectronEmptyState(
            EmptyStateUiModel.Default(
                icon = Icons.Outlined.EvStation,
                title = "No charging sessions yet",
                message = "Plug in your vehicle to start tracking consumption.",
                primaryActionLabel = "Find a station",
                secondaryActionLabel = "Learn more"
            )
        )
        ElectronEmptyState(
            EmptyStateUiModel.Error(
                icon = Icons.Outlined.CloudOff,
                title = "Couldn't load stations",
                message = "Check your connection and try again.",
                retryLabel = "Retry"
            )
        )
    }
}
