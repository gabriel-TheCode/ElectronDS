# ElectronDS

A production-grade Jetpack Compose design system built on the 3-layered UI
architecture: Primitive, Variant, Component. Extracted and refactored from
an existing design system codebase, rebuilt with a brand-new visual identity.

## Modules

| Module | Description |
|---|---|
| `:electronds` | The design system library. Only Components, UI models, tokens and the theme are public. Variants and primitives are `internal`, enforced by Kotlin `explicitApi()`. |
| `:app` | A component catalog demonstrating feature-level usage: screen UI state, UI mappers, component UI models and upward signals. |

## Quick start

```kotlin
ElectronTheme {
    ElectronButton(
        uiModel = ButtonUiModel.Primary(text = "Confirm transfer", isFullWidth = true),
        onClick = viewModel::onConfirmClicked
    )
}
```

## The three layers

```
Feature (screen)          ViewModel -> Screen UI State -> UI Mapper
                                         |
Design system (public)    Component UI Model -> Component
                                         |
Design system (internal)  Variant -> Primitive -> tokens
```

- **Primitive**: stateless visual block. Raw parameters, tokens, no UI models, no meaning.
- **Variant**: one stable visual configuration. Resolves semantic enums (size, tone, state) into raw values and delegates to the primitive.
- **Component**: the only public composable. Dispatches on the sealed UI model and exposes signals (`onClick`, `onSignal`) upward.

## Components shipped

ElectronButton, ElectronIcon, ElectronAvatar, ElectronTag, ElectronChip,
ElectronSwitch, ElectronFab, ElectronCard, ElectronSheetHeader,
ElectronInputField, plus the behavior-agnostic ElectronScaffold.

## Documentation

- `docs/ARCHITECTURE.md`: rules of the 3-layer architecture as enforced here.
- `docs/DESIGN_DECISIONS.md`: the Electron brand identity, tokens and rationale.
- `docs/USAGE_GUIDELINES.md`: how feature teams consume the system.

## Building

Open the project in Android Studio (Ladybug or newer). The Gradle wrapper
properties point to Gradle 8.11.1; run `gradle wrapper` once or let Android
Studio provision it. Brand font binaries (Space Grotesk, Inter, JetBrains
Mono) are not committed; the type scale resolves to platform fallbacks until
they are added under `electronds/src/main/res/font` and wired in
`ElectronFontFamilies`.
