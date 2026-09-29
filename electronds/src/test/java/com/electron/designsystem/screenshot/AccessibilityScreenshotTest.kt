package com.electron.designsystem.screenshot

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.accessibility.AccessibilityRenderExtension
import com.android.ide.common.rendering.api.SessionParams
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.checkbox.ElectronCheckbox
import com.electron.designsystem.components.checkbox.models.CheckboxUiModel
import com.electron.designsystem.components.dropdown.ElectronDropdown
import com.electron.designsystem.components.dropdown.models.DropdownUiModel
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.inputfield.ElectronInputField
import com.electron.designsystem.components.inputfield.models.InputFieldUiModel
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.components.segmentedcontrol.ElectronSegmentedControl
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.skeleton.ElectronSkeleton
import com.electron.designsystem.components.skeleton.models.SkeletonUiModel
import com.electron.designsystem.components.tabs.ElectronTabs
import com.electron.designsystem.components.tabs.models.TabItem
import com.electron.designsystem.components.tabs.models.TabsUiModel
import org.junit.Rule
import org.junit.Test

/**
 * Accessibility snapshots: Paparazzi draws each accessibility node with
 * what TalkBack would announce. They lock in the semantic decisions of the
 * system: a field is one node (label, value and error together), a toggle
 * row is one switch node with its state, a switch inside a row is not a
 * second node, an icon-only button has a label, a skeleton group is one
 * "loading" announcement.
 */
internal class AccessibilityScreenshotTest {

    // NORMAL, not SHRINK: the accessibility extension draws its legend beside
    // the content in space taken from the image width, which SHRINK sizes to
    // the content alone (the legend would get a negative width).
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceVariant.Phone.config,
        renderingMode = SessionParams.RenderingMode.NORMAL,
        showSystemUi = false,
        maxPercentDifference = MAX_PERCENT_DIFFERENCE,
        renderExtensions = setOf(AccessibilityRenderExtension())
    )

    @Test
    fun formSemantics() = paparazzi.electronGallery(ThemeVariant.Light) {
        ElectronInputField(
            InputFieldUiModel.Default(value = "12", label = "IBAN", isError = true, errorText = "IBAN looks too short"),
            onValueChange = {}
        )
        ElectronDropdown(
            DropdownUiModel.Default(label = "Tariff", options = listOf("Standard", "Off-peak"), selectedIndex = 1),
            onFieldClick = {}, onOptionSelected = {}, onDismissRequest = {}
        )
        ElectronCheckbox(CheckboxUiModel.Default(isChecked = true, label = "Monthly energy report"), onCheckedChange = {})
        ElectronButton(ButtonUiModel.Tertiary(icon = IconUiModel.Default(Icons.Outlined.Settings, "Settings")), onClick = {})
    }

    @Test
    fun selectionSemantics() = paparazzi.electronGallery(ThemeVariant.Light) {
        ElectronListItem(
            ListItemUiModel.Toggle(
                title = "Smart charging",
                isChecked = true,
                leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.Schedule, tone = IconTone.Muted))
            )
        )
        ElectronSegmentedControl(
            SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = 1),
            onOptionSelected = {}
        )
        ElectronTabs(
            TabsUiModel.Fixed(tabs = listOf(TabItem("Overview"), TabItem("Alerts", BadgeUiModel.Count(2))), selectedIndex = 0),
            onTabSelected = {}
        )
        ElectronSkeleton(SkeletonUiModel.ListItem(contentDescription = "Loading chargers"))
    }
}
