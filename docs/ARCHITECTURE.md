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
- Exposes callbacks as parameters. When a component emits more than one kind
  of interaction, it exposes a single typed signal callback
  (`onSignal: (ChipSignal) -> Unit`) instead of a growing list of lambdas.

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
