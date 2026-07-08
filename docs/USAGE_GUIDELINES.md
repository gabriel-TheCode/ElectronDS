# ElectronDS Usage Guidelines

## The golden path

1. Your ViewModel exposes a screen UI state.
2. A UI mapper (pure functions) derives component UI models from that state.
3. The screen passes UI models down and signals up.

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
emit `ChipSignal.Clicked` or `ChipSignal.Cleared`. Selection is your data.

### ElectronCard
`CardUiModel.Default(title?, actionLabel?)` frames a content slot;
`CardUiModel.Status(message, severity)` renders a tinted message card.

### ElectronTag / ElectronAvatar / ElectronIcon
Purely descriptive: pick a size, tone and style; the theme resolves colors.

### ElectronSwitch / ElectronFab / ElectronSheetHeader
Standard controls with hoisted state and signal callbacks.

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
4. Add previews and an entry in the catalog.

Adding a new component: copy the folder shape of `components/tag` (the
smallest complete example) and keep the visibility rules.
