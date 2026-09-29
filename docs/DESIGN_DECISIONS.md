# ElectronDS Design Decisions

## Brand identity

ElectronDS is designed for precision software: fintech dashboards, developer
tooling, energy and telemetry products. The visual language is "charged
instrument panel": cool graphite surfaces, one electric primary, one cyan
accent used like an indicator light.

This is a deliberate departure from the source system's warm terracotta and
cream wealth-management identity: same architecture, opposite temperature.

### Color system

| Role | Light | Dark | Token |
|---|---|---|---|
| Primary (Volt) | #3B48D9 | #6577FF | `brand.primary` |
| Accent (Ion) | #0AA394 | #2BE0CE | `brand.accent` |
| Canvas | #F4F6FA | #0B0F1A | `background.canvas` |
| Surface | #FFFFFF | #161B29 | `background.surface` |
| Text primary | #161B29 | #F4F6FA | `content.primary` |
| Success | #108552 | #17A56A | `status.success` |
| Warning | #B87104 | #E8930C | `status.warning` |
| Error | #C53438 | #E5484D | `status.error` |
| Info | #1F6CD6 | #2F81F7 | `status.info` |

Principles:
- The raw palette (`ElectronPalette`) is internal. Features and components
  consume semantic roles only, which is what makes dark theme a pure
  remapping exercise.
- Every status color ships with a `Subtle` container and an `on*` content
  color, so tinted and filled treatments are always available and always AA.
- Interaction states (hover, pressed, selected, disabled) are first-class
  roles instead of per-component alpha hacks.

### Typography

Role-based Material 3 scale with one signature addition: a monospaced
`data` family (`dataLarge/Medium/Small`) for amounts, counters and
identifiers, so digits align in dense numeric layouts. Intended faces are
Space Grotesk (display), Inter (body) and JetBrains Mono (data), wired
through `ElectronFontFamilies` with platform fallbacks until the font files
are licensed and committed.

### Shape

Crisp silhouette: 8dp on controls and fields, 12dp on cards, 20dp reserved
for floating surfaces (sheets, dialogs), and full pills only where the shape
carries meaning (buttons, chips, FAB). No component constructs its own
`RoundedCornerShape`; all consume `ElectronShapes`.

### Spacing and elevation

A 4dp grid (`xxs` 2 to `huge` 64). Elevation is a short scale (flat, raised,
floating, overlay) because Electron surfaces separate with hairline borders
and tonal contrast rather than shadows: cleaner in dark mode and cheaper to
render.

### Motion

Four duration tokens (instant 80ms to emphasized 400ms). Motion is fast and
decisive, like a circuit closing.

## Architectural decisions

1. **Flat semantic colors over deep nesting.** Two levels maximum. The
   source system's five-level paths made call sites unreadable and reviews
   slow.
2. **No dynamic color.** Material You palettes defeat the purpose of a brand
   design system.
3. **Semantic enums replace raw styling in UI models.** `TagTone.Success`
   instead of a `Color`; `AvatarSize.Md` instead of `40.dp`. The system,
   not the feature, decides what those mean per theme.
4. **One callback per interaction.** Components expose a dedicated,
   action-named lambda for every interaction (`ElectronChip(onClick,
   onClear)`, `ElectronSheetHeader(onCloseClick, onResetClick)`), matching
   Compose and Material conventions. No `onSignal` catch-all: each call
   site wires only what it needs and the compiler shows exactly which
   interactions exist.
5. **`explicitApi()` as an architectural tool.** The layer boundary is a
   compiler rule, not a convention.
6. **Feature-flavored components stay out.** Anything encoding product
   vocabulary (payment tabs, debit account cards, error illustrations)
   belongs to feature modules composed from Electron parts. This is the
   "reusable without becoming generic" rule applied in reverse: the library
   also must not become specific.
