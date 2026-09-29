# Screenshot tests

ElectronDS locks its visual quality with [Paparazzi](https://github.com/cashapp/paparazzi)
screenshot tests: components are rendered on the JVM with Android's
layoutlib (no emulator, no device) and compared pixel by pixel with
committed golden images. A 1dp padding change, a color step or a broken
dark theme fails the build with a visual diff.

## What is covered

| Test | Renders | Matrix |
|---|---|---|
| `ComponentGalleryScreenshotTest` | Every component family with each state that looks different (default, disabled, error, read-only, loading, selected, success), plus overlay surfaces (dialog, sheet, menu, tooltips) | Light, Dark |
| `AdaptiveLayoutScreenshotTest` | Full screens: dashboard (bottom bar on phone, rail on tablet/TV), empty state (readable width), dialog and sheet at their platform max widths | Light, Dark x Phone, Tablet, TV |
| `AccessibilityScreenshotTest` | What TalkBack announces: one node per field (label + value + error), toggle rows as a single switch, labelled icon-only buttons, one "loading" announcement per skeleton | Light |

Golden images live in `electronds/src/test/snapshots/images/`.

Overlays are captured through their internal surfaces (`DialogBody`,
`BottomSheetDefaultBody`, `MenuDefaultPanel`, `TooltipPlainSurface`,
`TooltipRichSurface`): windows and popups cannot be rendered off-device,
so every overlay keeps its visible surface in a separate composable.

## Commands

```bash
# Record (or re-record) golden images after an intentional visual change
./gradlew :electronds:recordPaparazziDebug

# Verify: fails on any difference above 0.1% and writes diffs to
# electronds/build/paparazzi/failures
./gradlew :electronds:verifyPaparazziDebug
```

Always review the recorded images before committing them: a golden image
is a design decision, not a test artifact.

## CI

`.github/workflows/screenshot-tests.yml` verifies snapshots on every pull
request and on `main`, and uploads the visual diffs as an artifact when it
fails.

To record golden images without a local Android SDK, run the workflow
manually (Actions > Screenshot tests > Run workflow) on your branch with
**record** checked: it records on GitHub's runner and commits the images to
that branch. Run it once after adding a component or changing a visual
decision, review the committed images in the pull request, then let the
normal verification take over.

## Writing a new snapshot

- Add the component to the matching gallery with every state that has a
  distinct appearance; skip states that only differ in behavior.
- If the component changes with the screen size (navigation, sheets,
  dialogs, reading content), add it to `AdaptiveLayoutScreenshotTest`.
- If you designed its semantics (merged nodes, custom roles), add it to
  `AccessibilityScreenshotTest`.
- Keep content deterministic: no current dates, no random data. Infinite
  animations (skeleton shimmer) are captured at their first frame.
