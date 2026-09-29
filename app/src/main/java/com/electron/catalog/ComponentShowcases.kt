package com.electron.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.badge.ElectronBadge
import com.electron.designsystem.components.badge.models.BadgeTone
import com.electron.designsystem.components.badge.models.BadgeUiModel
import com.electron.designsystem.components.checkbox.ElectronCheckbox
import com.electron.designsystem.components.checkbox.models.CheckboxUiModel
import com.electron.designsystem.components.divider.ElectronDivider
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.emptystate.ElectronEmptyState
import com.electron.designsystem.components.emptystate.models.EmptyStateUiModel
import com.electron.designsystem.components.progress.ElectronProgressIndicator
import com.electron.designsystem.components.progress.models.ProgressSize
import com.electron.designsystem.components.progress.models.ProgressTone
import com.electron.designsystem.components.progress.models.ProgressUiModel
import com.electron.designsystem.components.radiobutton.ElectronRadioButton
import com.electron.designsystem.components.radiobutton.models.RadioButtonUiModel
import com.electron.designsystem.components.segmentedcontrol.ElectronSegmentedControl
import com.electron.designsystem.components.segmentedcontrol.models.SegmentedControlUiModel
import com.electron.designsystem.components.topbar.ElectronTopBar
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarNavigation
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronSpacing

/** Selection controls: the catalog owns every selected value. */
@Composable
internal fun SelectionShowcase() {
    var acceptTerms by rememberSaveable { mutableStateOf(false) }
    var newsletter by rememberSaveable { mutableStateOf(true) }
    var plan by rememberSaveable { mutableIntStateOf(0) }
    var range by rememberSaveable { mutableIntStateOf(1) }
    val plans = listOf("Off-peak", "Standard", "Boost")

    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
        ElectronSegmentedControl(
            uiModel = SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = range),
            onOptionSelected = { range = it }
        )
        ElectronCheckbox(
            uiModel = CheckboxUiModel.Default(isChecked = acceptTerms, label = "I accept the terms", isError = !acceptTerms),
            onCheckedChange = { acceptTerms = it }
        )
        ElectronCheckbox(
            uiModel = CheckboxUiModel.Default(isChecked = newsletter, label = "Monthly energy report"),
            onCheckedChange = { newsletter = it }
        )
        ElectronDivider(DividerUiModel.Horizontal())
        plans.forEachIndexed { index, label ->
            ElectronRadioButton(
                uiModel = RadioButtonUiModel.Default(isSelected = plan == index, label = label),
                onClick = { plan = index }
            )
        }
    }
}

@Composable
internal fun FeedbackShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElectronBadge(BadgeUiModel.Dot())
            ElectronBadge(BadgeUiModel.Count(count = 4))
            ElectronBadge(BadgeUiModel.Count(count = 150, tone = BadgeTone.Brand))
            Row(modifier = Modifier.height(ElectronDimens.iconLg)) {
                ElectronDivider(DividerUiModel.Vertical())
            }
            ElectronProgressIndicator(ProgressUiModel.Circular(size = ProgressSize.Sm))
            ElectronProgressIndicator(ProgressUiModel.Circular(progress = 0.7f, size = ProgressSize.Sm, tone = ProgressTone.Success))
        }
        ElectronProgressIndicator(ProgressUiModel.Linear(progress = 0.45f))
        ElectronProgressIndicator(ProgressUiModel.Linear())
    }
}

@Composable
internal fun TopBarShowcase() {
    Column {
        ElectronTopBar(
            uiModel = TopBarUiModel.Default(
                title = "Home charger",
                subtitle = "Connected",
                navigation = TopBarNavigation.Back,
                action = TopBarAction(Icons.Outlined.Settings, "Settings")
            ),
            onNavigationClick = {},
            onActionClick = {}
        )
        ElectronTopBar(
            uiModel = TopBarUiModel.Large(
                title = "Dashboard",
                action = TopBarAction(Icons.Outlined.Notifications, "Notifications", badge = BadgeUiModel.Dot())
            ),
            onActionClick = {}
        )
    }
}

@Composable
internal fun EmptyStateShowcase() {
    ElectronEmptyState(
        uiModel = EmptyStateUiModel.Default(
            icon = Icons.Outlined.EvStation,
            title = "No charging sessions yet",
            message = "Plug in your vehicle to start tracking consumption.",
            primaryActionLabel = "Find a station",
            secondaryActionLabel = "Learn more"
        ),
        onPrimaryActionClick = {},
        onSecondaryActionClick = {}
    )
    ElectronEmptyState(
        uiModel = EmptyStateUiModel.Error(
            icon = Icons.Outlined.Bolt,
            title = "Couldn't reach the charger",
            message = "Check that it is powered on and connected.",
            retryLabel = "Retry"
        ),
        onRetryClick = {}
    )
}
