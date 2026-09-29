package com.electron.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.bottomsheet.ElectronBottomSheet
import com.electron.designsystem.components.bottomsheet.models.BottomSheetUiModel
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.dropdown.ElectronDropdown
import com.electron.designsystem.components.dropdown.models.DropdownUiModel
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.menu.ElectronMenu
import com.electron.designsystem.components.menu.models.MenuItem
import com.electron.designsystem.components.menu.models.MenuUiModel
import com.electron.designsystem.components.navigationbar.ElectronNavigationBar
import com.electron.designsystem.components.navigationbar.models.NavigationBarUiModel
import com.electron.designsystem.components.navigationbar.models.NavigationItem
import com.electron.designsystem.components.skeleton.ElectronSkeleton
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.slider.ElectronSlider
import com.electron.designsystem.components.slider.models.SliderUiModel
import com.electron.designsystem.components.tabs.ElectronTabs
import com.electron.designsystem.components.tabs.models.TabItem
import com.electron.designsystem.components.tabs.models.TabsUiModel
import com.electron.designsystem.components.toggle.ElectronSwitch
import com.electron.designsystem.components.toggle.models.SwitchUiModel
import com.electron.designsystem.components.tooltip.ElectronTooltip
import com.electron.designsystem.components.tooltip.models.TooltipUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronSpacing

@Composable
internal fun TabsShowcase() {
    var fixed by rememberSaveable { mutableIntStateOf(0) }
    var scrollable by rememberSaveable { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
        ElectronTabs(
            uiModel = TabsUiModel.Fixed(
                tabs = listOf(TabItem("Overview"), TabItem("Sessions"), TabItem("Alerts", BadgeUiModel.Count(2))),
                selectedIndex = fixed
            ),
            onTabSelected = { fixed = it }
        )
        ElectronTabs(
            uiModel = TabsUiModel.Scrollable(
                tabs = listOf("All", "Home", "Work", "Public stations", "Favorites", "Recent").map { TabItem(it) },
                selectedIndex = scrollable
            ),
            onTabSelected = { scrollable = it }
        )
    }
}

@Composable
internal fun NavigationShowcase() {
    var selected by rememberSaveable { mutableIntStateOf(0) }
    val items = listOf(
        NavigationItem("Home", Icons.Outlined.Home, Icons.Filled.Home),
        NavigationItem("Charging", Icons.Outlined.Bolt, Icons.Filled.Bolt, badge = BadgeUiModel.Dot()),
        NavigationItem("Account", Icons.Outlined.Person, Icons.Filled.Person)
    )
    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
        ElectronNavigationBar(
            uiModel = NavigationBarUiModel.Bottom(items, selected),
            onItemSelected = { selected = it }
        )
        // Tablet / TV layout: the same destinations as a rail.
        Row(modifier = Modifier.height(ElectronDimens.readableWidth)) {
            ElectronNavigationBar(
                uiModel = NavigationBarUiModel.Rail(items, selected),
                onItemSelected = { selected = it }
            )
        }
    }
}

/** Overlays: every one is driven by state owned here, like a screen would. */
@Composable
internal fun OverlaysShowcase() {
    var isMenuOpen by rememberSaveable { mutableStateOf(false) }
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isDropdownOpen by rememberSaveable { mutableStateOf(false) }
    var tariff by rememberSaveable { mutableStateOf<Int?>(null) }
    var offPeakOnly by rememberSaveable { mutableStateOf(true) }
    val tariffs = listOf("Standard", "Off-peak", "Dynamic")

    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElectronButton(
                uiModel = ButtonUiModel.Secondary(text = "Open sheet"),
                onClick = { isSheetOpen = true }
            )
            Box {
                ElectronButton(
                    uiModel = ButtonUiModel.Tertiary(icon = IconUiModel.Default(Icons.Outlined.MoreVert, "More actions")),
                    onClick = { isMenuOpen = true }
                )
                ElectronMenu(
                    uiModel = MenuUiModel.Default(
                        items = listOf(
                            MenuItem("Rename", leadingIcon = Icons.Outlined.Edit),
                            MenuItem("Duplicate", leadingIcon = Icons.Outlined.ContentCopy),
                            MenuItem("Delete", leadingIcon = Icons.Outlined.DeleteOutline, isDestructive = true)
                        ),
                        isExpanded = isMenuOpen
                    ),
                    onItemClick = { isMenuOpen = false },
                    onDismissRequest = { isMenuOpen = false }
                )
            }
            ElectronTooltip(
                uiModel = TooltipUiModel.Rich(
                    title = "Smart charging",
                    text = "Charges when electricity is cheapest, and always finishes before your departure time.",
                    actionLabel = "Learn more"
                )
            ) {
                ElectronButton(
                    uiModel = ButtonUiModel.Tertiary(icon = IconUiModel.Default(Icons.Outlined.Info, "About smart charging")),
                    onClick = {}
                )
            }
        }
        ElectronDropdown(
            uiModel = DropdownUiModel.Default(
                label = "Tariff",
                options = tariffs,
                selectedIndex = tariff,
                placeholder = "Choose a tariff",
                helperText = "Used to estimate your charging costs",
                isExpanded = isDropdownOpen
            ),
            onFieldClick = { isDropdownOpen = !isDropdownOpen },
            onOptionSelected = {
                tariff = it
                isDropdownOpen = false
            },
            onDismissRequest = { isDropdownOpen = false }
        )
    }

    if (isSheetOpen) {
        ElectronBottomSheet(
            uiModel = BottomSheetUiModel.Default(title = "Charging schedule", resetLabel = "Reset"),
            onDismissRequest = { isSheetOpen = false },
            onResetClick = { offPeakOnly = true }
        ) {
            ElectronListItem(
                uiModel = ListItemUiModel.Toggle(title = "Off-peak only", subtitle = "22:00 to 06:00", isChecked = offPeakOnly),
                onCheckedChange = { offPeakOnly = it }
            )
        }
    }
}

@Composable
internal fun SliderShowcase() {
    var power by rememberSaveable { mutableFloatStateOf(7.4f) }
    var limit by rememberSaveable { mutableFloatStateOf(0.8f) }
    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.lg)) {
        ElectronSlider(
            uiModel = SliderUiModel.Continuous(
                value = power,
                valueRangeStart = 1.4f,
                valueRangeEnd = 11f,
                label = "Max power",
                valueText = "%.1f kW".format(power)
            ),
            onValueChange = { power = it }
        )
        ElectronSlider(
            uiModel = SliderUiModel.Stepped(
                value = limit,
                steps = 3,
                valueRangeStart = 0.6f,
                valueRangeEnd = 1f,
                label = "Charge limit",
                valueText = "${(limit * 100).toInt()}%"
            ),
            onValueChange = { limit = it }
        )
    }
}

/** Skeletons next to the content they stand for: toggling shows that nothing moves. */
@Composable
internal fun SkeletonShowcase() {
    var isLoading by rememberSaveable { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Loading",
                style = ElectronTheme.typography.bodyLarge,
                color = ElectronTheme.colors.content.primary,
                modifier = Modifier.weight(1f)
            )
            ElectronSwitch(SwitchUiModel.Default(isChecked = isLoading), onCheckedChange = { isLoading = it })
        }
        if (isLoading) {
            ElectronSkeleton(SkeletonUiModel.ListItem(contentDescription = "Loading stations"))
            ElectronSkeleton(SkeletonUiModel.ListItem())
            ElectronSkeleton(SkeletonUiModel.Circle(AvatarSize.Lg))
            ElectronSkeleton(SkeletonUiModel.Text(lines = 2))
        } else {
            ElectronListItem(ListItemUiModel.Detail(title = "Home charger", subtitle = "Wallbox 22 kW", valueText = "7.4 kW"))
            ElectronListItem(ListItemUiModel.Detail(title = "Office", subtitle = "Public station", valueText = "11 kW"))
        }
    }
}
