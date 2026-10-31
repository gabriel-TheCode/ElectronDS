---
name: three-layer-ui
description: Audits and fixes a Jetpack Compose (Android, KMP, Compose Multiplatform) or SwiftUI codebase against the three-layer UI architecture (Primitive, Variant, Component) and a clean UI boundary where screens consume UI models only, never domain models. Inventories every design-system component, details what each one is missing, asks before fixing, fixes bottom-up with visual parity, verifies, updates the docs, opens and merges the PR when allowed, and ends with a report. Use when the user asks to audit, review, enforce, refactor or migrate a design system or its screens; mentions the three-layer or 3-layer UI architecture, ElectronDS, Primitive/Variant/Component, sealed UI models, UiModel vs DomainModel, Route/Screen, or explicitApi on a design system; or asks whether their components follow their UI guidelines.
---

# Three-layer UI audit and fix

You audit a codebase against two contracts, report every gap, and fix only what the user approves.

1. **The three-layer design system.** Each component is a public **Component** (one entry, a sealed UI model, signals up) over internal **Variants** (resolve tokens for one model subclass) over internal **Primitives** (render raw resolved values). Tokens are the only source of values.
2. **The clean UI boundary.** Screens consume UI models only. Domain and data types never reach the UI. Presentation talks to use cases, maps domain models to feature UI models, and the screen maps those to component UI models.

The reference implementation is [ElectronDS](https://github.com/gabriel-TheCode/ElectronDS) (Compose). Its `docs/ARCHITECTURE.md` and `docs/USAGE_GUIDELINES.md` are the long form of the rules; [references/rules.md](references/rules.md) is the checklist you apply.

Files in this skill, read when the step needs them:

| File | Read at |
|---|---|
| `scripts/audit.py` | Step 2: static checks, Kotlin and Swift (Python 3.9+, no dependencies) |
| `references/rules.md` | Step 2: every rule, its severity and how to check it by hand |
| `references/report-template.md` | Steps 3 and 8: audit report and final report |
| `references/fix-playbook.md` | Step 5: templates and the order of work |

## Step 1. Map the project

Read before judging. Record, in a short working note:

- **Stack**: Gradle files (AGP and Kotlin versions, KMP or Android only), `Package.swift` or `.xcodeproj` for SwiftUI.
- **Design system roots**: modules or packages that hold components (`designsystem`, `ds`, `ui-kit`, `LRCDesignSystem`…). The component prefix (`Electron`, `Lrc`…).
- **Feature UI roots**: `ui/`, `presentation/`, `feature*/`, `Screens/`.
- **Architecture**: domain, data and presentation modules or packages; use cases; how state reaches the screens (ViewModel, Store, MVI).
- **Tokens**: where colors, dimensions, type and motion come from; whether one source generates both platforms.
- **Tests and tools**: unit tests, screenshot tests (Paparazzi, Compose Preview Screenshot Testing, Roborazzi, swift-snapshot-testing), catalog app, CI workflows.
- **House rules**: `CLAUDE.md`, `AGENTS.md`, `CONTRIBUTING.md`, PR template, branch naming, merge strategy, commit style. They win over this skill on anything they cover.
- **Working tree**: `git status`. Never fold the user's unrelated changes into your commits.

If there is no design system at all, say so and offer to bootstrap one from the screens' repeated UI instead of auditing; that is a different job.

## Step 2. Audit

Run the static checks from the repository root (the path is relative to this skill's folder):

```bash
python3 <skill-dir>/scripts/audit.py . --format json --out /tmp/three-layer-ui-before.json
python3 <skill-dir>/scripts/audit.py .
```

Pass `--ds <path>` and `--features <path>` (repeatable) when discovery misses or mislabels a root; the markdown output lists what it found. The script reads files; it does not understand meaning. Then do the manual review in [references/rules.md](references/rules.md), every rule marked **manual**, component by component. Open each component's entry, models, variants and primitives; open each screen and its mappers.

Build the **component inventory**: one row per component with entry, models, variants, primitives, previews (light/dark), screenshot test, catalog entry, and its findings. Do the same for features (Route/Screen split, mappers, domain leaks) and for the architecture (dependency direction, use cases, naming).

Severity:
- **blocker**: breaks the contract (public variant or primitive, lambda in a UI model, domain type in the UI, a composite importing another component's internals).
- **major**: weakens it (raw values, copy inside the design system, no sealed model, missing entry, screen without a mapper).
- **minor**: hygiene (callback naming, missing dark preview, missing catalog entry).

Drop false positives you can justify (a generated file, a value that is a token by another name) and say why in the report.

## Step 3. Report and ask

Write the audit report with [references/report-template.md](references/report-template.md): summary counts, component inventory, findings grouped by rule with file and line, the fix plan in the order of Step 5 with a size for each item (S, M, L), the risks (public API changes, visual changes, files touched outside the design system).

Then ask the user what to fix. Use the platform's question tool when there is one, otherwise ask in plain text:

1. Everything (recommended when blockers exist)
2. Blockers and majors only
3. Chosen components or rules (the user lists them)
4. Report only, no change

Also ask, in the same message, whether to ship at the end: open a PR only, or open and merge it. Do not change a file before the answer. If the user already said what they want in the request ("audit and fix everything, then merge"), that is the answer: state it and go on.

## Step 4. Prepare

- Create a branch following the house rules (default `refactor/three-layer-ui`). Never commit on the default branch.
- **Lock the current look before touching anything.** If screenshot tests exist, run them green first. If not, set them up now (playbook, "Screenshot tests") and record the baseline from the current code: these images are what "no visual regression" means for the rest of the job.

## Step 5. Fix, bottom-up

Work in this order; each layer only depends on the ones before it. Build after each group, not only at the end.

1. **Tooling**: `explicitApi()` on the design-system module, screenshot-test setup, the audit in CI if the user wants it.
2. **Tokens**: move raw values into the token source (and its generator when one exists); add the semantic or component tokens the fixes need.
3. **Foundation**: theme, colors, typography, shapes, tone enums, preview surfaces.
4. **Leaf components** (no other component inside): button, icon, tag, avatar, field…
5. **Composites**, after the components they use.
6. **Overlays**: dialog, sheet, menu, tooltip, with the surface split from the window.
7. **Features**: Route/Screen split, feature UI models, mappers to component UI models, removal of domain types from the UI, callers migrated to the new APIs.
8. **Architecture**, only when in scope: use cases with one `invoke`, `DomainModel`/`UiModel` naming, mappers at each boundary, dependency direction.
9. **Previews, screenshot tests, catalog** for every component you created or changed.

Per component, follow the playbook: folder `components/<name>/` with the entry, `models/`, `variants/`, `primitives/`; sealed `@Immutable` model; internal variants resolving tokens in a `*Styling.kt`; internal primitives taking raw values; one callback per interaction named `on…`; previews light and dark.

Rules while fixing:
- **Visual parity.** A structural refactor changes no pixel. Only a finding that is visual (raw color, wrong token) may change the look, and the report names each such change.
- **Public API.** When a signature changes, migrate every caller in the same change; do not leave the old API next to the new one.
- **Scope.** Fix what was approved. Note anything else you find for the final report instead of fixing it.
- **SwiftUI** gets the same layers with the platform's idiom (playbook, "SwiftUI"); iOS stays iOS, Android stays Android.

## Step 6. Verify

All of these, and report each result as it is:

- Clean build of every touched module (and the app, and the catalog).
- Unit tests, including the new mapper tests.
- Screenshot tests against the baseline: zero diffs, or only the visual changes listed in the report, each re-recorded on purpose and reviewed.
- The audit again, saved as `/tmp/three-layer-ui-after.json`: zero blockers; every remaining major either fixed or justified.
- When a device, emulator or simulator is available: run the app and the catalog, open the screens you touched, light and dark, and look.

Do not call the job done with a failing step. Fix it, or stop and report it.

## Step 7. Document and ship

- Update the project's docs: the architecture rules (link ElectronDS's `docs/ARCHITECTURE.md` or copy the contract), the component list, how to run the audit, the screenshot tests and the catalog. Change the README only where it is wrong or silent.
- Commit in coherent steps with the house commit style. Push, open the PR with the report summary (counts before and after, what changed visually, how to verify).
- Wait for CI. If it fails, fix and push again.
- Merge only if the user chose "open and merge", with the house strategy (default: squash, delete the branch). Otherwise leave the PR open and give its link.

## Step 8. Final report

Use the final-report section of [references/report-template.md](references/report-template.md): what was audited, findings before and after by severity, what was fixed per component, visual changes, what was skipped and why, verification results, the PR and merge state, and the next steps (deferred findings, CI gate, components still missing).

## Ground rules

- UI models carry visual data only: no lambdas, no `Dp`/`Color`/`TextStyle`, no domain types. Nullable fields mean "absent", never empty strings.
- Variants and primitives are internal; composites use public components only.
- The design system is visually authoritative and behaviorally neutral: it never decides what a signal means, never navigates, never reads feature state.
- Features never see `DomainModel`, `Dto` or `Entity` types; they see their own `UiModel`s and the components' UI models.
- Report results faithfully: a skipped step is reported as skipped, a failing test as failing.
