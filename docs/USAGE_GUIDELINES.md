# ElectronDS Usage Guidelines

## The golden path

1. Your ViewModel exposes a screen UI state.
2. A UI mapper (pure functions) derives component UI models from that state.
3. The screen passes UI models down and signals up, through one dedicated
   callback per interaction (`onClick`, `onValueChange`, ...).

```kotlin
// 1. Screen state (feature module)
data class PayeeFormUiState(
    val name: String = "",
    val iban: String = "",
    val ibanError: String? = null,
    val isSubmitting: Boolean = false
)

// 2. Mapper (feature module)
fun PayeeFormUiState.toIbanFieldUiModel() = InputFieldUiModel.Default(
    value = iban,
    label = "IBAN",
    isError = ibanError != null,
    errorText = ibanError
)

// 3. Screen (feature module)
ElectronInputField(
    uiModel = uiState.toIbanFieldUiModel(),
    onValueChange = viewModel::onIbanChanged
)
```

The catalog app's `PayeeFormSection` is a runnable version of this loop;
`ChargerDashboardSection` applies it to the composite components (metric
cards, list items, segmented control, dialog).

## Component reference

### ElectronButton
One API for five variants. The UI model selects the variant.
```kotlin
ElectronButton(ButtonUiModel.Primary(text = "Confirm", isFullWidth = true), onClick = ...)
ElectronButton(ButtonUiModel.Secondary(text = "Cancel"), onClick = ...)
ElectronButton(ButtonUiModel.Link(text = "Learn more"), onClick = ...)
ElectronButton(ButtonUiModel.Loading(text = "Saving"), onClick = {})
```
Use `state = ButtonState.Success/Error` for post-action feedback; the colors
cross-fade to the new state. Swap `Primary` for `Loading` in your mapper
while a request is in flight. Emphasis ladder: `Primary` (filled, one per
screen), `Secondary` (tonal, no border), `Tertiary` (text), `Link` (inline,
underlined, no padding so it aligns with surrounding text). Button icons
always take the button's content color.

### ElectronInputField
Value lives in your state; edits arrive via `onValueChange`. The label sits
above a 48dp field (same height as a Large button). Error text wins over
helper text when `isError` is true, and appears with an icon and an
animated height change instead of a layout jump.

### ElectronChip
Filter chips show `defaultText` when idle, `valueText` when selected, and
call `onClick` when tapped and `onClear` when the clear icon is tapped.
Selection is your data. Assist chips only use `onClick`.
```kotlin
ElectronChip(
    uiModel = uiState.toPeriodChipUiModel(),
    onClick = viewModel::onPeriodChipClicked,
    onClear = viewModel::onPeriodCleared
)
```

### ElectronCard
`CardUiModel.Default(title?, actionLabel?)` frames a content slot: title
and action share the header line (action trailing). The content slot owns
its own padding. `CardUiModel.Status(message, severity)` renders a tinted
message card. `onActionClick` fires when the header action is tapped.

### ElectronTag / ElectronAvatar / ElectronIcon
Purely descriptive: pick a size, tone and style; the theme resolves colors.

### ElectronSwitch / ElectronFab
Standard controls with hoisted state: `ElectronSwitch` reports
`onCheckedChange(Boolean)`, `ElectronFab` reports `onClick`.

### ElectronSheetHeader
`onCloseClick` fires when the close icon is tapped. `onResetClick` fires
when the reset action is tapped; it is optional and only relevant when the
UI model sets `resetLabel`.
```kotlin
ElectronSheetHeader(
    uiModel = SheetHeaderUiModel.Default(title = "Filters", resetLabel = "Reset"),
    onCloseClick = viewModel::onFiltersDismissed,
    onResetClick = viewModel::onFiltersReset
)
```

### ElectronCheckbox / ElectronRadioButton
The whole row (control + label) is the touch target. `ElectronCheckbox`
reports `onCheckedChange(Boolean)`; `ElectronRadioButton` reports `onClick`,
and your state decides which option of the group is selected.

### ElectronSegmentedControl
2 to 4 mutually exclusive options. `onOptionSelected` carries the tapped
index; `selectedIndex` is your data.
```kotlin
ElectronSegmentedControl(
    uiModel = SegmentedControlUiModel.Default(options = listOf("Day", "Week", "Month"), selectedIndex = uiState.rangeIndex),
    onOptionSelected = viewModel::onRangeSelected
)
```

### ElectronBadge / ElectronDivider / ElectronProgressIndicator
Purely descriptive. A null `progress` renders an indeterminate indicator.

### ElectronListItem
Pick the variant by purpose: `Navigation` (chevron, `onClick`), `Toggle`
(switch, `onCheckedChange`), `Detail` (read-only value or tag). Leading
visuals reuse `AvatarUiModel` / `IconUiModel` through `ListItemLeading`.
```kotlin
ElectronListItem(
    uiModel = ListItemUiModel.Toggle(title = "Smart charging", isChecked = uiState.smartCharging),
    onCheckedChange = viewModel::onSmartChargingToggled
)
```

### ElectronMetricCard
Key figure for dashboards. Format the value in your mapper; the card
renders it in the data typeface. `MetricDelta` separates the trend
direction from its sentiment, since "up" is good for revenue but bad for
consumption.

### ElectronTopBar
Fits the `topBar` slot of `ElectronScaffold`. `navigation` is an enum
(`None`, `Back`, `Close`) so every screen uses the same glyphs;
`onNavigationClick` and `onActionClick` are the signals.

### ElectronEmptyState
`Default` for empty content (optional primary button and secondary link),
`Error` for load failures (optional retry button). Show it inside your
content slot when the list is empty or loading failed.

### ElectronDialog
Compose it behind a state flag to show it. `onConfirmClick` and
`onDismissRequest` are required; `onDismissClick` defaults to
`onDismissRequest`. Use `Destructive` for irreversible actions.
```kotlin
if (uiState.showDisconnectDialog) {
    ElectronDialog(
        uiModel = DialogUiModel.Destructive(
            title = "Disconnect charger?",
            message = "Scheduled sessions will be cancelled.",
            confirmLabel = "Disconnect",
            dismissLabel = "Cancel"
        ),
        onConfirmClick = viewModel::onDisconnectConfirmed,
        onDismissRequest = viewModel::onDisconnectDialogDismissed
    )
}
```

### ElectronBottomSheet
Compose it behind a state flag, like a dialog. With a `title`, the sheet
shows an ElectronSheetHeader whose close button slides the sheet out
before `onDismissRequest` fires. Width is capped at 640dp on tablets/TV.

### ElectronTabs / ElectronSegmentedControl
Tabs switch between sibling views of a screen (Overview / History);
segmented controls filter or pick a value inside one view (Day / Week).
`Fixed` tabs share the width (2 to 4 tabs), `Scrollable` tabs size to
their labels and keep the selected tab in view.

### ElectronNavigationBar
3 to 5 top-level destinations. Use `Bottom` on compact widths and `Rail`
on medium and expanded widths (tablets, TV): a bottom bar on a TV forces
the D-pad on a long horizontal trip. Provide `selectedIcon` (filled glyph)
so the active destination differs by shape, not only color.

### ElectronMenu / ElectronDropdown
`ElectronMenu` goes in the same `Box` as its anchor and opens below it
(above when there is no room). It does not close itself: set
`isExpanded = false` in `onItemClick`. `ElectronDropdown` is the form
version: same look as ElectronInputField, the menu as wide as the field,
the chosen option checked.

### ElectronTooltip
`Plain` names an icon-only control; `Rich` explains, with an optional
action. Tooltips appear on long-press, hover or focus, so never put the
only way to do something in one.

### ElectronSlider
For relative values (charge limit, power). Put formatted values in
`valueText` (the header shows it in the data typeface). Commit expensive
work in `onValueChangeFinished`, not `onValueChange`.

### ElectronSkeleton
Use skeletons when loading replaces content whose shape is known; use
ElectronProgressIndicator for actions. `ListItem` and `MetricCard`
placeholders match the real components' geometry exactly, so nothing
shifts when data arrives. Give one placeholder per group a
`contentDescription` ("Loading sessions").

### ElectronScaffold
Slot-only page frame. Any conditional content (offline, error, empty) is
decided by the screen inside the content slot.

## Rules for feature teams

- Never import from `variants` or `primitives` packages (the compiler will
  stop you: they are internal).
- Never put lambdas or raw `Dp`/`Color`/`FontWeight` into anything you feed
  a component; if you feel the need, request a new variant or token instead.
- Build UI models in mappers, not inline in composables, so they are unit
  testable and preview-friendly.
- One-off compositions (an error card with an illustration, a product tab
  bar) are feature components built from Electron parts, living in your
  feature module under the same 3-layer discipline if they grow.

## Extending the system

Adding a variant to an existing component:
1. Add a subclass to the sealed UI model.
2. Create the internal variant that resolves it and calls the primitive.
3. Add the branch in the component dispatch (the compiler enforces
   exhaustiveness).
4. If the variant introduces a new interaction, add a dedicated callback
   parameter to the component (optional, defaulting to `{}`, if other
   variants don't use it).
5. Add previews and an entry in the catalog.

Adding a new component: copy the folder shape of `components/tag` (the
smallest complete example) and keep the visibility rules. For a composite
component, look at `components/listitem`: its variants reuse public Electron
components and its UI model embeds their public UI models.
