package com.electron.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.electron.catalog.demo.PayeeFormSection
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardSeverity
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.chip.ElectronChip
import com.electron.designsystem.components.chip.models.ChipUiModel
import com.electron.designsystem.components.fab.ElectronFab
import com.electron.designsystem.components.fab.models.FabUiModel
import com.electron.designsystem.components.sheetheader.ElectronSheetHeader
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.components.tag.ElectronTag
import com.electron.designsystem.components.tag.models.TagStyle
import com.electron.designsystem.components.tag.models.TagTone
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.components.topbar.ElectronTopBar
import com.electron.designsystem.components.topbar.models.TopBarAction
import com.electron.designsystem.components.topbar.models.TopBarNavigation
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.layout.ElectronScaffold
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Component catalog: every component, variant and state, live. Feature-level
 * code: it owns meaning and interaction, the design system owns rendering.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatalogScreen(
    isDarkTheme: Boolean,
    onThemeToggled: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    ElectronScaffold(
        topBar = {
            ElectronTopBar(
                uiModel = TopBarUiModel.Large(
                    title = "Components",
                    navigation = TopBarNavigation.Back,
                    navigationContentDescription = "Back to home",
                    action = TopBarAction(
                        icon = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                        contentDescription = if (isDarkTheme) "Switch to light theme" else "Switch to dark theme"
                    )
                ),
                onNavigationClick = onBackClick,
                onActionClick = { onThemeToggled(!isDarkTheme) }
            )
        },
        floatingActionButton = {
            ElectronFab(
                uiModel = FabUiModel.Extended(text = "New", icon = Icons.Outlined.Add),
                onClick = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                // The scaffold already applied the system bars: showcased top
                // bars inside the list must not pad for them again.
                .consumeWindowInsets(padding)
                .padding(horizontal = ElectronSpacing.lg)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(ElectronSpacing.lg)
        ) {
            SectionTitle("Buttons")
            ElectronButton(ButtonUiModel.Primary(text = "Primary", isFullWidth = true), onClick = {})
            ElectronButton(ButtonUiModel.Secondary(text = "Secondary", isFullWidth = true), onClick = {})
            Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
                ElectronButton(ButtonUiModel.Tertiary(text = "Tertiary"), onClick = {})
                ElectronButton(ButtonUiModel.Link(text = "Link"), onClick = {})
            }
            ElectronButton(ButtonUiModel.Primary(text = "Saved", state = ButtonState.Success), onClick = {})
            ElectronButton(ButtonUiModel.Loading(text = "Charging", isFullWidth = true), onClick = {})

            SectionTitle("Tags")
            Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
                ElectronTag(TagUiModel.Text("Live", tone = TagTone.Success))
                ElectronTag(TagUiModel.Text("Pending", tone = TagTone.Warning, style = TagStyle.Outlined))
                ElectronTag(TagUiModel.Text("Failed", tone = TagTone.Error, style = TagStyle.Filled))
                ElectronTag(TagUiModel.Text("Beta", tone = TagTone.Brand))
            }

            SectionTitle("Avatars")
            Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
                ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Bolt, AvatarSize.Sm, AvatarTone.Brand))
                ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Bolt, AvatarSize.Md, AvatarTone.Accent))
                ElectronAvatar(AvatarUiModel.Initials("GT", AvatarSize.Md, AvatarTone.Neutral))
                ElectronAvatar(AvatarUiModel.Default(Icons.Outlined.Bolt, AvatarSize.Lg, AvatarTone.Critical))
            }

            SectionTitle("Chips")
            ChipShowcase()

            SectionTitle("Cards")
            ElectronCard(
                uiModel = CardUiModel.Default(title = "Consumption", actionLabel = "See all"),
                onActionClick = {}
            ) {
                Text(
                    text = "3 devices connected to the grid",
                    style = ElectronTheme.typography.bodyMedium,
                    color = ElectronTheme.colors.content.secondary,
                    modifier = Modifier.padding(
                        start = ElectronSpacing.lg,
                        end = ElectronSpacing.lg,
                        bottom = ElectronSpacing.lg
                    )
                )
            }
            ElectronCard(CardUiModel.Status("Your session expires in 5 minutes.", CardSeverity.Warning))
            ElectronCard(CardUiModel.Status("Transfer completed.", CardSeverity.Success))

            SectionTitle("Sheet header")
            ElectronSheetHeader(
                uiModel = SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"),
                onCloseClick = {},
                onResetClick = {}
            )

            SectionTitle("Top bars")
            TopBarShowcase()

            SectionTitle("Selection")
            SelectionShowcase()

            SectionTitle("Badges & progress")
            FeedbackShowcase()

            SectionTitle("Empty states")
            EmptyStateShowcase()

            SectionTitle("Tabs")
            TabsShowcase()

            SectionTitle("Navigation")
            NavigationShowcase()

            SectionTitle("Menus, dropdowns, sheets & tooltips")
            OverlaysShowcase()

            SectionTitle("Sliders")
            SliderShowcase()

            SectionTitle("Loading skeletons")
            SkeletonShowcase()

            SectionTitle("Feature example: payee form")
            PayeeFormSection()

            Spacer(modifier = Modifier.height(ElectronSpacing.huge))
        }
    }
}

@Composable
private fun ChipShowcase() {
    // The screen owns selection state: the design system only renders it.
    var periodSelected by rememberSaveable { mutableStateOf(false) }
    Row(horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm)) {
        ElectronChip(
            uiModel = ChipUiModel.Filter(
                defaultText = "Period",
                valueText = "30 days",
                isSelected = periodSelected,
                clearIconContentDescription = "Clear period filter"
            ),
            onClick = { periodSelected = true },
            onClear = { periodSelected = false }
        )
        ElectronChip(
            uiModel = ChipUiModel.Assist(text = "Filters", leadingIcon = Icons.Outlined.Tune),
            onClick = {}
        )
    }
}

/**
 * Sections are separated by a larger gap (xl above the title) than the
 * gap between items inside a section (lg), so the page reads as groups.
 */
@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = ElectronTheme.typography.titleLarge,
        color = ElectronTheme.colors.content.primary,
        modifier = Modifier.padding(top = ElectronSpacing.xl)
    )
}
