# ElectronDS Architecture

ElectronDS implements the three-layer UI architecture (Primitive, Variant,
Component) with strict, tool-enforced boundaries.

## Layer contract

### Layer 1: Primitive (`components/*/primitives`)
- `internal` visibility, always.
- Stateless. Receives raw, fully resolved parameters: `Color`, `Dp`,
  `TextStyle`, `String`, lambdas.
- No UI models, no semantic enums, no theme decisions beyond reading the
  current theme for Material bridging where unavoidable.
- May expose interaction lambdas but never interprets them.

### Layer 2: Variant (`components/*/variants`)
- `internal` visibility, always. The source system leaked six variants and
  five primitives as public API; ElectronDS closes that hole with
  `explicitApi()` plus `internal` modifiers.
- Receives a component-specific UI model subclass.
- Resolves semantic tokens (TagTone, ButtonSize, AvatarSize) into raw
  values. All color-mapping logic lives beside the variants in `*Styling.kt`
  files, never in the model layer.
- Minor conditional rendering only; no state ownership, no navigation.
- Every component has a default variant.

### Layer 3: Component (`components/*/ElectronX.kt`)
- The single public entry point, named `Electron` + component name.
- Dispatches exhaustively on the sealed UI model.
- Exposes signals upward, one callback parameter per interaction (see below).

## Signals
A signal is a user interaction a component emits upward (a tap, a clear, a
value change). Each signal is exposed as its own dedicated callback
parameter.

| Component | Callbacks |
|---|---|
| `ElectronButton` | `onClick` |
| `ElectronFab` | `onClick` |
| `ElectronChip` | `onClick`, `onClear` (Filter chips only) |
| `ElectronCard` | `onActionClick` (Default card only) |
| `ElectronSheetHeader` | `onCloseClick`, `onResetClick` |
| `ElectronSwitch` | `onCheckedChange` |
| `ElectronInputField` | `onValueChange` |

Rules:
- **Naming**: `on` + action, following Compose/Material conventions:
  `onClick` for the component's main tap, `on<Element>Click` for a
  secondary element (`onCloseClick`, `onActionClick`), `on<Value>Change`
  for value updates, or a domain verb for a specific action (`onClear`).
- **Signature**: `() -> Unit` for plain actions; carry the new value only
  for value changes (`(Boolean) -> Unit`, `(String) -> Unit`).
- **Required vs optional**: the main interaction is a required parameter
  placed right after `uiModel`. Callbacks tied to an optional element (a
  reset action that only exists when `resetLabel` is set, a clear icon only
  shown on selected Filter chips) default to `{}` and are placed after
  `modifier`.
- **Pass-through**: variants and primitives forward the callbacks
  unchanged. The design system never interprets a signal; the screen
  decides what each one means.
- **Never in UI models**: callbacks are component parameters, never fields
  of a UI model.

## UI models (`components/*/models`)
- Sealed classes, one subclass per variant, `@Immutable` data classes.
- Contain only visual data. Never lambdas, never raw styling values
  (`Dp`, `FontWeight`, `Color`): those are replaced by semantic enums the
  design system resolves per theme.
- Nullable fields express optional elements; empty strings are never used
  as "absent" markers.

## Ownership and boundary
- The design system is visually authoritative, behaviorally neutral and
  feature independent. `ElectronScaffold` demonstrates the fix: the legacy
  version branched on `isNetworkConnected`, a feature decision; the Electron
  version exposes slots only.
- Screens own data, meaning and event interpretation. The catalog's
  `PayeeFormSection` shows the full loop: screen UI state, UI mapper
  producing component UI models, signals flowing back up.

## Enforcement mechanisms
1. `explicitApi()` on the library module: every public declaration is
   deliberate.
2. `internal` on all variants, primitives and styling resolvers.
3. Folder structure mirrors the layers 1:1, so review tooling can flag any
   import of a `primitives` or `variants` package from feature code.
4. Tokens are the only source of dimensions, colors, shapes, type and
   motion. Raw values inside `components/` are treated as review failures.
