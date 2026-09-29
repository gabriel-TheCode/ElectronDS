# ElectronDS Design Decisions

## Brand identity

ElectronDS is designed for precision software: fintech dashboards, developer
tooling, energy and telemetry products. The visual language is "charged
instrument panel": cool graphite surfaces, one electric primary, one cyan
accent used like an indicator light.

This is a deliberate departure from the source system's warm terracotta and
cream wealth-management identity: same architecture, opposite temperature.

## Visual principles

Every visual decision in the system follows from these rules. When two
solutions are possible, the one that respects them wins.

1. **Calm by default, loud on purpose.** Brand color is reserved for the
   primary action, focus and selection. A screen should have one obvious
   thing to do. Selection always speaks the same way: a light brand tint
   (`interaction.selected` for chips on white, `primarySubtle` for moving
   indicators on a track) with `primaryStrong` content.
2. **Tone before lines.** Surfaces separate by tonal steps (canvas,
   surface, raised, sunken). Borders are hairlines, used only where tone is
   too close (cards on canvas, fields). Buttons, selected chips and
   segments have no outline.
3. **Radius follows size.** 4dp labels, 8dp controls, 12dp containers,
   20dp floating surfaces, full pills only where roundness means something
   (removable chips, the FAB, badges, avatars, switches). Nested shapes are
   concentric.
4. **One height per row of controls.** Large buttons and fields are both
   48dp with the same radius, so forms line up without per-screen fixes.
   Everything sits on the 4dp grid.
5. **Every state is explicit, and never color alone.** Pressed scales,
   focused rings, checked switches carry a check glyph, errors carry an
   icon and a message, disabled looks the same on every control.
6. **Motion explains a change.** Colors cross-fade (quick), things that
   move slide on a spring (segmented indicator), new values roll in
   (badges, metrics), entering elements decelerate and leaving ones
   accelerate. Nothing animates for decoration.
7. **Readable everywhere.** Every text role meets WCAG AA (4.5:1) on both
   canvas and surface in both themes. Centered reading content is capped
   at `readableWidth` for tablets and TV. Every focusable control shows a
   focus ring for keyboard and D-pad users.

### Color system

| Role | Light | Dark | Token |
|---|---|---|---|
| Primary (Volt) | #3B48D9 | #6577FF | `brand.primary` |
| Accent (Ion) | #08766C | #2BE0CE | `brand.accent` |
| Canvas | #F4F6FA | #0B0F1A | `background.canvas` |
| Surface | #FFFFFF | #161B29 | `background.surface` |
| Raised | #FFFFFF | #272E40 | `background.surfaceRaised` |
| Sunken | #E9EDF4 | #1E2434 | `background.surfaceSunken` |
| Text primary | #161B29 | #F4F6FA | `content.primary` |
| Text muted | #646E87 | #97A0B4 | `content.muted` |
| Success | #0E774A | #3DD68C | `status.success` |
| Warning | #9A5B00 | #E8930C | `status.warning` |
| Error | #C53438 | #FF6B6E | `status.error` |
| Info | #1A62C8 | #58A0FF | `status.info` |

Principles:
- The raw palette (`ElectronPalette`) is internal. Features and components
  consume semantic roles only, which is what makes dark theme a pure
  remapping exercise.
- Every status color ships with a `Subtle` container and an `on*` content
  color, so tinted and filled treatments are always available and always AA.
- Interaction states (hover, pressed, selected, disabled) are first-class
  roles instead of per-component alpha hacks.
- Dark mode is a remapping, not an inversion: surfaces get lighter as they
  come forward, status colors move to their lighter steps for contrast, and
  each subtle container keeps its hue (a dark error card is dark red, not
  the same grey as a warning card).
- The Material 3 bridge maps every role, including the surface container
  scale and `surfaceTint`, so no Material component can fall back to the
  baseline lavender.

### Typography

Role-based Material 3 scale with one signature addition: a monospaced
`data` family (`dataDisplay/Large/Medium/Small`) for amounts, counters and
identifiers, so digits align in dense numeric layouts. `dataDisplay` (32sp)
is the hero figure of a dashboard. Actions (`labelLarge`) are SemiBold so a
button always outweighs the text around it; large sizes carry slightly
negative tracking. The role rules live in the `ElectronTypography` KDoc. Intended faces are
Space Grotesk (display), Inter (body) and JetBrains Mono (data), wired
through `ElectronFontFamilies` with platform fallbacks until the font files
are licensed and committed.

### Shape

Crisp silhouette, radius growing with size: 4dp on tags, 8dp on controls
(buttons, fields, segmented tracks), 12dp on cards, 20dp on floating
surfaces (sheets, dialogs), and full pills only where roundness carries
meaning (chips, FAB, badges, avatars, switches). Buttons are 8dp, not
pills, so a button and the field above it share one silhouette. Nested
shapes are concentric (`segment` = control radius minus its inset). No
component constructs its own `RoundedCornerShape`; all consume
`ElectronShapes`.

### Spacing and elevation

A 4dp grid (`xxs` 2 to `huge` 64). Elevation is a short scale (flat, raised,
floating, overlay) because Electron surfaces separate with hairline borders
and tonal contrast rather than shadows: cleaner in dark mode and cheaper to
render.

### Motion

Four duration tokens (instant 80ms to emphasized 400ms) and three easing
curves (`easeStandard`, `easeEnter`, `easeExit`). Motion is fast and
decisive, like a circuit closing, and only explains a change:

| Change | Treatment |
|---|---|
| State color (button Default to Success, field focus) | cross-fade, `quick`, `easeStandard` |
| Press | 3% scale-down, critically damped spring |
| Focus | 2dp `border.focus` ring, `instant` |
| Position (segmented indicator) | spring, no bounce |
| New value (badge count, metric figure) | vertical roll, `easeEnter` in, `easeExit` out |
| Appearing message (field error/helper) | fade in with animated height |
| Progress value | glide over `standard` |

Press and focus feedback are shared by every tappable control through
`utils/ElectronInteraction.kt`, so a button, a chip, a segment and the FAB
answer the same way. Full-width rows (list items) keep the ripple only:
scaling a full-width surface reads as layout jitter.

## Architectural decisions

1. **Flat semantic colors over deep nesting.** Two levels maximum. The
   source system's five-level paths made call sites unreadable and reviews
   slow.
2. **No dynamic color.** Material You palettes defeat the purpose of a brand
   design system.
3. **Semantic enums replace raw styling in UI models.** `TagTone.Success`
   instead of a `Color`; `AvatarSize.Md` instead of `40.dp`. The system,
   not the feature, decides what those mean per theme.
4. **One callback per signal.** Each user interaction a component emits
   has its own action-named callback (`ElectronChip(onClick, onClear)`,
   `ElectronSheetHeader(onCloseClick, onResetClick)`), matching Compose and
   Material conventions: each call site wires only what it needs and the
   compiler shows exactly which interactions exist.
5. **`explicitApi()` as an architectural tool.** The layer boundary is a
   compiler rule, not a convention.
6. **Feature-flavored components stay out.** Anything encoding product
   vocabulary (payment tabs, debit account cards, error illustrations)
   belongs to feature modules composed from Electron parts. This is the
   "reusable without becoming generic" rule applied in reverse: the library
   also must not become specific.
