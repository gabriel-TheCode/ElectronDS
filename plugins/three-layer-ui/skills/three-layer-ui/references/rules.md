# Rules

Each rule has an ID used in reports, a default severity, and how it is checked: **auto** (`scripts/audit.py` finds it), **manual** (read the code), or both. Severity can go up when the gap is widespread or blocks another fix; say so in the report.

Kotlin names below use the `Electron` prefix; read it as the project's own prefix (`Lrc`, `Acme`…). SwiftUI equivalents are at the end.

## DS: design system

### DS-01 Structure (blocker if there is no `components/`, else major) · auto + manual
One folder per component, the layers as subfolders:

```
designsystem/
├── components/<name>/
│   ├── ElectronName.kt        entry: the only public composable
│   ├── models/                public sealed UI models and their enums
│   ├── variants/              internal, one per model subclass, plus *Styling.kt
│   └── primitives/            internal, raw resolved values only
├── foundation/                theme, colors, tones, CompositionLocals
├── tokens/                    dimensions, component colors, typography, shapes, motion
├── layout/                    scaffolds and screen frames (slots only)
└── utils/                     preview surfaces, modifiers, overlay window
```
Flat files holding several public composables at the root are a finding.

### DS-02 Visibility (blocker) · auto
`explicitApi()` on the design-system module. Variants, primitives, `*Styling.kt` resolvers and preview helpers are `internal`. Public: entries, UI models and their enums, the theme and tokens, state holders (`ElectronXState` + `rememberElectronXState()`), layout slots.

### DS-03 Single entry (major) · manual
One public composable per component, named prefix + component (`ElectronButton`), taking `uiModel` first and dispatching exhaustively with `when (uiModel)`. No public overloads per variant, no `ElectronPrimaryButton`.

### DS-04 Variants (major) · auto + manual
Every component has a default variant. A variant receives its model subclass, resolves semantic values (tones, sizes) into raw ones in `*Styling.kt`, and calls primitives. Minor conditional rendering only: no state ownership, no navigation, no side effects.

### DS-05 Entry file (major) · auto
The entry file sits at the component root and is named after the folder (`emptystate/ElectronEmptyState.kt`).

### DS-06 UI models (blocker for lambdas or domain types, else major) · auto + manual
Sealed class or interface, one `@Immutable data class` per variant. Visual data only:
- no lambdas: signals are component parameters;
- no `Dp`, `Color`, `TextStyle`, `FontWeight`, `Shape`: a semantic enum the variant resolves (`TagTone.Success`, `ButtonSize.Large`);
- no domain or data types;
- nullable for optional elements, never `""` as "absent";
- composite models hold other public UI models (`ListItemLeading.Avatar(AvatarUiModel)`).
A component driven by loose parameters instead of a model is a major finding.

### DS-07 Signals (minor) · auto + manual
One callback parameter per interaction, named `on` + action: `onClick` for the main tap, `on<Element>Click` for a secondary element, `on<Value>Change` for values, a domain verb for a specific action (`onClear`). `() -> Unit` for actions; carry the new value only for changes. The main interaction is required and comes right after `uiModel`; callbacks of optional elements default to `{}` and come after `modifier`. Variants and primitives forward callbacks unchanged.

### DS-08 Composition (blocker) · auto
Composites use other components through their public entries only. Importing another component's `variants` or `primitives` package is a blocker.

### DS-09 No copy (major) · auto + manual
Every visible string comes from the UI model, including content descriptions and accessibility labels. No literal in variants or primitives, no feature string resources.

### DS-10 Tokens only (major) · auto + manual
No raw `dp`, `sp`, `Color(0x…)` or named colors in components. Values come from tokens (`ElectronDimens`, theme colors, typography, shapes, motion). When several platforms share a design, one token source generates all of them. `0.dp` and values inside previews are fine. Letter spacing, line heights and font sizes are tokens too.

### DS-11 Behaviorally neutral (major) · manual
The design system owns how things look, never what they mean: no navigation, no ViewModel, no repository, no connectivity or feature flags, no business rule in a variant. Scaffolds expose slots (`topBar`, `bottomBar`, `content`), not feature switches.

### DS-12 Previews (minor) · auto
Each entry has previews of its variants, light and dark (a dark screenshot test counts). Previews render through an internal preview surface that applies the theme.

### DS-13 Overlays (major) · manual
Dialogs, sheets, menus and tooltips draw their visible surface in a separate internal composable and wrap it in a window or popup only at the variant level, so previews and screenshot tests can render the surface. The scrim is a token. When the platform window dim is unreliable (some emulators and hardware composers skip it), the overlay paints its own scrim and sets the window dim to zero.

### DS-14 State (major) · manual
Components are stateless apart from transient visual state (animation, press, scroll position). Persistent or shared state is hoisted: value plus `on…Change`, or a public `ElectronXState` with `rememberElectronXState()`.

### DS-15 Accessibility (major) · manual
Touch targets of at least 48dp (44pt on iOS). Labels from the model. Rows merge their semantics and expose a role. Text scales with the system font size without clipping (check at 1.5x and 2x). Contrast of text on its container meets WCAG AA.

### DS-16 Screenshot tests (minor; major during a refactor) · manual
Every component and variant in light and dark, text components at font scale 1.5, overlays through their surfaces. They are the proof of visual parity during a refactor, so a refactor without them starts by adding them.

### DS-17 Catalog (minor) · manual
A catalog app or screen shows every public component with its variants and states, built only from the public API. It is the first consumer and the place to see a change before the screens.

## FT: features (the UI boundary)

### FT-01 UI models only (blocker) · auto + manual
UI code (screens, routes, UI mappers to components) never imports `domain` or `data` packages and never names a `…DomainModel`, `…Dto` or `…Entity`. It receives feature UI models (`HomeUiModel`, `CallUiModel`) from presentation.

### FT-02 Route and Screen (major) · auto + manual
- `XRoute`: collects state (`collectAsStateWithLifecycle()`, or the platform equivalent), handles one-shot events (navigation, toasts), owns no layout.
- `XScreen`: stateless, takes the UI state and callbacks or a single `onEvent`, previewable with fake state.

### FT-03 Mappers to components (major) · manual
Feature UI models become component UI models in pure functions next to the feature (`ui/<feature>/mapper/<Feature>ComponentMapper.kt` or `…+Components.swift`). Formatting (money, dates, plurals) happens in presentation or in these mappers, never in the design system. Mappers have unit tests for each branch.

### FT-04 Design system only (blocker for internals, minor for Material) · auto
Screens use public design-system components. Importing a variant or a primitive is a blocker. Raw Material components in a feature while the design system exists are a minor finding: use the component, or add it to the design system.

### FT-05 Copy in features (minor) · manual
Strings live in the feature's resources or in presentation, localized. Screens do not hardcode copy.

### FT-06 Screen tests (minor) · manual
Screens have previews with fake states (content, loading, empty, error) and screen-level screenshot tests for the states that look different.

## CA: clean architecture

### CA-01 Dependency direction (blocker) · manual
`presentation → domain ← data`. Domain is pure Kotlin (or Swift) with no framework, network or storage imports. Data implements the domain's repository interfaces. The UI depends on presentation only.

### CA-02 Use cases (major) · manual
One class per use case, named verb + noun (`PayContributionCall`, `ObserveHousehold`), with one `operator fun invoke`. Presentation calls use cases, never repositories.

### CA-03 Models and mappers (major) · manual
Suffixes say the layer: `XxxDomainModel` in domain, `XxxDto`/`XxxEntity` in data, `XxxUiModel` and `XxxUiState` in presentation. Each boundary has its mapper (data to domain in data, domain to UI in presentation), `internal` to its module, with unit tests.

### CA-04 Presentation state (minor) · manual
One UI state per screen (`XUiState`: loading, content, error), one event type (`XUiEvent`) through `onEvent`, one-shot effects through `SharedFlow` or `Channel`. State exposed with `stateIn(scope, SharingStarted.WhileSubscribed(5_000), initial)`.

### CA-05 Modules (minor) · manual
Domain, data and presentation are separate modules (or at least packages with enforced visibility). On KMP, the iOS framework exports presentation only.

## SwiftUI equivalents

| Rule | SwiftUI |
|---|---|
| DS-01 | `Components/<Name>/{ElectronName.swift, Models/, Variants/, Primitives/}` plus `Foundation/`, `Tokens/`, `Utils/` in the design-system package or target |
| DS-02 | Variants and primitives have no `public` modifier (internal is the default); entries and models are `public` |
| DS-03 | `public struct ElectronName: View` with `let model: NameUiModel`, `body` switches over the model |
| DS-06 | `public enum NameUiModel: Equatable, Sendable` with associated values, or structs; no closures, no `Color`/`CGFloat`, no domain types |
| DS-07 | Closure parameters on the view's init, named `onTap`, `onDismiss`, `on<Element>Tap`; never stored in the model |
| DS-10 | No `.padding(16)`, `.frame(height: 48)`, `Color(red:…)`, `.font(.system(size:))`, `cornerRadius: 12` in components: tokens (`ElectronDimens`, color extensions, typography) |
| DS-12 | `#Preview` light and dark (`.preferredColorScheme(.dark)`) |
| DS-13 | Overlay content in its own view; presentation (`.sheet`, `.fullScreenCover`) at the variant or screen level |
| DS-16 | swift-snapshot-testing (pointfree) or Xcode previews snapshotting, light and dark, Dynamic Type XL |
| FT-01 | Views never name `…DomainModel`, `…Dto`, `…Entity`; on KMP, Swift sees the exported presentation types only |
| FT-02 | `XRoute` observes the store (`@StateObject`/`@Observable` wrapper, `task { for await … }`) and owns navigation; `XScreen` takes state and closures |

iOS stays iOS: the layers and the rules are shared, the look and the platform idioms are not copied from Android.
