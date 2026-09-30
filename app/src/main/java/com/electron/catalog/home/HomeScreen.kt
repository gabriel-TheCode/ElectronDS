package com.electron.catalog.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.EvStation
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.electron.catalog.R
import com.electron.catalog.app.AppIntent
import com.electron.designsystem.components.avatar.ElectronAvatar
import com.electron.designsystem.components.avatar.models.AvatarSize
import com.electron.designsystem.components.avatar.models.AvatarTone
import com.electron.designsystem.components.avatar.models.AvatarUiModel
import com.electron.designsystem.components.button.ElectronButton
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.card.ElectronCard
import com.electron.designsystem.components.card.models.CardUiModel
import com.electron.designsystem.components.divider.ElectronDivider
import com.electron.designsystem.components.divider.models.DividerUiModel
import com.electron.designsystem.components.icon.models.IconTone
import com.electron.designsystem.components.icon.models.IconUiModel
import com.electron.designsystem.components.listitem.ElectronListItem
import com.electron.designsystem.components.listitem.models.ListItemLeading
import com.electron.designsystem.components.listitem.models.ListItemUiModel
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.layout.ElectronScaffold
import com.electron.designsystem.tokens.ElectronSpacing

/** Width from which the two entry points sit side by side. */
private val ExpandedWidth = 600.dp

/** Content column cap on tablets: the page reads as one composition, not a stretched phone. */
private val ContentMaxWidth = 720.dp

private val LogoSize = 64.dp

/**
 * Home: who we are (logo, name, one sentence), what's inside (three
 * figures), and two ways in. Both entries are equal choices, so they carry
 * the same action style. Pure view of the app state: every action
 * is an [AppIntent].
 */
@Composable
fun HomeScreen(
    isDarkTheme: Boolean,
    onIntent: (AppIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    ElectronScaffold(modifier = modifier) { padding ->
        BoxWithConstraints(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val isExpanded = maxWidth >= ExpandedWidth
            Column(
                modifier = Modifier
                    .widthIn(max = ContentMaxWidth)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = if (isExpanded) ElectronSpacing.xxl else ElectronSpacing.lg)
                    .padding(top = ElectronSpacing.xxl, bottom = ElectronSpacing.xl)
            ) {
                Hero()
                Spacer(modifier = Modifier.height(ElectronSpacing.xl))
                Figures()
                Spacer(modifier = Modifier.height(ElectronSpacing.xxl))
                SectionTitle("Explore")
                Spacer(modifier = Modifier.height(ElectronSpacing.md))
                Entries(isExpanded = isExpanded, onIntent = onIntent)
                Spacer(modifier = Modifier.height(ElectronSpacing.xxl))
                ElectronCard(CardUiModel.Default(title = "Appearance")) {
                    ElectronListItem(
                        uiModel = ListItemUiModel.Toggle(
                            title = "Dark theme",
                            subtitle = "Every color role remaps, nothing is inverted",
                            isChecked = isDarkTheme,
                            leading = ListItemLeading.Icon(IconUiModel.Default(Icons.Outlined.DarkMode, tone = IconTone.Muted))
                        ),
                        onCheckedChange = { onIntent(AppIntent.SetDarkTheme(it)) }
                    )
                }
                Spacer(modifier = Modifier.height(ElectronSpacing.xl))
                Text(
                    text = "Every pixel of this app is an ElectronDS component.\nVersion 1.0",
                    style = ElectronTheme.typography.bodySmall,
                    color = ElectronTheme.colors.content.muted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Hero() {
    Column {
        Image(
            painter = painterResource(R.drawable.ic_electron_logo),
            contentDescription = null,
            modifier = Modifier.size(LogoSize)
        )
        Spacer(modifier = Modifier.height(ElectronSpacing.xl))
        Text(
            text = "Electron",
            style = ElectronTheme.typography.displayLarge,
            color = ElectronTheme.colors.content.primary,
            modifier = Modifier.semantics { heading() }
        )
        Text(
            text = "Design system for Jetpack Compose",
            style = ElectronTheme.typography.titleMedium,
            color = ElectronTheme.colors.brand.primary
        )
        Spacer(modifier = Modifier.height(ElectronSpacing.md))
        Text(
            text = "Precise, calm components for energy, fintech and data-heavy apps. " +
                "Three strict layers, semantic tokens, and a dark theme that remaps instead of inverting.",
            style = ElectronTheme.typography.bodyLarge,
            color = ElectronTheme.colors.content.secondary
        )
    }
}

/** The system at a glance, as an instrument readout. */
@Composable
private fun Figures() {
    ElectronCard(CardUiModel.Default()) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Figure(value = "29", label = "components", modifier = Modifier.weight(1f))
            ElectronDivider(DividerUiModel.Vertical())
            Figure(value = "3", label = "layers", modifier = Modifier.weight(1f))
            ElectronDivider(DividerUiModel.Vertical())
            Figure(value = "2", label = "themes", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun Figure(value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ElectronSpacing.xxs),
        modifier = modifier.padding(vertical = ElectronSpacing.lg)
    ) {
        Text(text = value, style = ElectronTheme.typography.dataLarge, color = ElectronTheme.colors.content.primary)
        Text(text = label, style = ElectronTheme.typography.labelMedium, color = ElectronTheme.colors.content.secondary)
    }
}

@Composable
private fun Entries(isExpanded: Boolean, onIntent: (AppIntent) -> Unit) {
    val components: @Composable (Modifier) -> Unit = { modifier ->
        EntryCard(
            avatar = AvatarUiModel.Default(Icons.Outlined.Widgets, AvatarSize.Lg, AvatarTone.Brand),
            title = "Components",
            description = "Every component, variant and state, live and interactive, in light and dark.",
            button = ButtonUiModel.Primary(text = "Browse components", isFullWidth = true),
            onClick = { onIntent(AppIntent.OpenComponents) },
            fillHeight = isExpanded,
            modifier = modifier
        )
    }
    val demo: @Composable (Modifier) -> Unit = { modifier ->
        EntryCard(
            avatar = AvatarUiModel.Default(Icons.Outlined.EvStation, AvatarSize.Lg, AvatarTone.Accent),
            title = "Volt demo",
            description = "An EV charging app built only from Electron components: dashboard, live session, account.",
            button = ButtonUiModel.Primary(text = "Open the demo", isFullWidth = true),
            onClick = { onIntent(AppIntent.OpenDemo) },
            fillHeight = isExpanded,
            modifier = modifier
        )
    }
    if (isExpanded) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.lg),
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            components(Modifier.weight(1f).fillMaxHeight())
            demo(Modifier.weight(1f).fillMaxHeight())
        }
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)) {
            components(Modifier)
            demo(Modifier)
        }
    }
}

/**
 * One way in: what it is (avatar, title, one sentence) and one action.
 * Side by side, cards share a height and their buttons line up at the bottom.
 */
@Composable
private fun EntryCard(
    avatar: AvatarUiModel,
    title: String,
    description: String,
    button: ButtonUiModel,
    onClick: () -> Unit,
    fillHeight: Boolean,
    modifier: Modifier = Modifier
) {
    ElectronCard(CardUiModel.Default(), modifier = modifier) {
        Column(
            modifier = Modifier
                .then(if (fillHeight) Modifier.fillMaxHeight() else Modifier)
                .padding(ElectronSpacing.lg)
        ) {
            ElectronAvatar(avatar)
            Spacer(modifier = Modifier.height(ElectronSpacing.lg))
            Text(
                text = title,
                style = ElectronTheme.typography.titleLarge,
                color = ElectronTheme.colors.content.primary,
                modifier = Modifier.semantics { heading() }
            )
            Spacer(modifier = Modifier.height(ElectronSpacing.xs))
            Text(
                text = description,
                style = ElectronTheme.typography.bodyMedium,
                color = ElectronTheme.colors.content.secondary
            )
            Spacer(modifier = Modifier.height(ElectronSpacing.lg))
            if (fillHeight) Spacer(modifier = Modifier.weight(1f))
            ElectronButton(uiModel = button, onClick = onClick)
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = ElectronTheme.typography.titleMedium,
        color = ElectronTheme.colors.content.primary,
        modifier = Modifier.semantics { heading() }
    )
}
