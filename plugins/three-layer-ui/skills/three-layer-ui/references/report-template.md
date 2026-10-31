# Report templates

Write in the user's language. Keep file paths clickable (`path/File.kt:42`). Facts only: every count comes from the audit or the manual review, every "done" from a command that ran.

## Audit report (Step 3)

```markdown
# Three-layer UI audit: <project>

**Scope**: <design-system roots>, <feature roots>, <platforms>. Date, commit.
**Result**: <n> blockers, <n> majors, <n> minors (<n> from the script, <n> from the manual review, <n> false positives dropped).

## In one paragraph
What holds, what breaks, and the one or two changes that matter most.

## Components
| Component | Entry | Model | Variants | Primitives | Previews L/D | Screenshot | Catalog | Findings |
|---|---|---|---|---|---|---|---|---|
| ElectronButton | yes | sealed, 5 | 5 | 1 | yes/yes | yes | yes | none |
| ElectronTag | yes | sealed, 2 | 2 | 1 | yes/no | no | yes | DS-10 x2, DS-12 |
| (missing) Banner | no | no | no | no | no | no | no | screens draw it with Material: add it |

## Features
| Feature | Route/Screen | UI models only | Mapper to components | Previews | Findings |
|---|---|---|---|---|---|

## Architecture
Dependency direction, use cases, naming, mappers: one line each, with findings.

## Findings
### <RULE-ID> <rule name> · <severity> · <count>
Why it matters, in one sentence.
| File | Line | Finding |
|---|---|---|

## Fix plan
| # | Step | Items | Size | Visual change |
|---|---|---|---|---|
| 1 | Tooling | explicitApi, screenshot tests, baseline | S | none |
| 2 | Tokens | 6 raw values into tokens | S | none |
| … | | | | |

## Risks
Public API changes and their callers, visual changes, files outside the design system, anything that needs a decision.

## Not checked
What the audit could not see (runtime behavior, platforms without sources, generated code).
```

Then the question (Step 3): fix everything, blockers and majors only, chosen components or rules, or report only; and PR only or PR and merge.

## Final report (Step 8)

```markdown
# Three-layer UI: final report

**Before / after**: blockers <a> to <b>, majors <a> to <b>, minors <a> to <b>.
**PR**: <link>, <open | merged (squash) | not opened, why>.

## What changed
Per component or feature, one line: what moved where, what was added.

## Visual changes
None, or each one with the reason and the screenshot that shows it.

## Verification
| Check | Command | Result |
|---|---|---|
| Build | `./gradlew assembleDebug` | passed |
| Unit tests | `./gradlew test` | 42 passed |
| Screenshot tests | `./gradlew validateDebugScreenshotTest` | 0 diffs (3 re-recorded on purpose) |
| Audit | `python3 audit.py .` | 0 blockers, 0 majors, 4 minors |
| On device | Pixel emulator, light and dark | screens checked: … |

## Skipped or deferred
Each item with why (out of the approved scope, needs a decision, needs a device).

## Next steps
The deferred findings, the CI gate if not added, components still missing from the design system.
```
