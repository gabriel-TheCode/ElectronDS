# ElectronDS Usage Guidelines

## The golden path

1. Your ViewModel exposes a screen UI state.
2. A UI mapper (pure functions) derives component UI models from that state.
3. The screen passes UI models down and receives interactions back through
   one dedicated callback per action (`onClick`, `onValueChange`, ...).

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

The catalog app's `PayeeFormSection` is a runnable version of this loop.

## Component reference

### ElectronButton
One API for five variants. The UI model selects the variant.
```kotlin
ElectronButton(ButtonUiModel.Primary(text = "Confirm", isFullWidth = true), onClick = ...)
ElectronButton(ButtonUiModel.Secondary(text = "Cancel"), onClick = ...)
ElectronButton(ButtonUiModel.Link(text = "Learn more"), onClick = ...)
ElectronButton(ButtonUiModel.Loading(text = "Saving"), onClick = {})
```
Use `state = ButtonState.Success/Error` for post-action feedback. Swap
`Primary` for `Loading` in your mapper while a request is in flight.

### ElectronInputField
Value lives in your state; edits arrive via `onValueChange`. Error text
wins over helper text when `isError` is true.

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
`CardUiModel.Default(title?, actionLabel?)` frames a content slot;
`CardUiModel.Status(message, severity)` renders a tinted message card.
`onActionClick` fires when the footer action of a Default card is tapped.

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

### ElectronScaffold
Slot-only page frame. Any conditional content (offline, error, empty) is
decided by the screen inside the content slot.

## Rules for feature teams

- Never import from `variants` or `primitives` packages (the compiler will
  stop you: they are internal).
- Never put lambdas or raw `Dp`/`Color`/`FontWeight` into anything you feed
  a component; if you feel the need, request a new variant or token instead.
- Wire each callback you need individually; never wrap several interactions
  into a single handler with a `when` over an event type.
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
   variants don't use it). Never introduce an `onSignal` callback.
5. Add previews and an entry in the catalog.

Adding a new component: copy the folder shape of `components/tag` (the
smallest complete example) and keep the visibility rules.
