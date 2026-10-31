# Fix playbook

Templates for each fix, in the order of SKILL.md Step 5. Replace `Electron` with the project's prefix and keep the project's own conventions (package names, file headers, KDoc density) wherever they do not break a rule.

## Migrating one component

1. Record its current look (screenshot test or preview render) if not already in the baseline.
2. Create `components/<name>/` with the entry, `models/`, `variants/`, `primitives/`.
3. Write the sealed model: one subclass per visual variant the callers actually use. Replace style parameters with semantic enums.
4. Move the drawing code into a primitive taking raw values. Move token resolution into `variants/<Name>Styling.kt`.
5. Write one variant per subclass, the entry dispatching on the model, the previews.
6. Migrate every caller to `ElectronName(uiModel = …, on… = …)`, then delete the old function.
7. Build, run the screenshot tests: zero diffs.

## Kotlin (Compose)

### Gradle

```kotlin
// designsystem/build.gradle.kts
kotlin {
    explicitApi()
}
dependencies {
    // Types used in public signatures (PaddingValues, LazyListScope…) must be `api`.
    api(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
}
```

### Model: `components/tag/models/TagUiModel.kt`

```kotlin
public enum class TagTone { Brand, Neutral, Success, Warning, Error }
public enum class TagSize { Small, Medium }

public sealed class TagUiModel {
    @Immutable
    public data class Text(
        val text: String,
        val tone: TagTone = TagTone.Neutral,
        val size: TagSize = TagSize.Medium,
    ) : TagUiModel()

    @Immutable
    public data class Dot(val text: String, val tone: TagTone) : TagUiModel()
}
```

### Styling: `components/tag/variants/TagStyling.kt`

```kotlin
internal data class TagColors(val container: Color, val content: Color)

@Composable
internal fun TagTone.colors(): TagColors {
    val c = ElectronTheme.colors
    return when (this) {
        TagTone.Brand -> TagColors(c.brandSubtle, c.brand)
        TagTone.Neutral -> TagColors(c.surfaceSunken, c.contentSecondary)
        TagTone.Success -> TagColors(c.successSubtle, c.success)
        TagTone.Warning -> TagColors(c.warningSubtle, c.warning)
        TagTone.Error -> TagColors(c.errorSubtle, c.error)
    }
}

internal fun TagSize.padding(): PaddingValues = when (this) {
    TagSize.Small -> PaddingValues(ElectronSpacing.sm, ElectronSpacing.xxs)
    TagSize.Medium -> PaddingValues(ElectronSpacing.md, ElectronSpacing.xs)
}
```

### Primitive: `components/tag/primitives/TagPrimitive.kt`

```kotlin
@Composable
internal fun TagPrimitive(
    text: String,
    container: Color,
    content: Color,
    padding: PaddingValues,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = content,
        style = textStyle,
        modifier = modifier.background(container, ElectronShapes.pill).padding(padding),
    )
}
```

### Variant: `components/tag/variants/TagText.kt`

```kotlin
@Composable
internal fun TagText(uiModel: TagUiModel.Text, modifier: Modifier = Modifier) {
    val colors = uiModel.tone.colors()
    TagPrimitive(
        text = uiModel.text,
        container = colors.container,
        content = colors.content,
        padding = uiModel.size.padding(),
        textStyle = ElectronTypography.labelMedium,
        modifier = modifier,
    )
}
```

### Entry: `components/tag/ElectronTag.kt`

```kotlin
/** Compact status or category marker. */
@Composable
public fun ElectronTag(uiModel: TagUiModel, modifier: Modifier = Modifier) {
    when (uiModel) {
        is TagUiModel.Text -> TagText(uiModel, modifier)
        is TagUiModel.Dot -> TagDot(uiModel, modifier)
    }
}

@Preview
@Composable
private fun ElectronTagPreview() = ElectronPreviewSurface { /* every variant */ }

@Preview
@Composable
private fun ElectronTagDarkPreview() = ElectronPreviewSurface(dark = true) { /* every variant */ }
```

Interactive components put the main signal right after `uiModel`, optional ones after `modifier`:

```kotlin
@Composable
public fun ElectronChip(
    uiModel: ChipUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClear: () -> Unit = {},
)
```

### Composite

A variant may call public entries of other components, never their internals; its model holds their public models.

```kotlin
public sealed class ListItemLeading {
    @Immutable public data class Avatar(val avatar: AvatarUiModel) : ListItemLeading()
    @Immutable public data class Icon(val icon: IconUiModel) : ListItemLeading()
}

@Composable
internal fun ListItemNavigation(uiModel: ListItemUiModel.Navigation, onClick: () -> Unit, modifier: Modifier) {
    ListItemRowPrimitive(onClick = onClick, modifier = modifier) {
        when (val leading = uiModel.leading) {
            is ListItemLeading.Avatar -> ElectronAvatar(leading.avatar)
            is ListItemLeading.Icon -> ElectronIcon(leading.icon)
            null -> Unit
        }
        /* … */
    }
}
```

### Overlay

```kotlin
// variants/DialogDefault.kt: the only place that opens a window.
@Composable
internal fun DialogDefault(uiModel: DialogUiModel.Default, onConfirmClick: () -> Unit, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest, DialogProperties(usePlatformDefaultWidth = false, decorFitsSystemWindows = false)) {
        // Some hardware composers skip the window dim: paint the scrim, zero the platform dim.
        (LocalView.current.parent as? DialogWindowProvider)?.window?.setDimAmount(0f)
        Box(Modifier.fillMaxSize().background(ElectronTheme.colors.scrim).clickable(onClick = onDismissRequest)) {
            DialogBody(uiModel, onConfirmClick, onDismissRequest)   // previews and screenshot tests render this
        }
    }
}
```

### State holder

```kotlin
@Stable
public class ElectronToastState internal constructor() { /* show(), current */ }

@Composable
public fun rememberElectronToastState(): ElectronToastState = remember { ElectronToastState() }
```

### Tokens

Raw values found by DS-10 go to the token source. If the project generates tokens (a JSON file and a script), add the token there and regenerate; never edit generated files. Typical additions: letter spacing, font sizes of display styles, dash and stroke lengths, scrim color, elevation.

```kotlin
public object ElectronDimens {
    public val wordmarkSize: TextUnit = 20.sp
    public val labelTracking: TextUnit = 1.5.sp
}
```

## Features

### Route and Screen

```kotlin
@Composable
fun HomeRoute(store: HomeStore, onOpenRelative: (String) -> Unit) {
    val state by store.state.collectAsStateWithLifecycle()
    LaunchedEffect(store) {
        store.effects.collect { effect ->
            when (effect) {
                is HomeUiEffect.OpenRelative -> onOpenRelative(effect.id)
            }
        }
    }
    HomeScreen(state = state, onEvent = store::onEvent)
}

@Composable
fun HomeScreen(state: HomeUiState, onEvent: (HomeUiEvent) -> Unit) {
    when (state) {
        HomeUiState.Loading -> ElectronSkeleton(/* … */)
        is HomeUiState.Content -> HomeContent(state.content, onEvent)
        is HomeUiState.Error -> ElectronEmptyState(state.toEmptyStateUiModel(), onRetryClick = { onEvent(HomeUiEvent.Retry) })
    }
}

@Preview @Composable
private fun HomeScreenPreview() = ElectronTheme { HomeScreen(HomeUiState.Content(HomeUiModel.preview), onEvent = {}) }
```

### Mapper to components: `ui/home/mapper/HomeComponentMapper.kt`

```kotlin
internal fun CallUiModel.toTicketUiModel(): TicketUiModel = TicketUiModel.Call(
    title = title,
    amount = amountLabel,
    status = if (isLate) TagUiModel.Dot(statusLabel, TagTone.Error) else TagUiModel.Dot(statusLabel, TagTone.Neutral),
)
```

Each branch gets a unit test (plain JUnit or `kotlin.test`).

### Removing a domain leak (FT-01)

1. Find the domain type the screen reads.
2. Add the fields the screen needs to the feature `XUiModel` (already formatted: labels, amounts as text, enums for tone).
3. Map it in presentation (`domain → UiModel`), test the mapper.
4. Change the screen to read the `UiModel`, then map it to component models in the UI mapper.

## Clean architecture

```kotlin
// domain/models/HouseholdDomainModel.kt
data class HouseholdDomainModel(val id: String, val members: List<MemberDomainModel>)

// domain/repositories/HouseholdRepository.kt
interface HouseholdRepository { fun observe(): Flow<HouseholdDomainModel?> }

// domain/usecases/ObserveHousehold.kt
class ObserveHousehold(private val repository: HouseholdRepository) {
    operator fun invoke(): Flow<HouseholdDomainModel?> = repository.observe()
}

// presentation/home/HomeStore.kt
class HomeStore(observeHousehold: ObserveHousehold, scope: CoroutineScope) {
    val state: StateFlow<HomeUiState> = observeHousehold()
        .map { it?.let { household -> HomeUiState.Content(household.toUiModel()) } ?: HomeUiState.Loading }
        .stateIn(scope, SharingStarted.WhileSubscribed(5_000), HomeUiState.Loading)

    private val _effects = MutableSharedFlow<HomeUiEffect>()
    val effects: SharedFlow<HomeUiEffect> = _effects

    fun onEvent(event: HomeUiEvent) { /* … */ }
}

// presentation/home/mapper/HomeUiMapper.kt
internal fun HouseholdDomainModel.toUiModel(): HomeUiModel = /* … */
```

On KMP, the shared framework exports presentation only: `api(project(":core:presentation"))`, `implementation` for domain and data.

## SwiftUI

```swift
// Components/Tag/Models/TagUiModel.swift
public enum TagTone: Sendable { case brand, neutral, success, warning, error }

public enum TagUiModel: Equatable, Sendable {
    case text(String, tone: TagTone = .neutral)
    case dot(String, tone: TagTone)
}

// Components/Tag/Variants/TagText.swift (internal by default)
struct TagText: View {
    let text: String
    let tone: TagTone
    var body: some View {
        let colors = tone.colors
        TagPrimitive(text: text, container: colors.container, content: colors.content)
    }
}

// Components/Tag/Primitives/TagPrimitive.swift
struct TagPrimitive: View {
    let text: String
    let container: Color
    let content: Color
    var body: some View {
        Text(text)
            .font(ElectronTypography.label)
            .foregroundStyle(content)
            .padding(.horizontal, ElectronDimens.spaceMd)
            .padding(.vertical, ElectronDimens.spaceXs)
            .background(container, in: Capsule())
    }
}

// Components/Tag/ElectronTag.swift
public struct ElectronTag: View {
    private let model: TagUiModel
    public init(_ model: TagUiModel) { self.model = model }
    public var body: some View {
        switch model {
        case let .text(text, tone): TagText(text: text, tone: tone)
        case let .dot(text, tone): TagDot(text: text, tone: tone)
        }
    }
}

#Preview { ElectronTag(.text("Paid", tone: .success)) }
#Preview("Dark") { ElectronTag(.text("Paid", tone: .success)).preferredColorScheme(.dark) }
```

Route and Screen: `HomeRoute` holds the store observation and navigation; `HomeScreen(state:onEvent:)` is a plain view with previews.

## Screenshot tests

Pick by stack; do not mix two tools in one module.

| Stack | Tool | Record | Verify |
|---|---|---|---|
| AGP 9.5.0-alpha03 or higher | Compose Preview Screenshot Testing as an AGP test suite (`testOptions { screenshotTests.create(…) }`) | see the suite's update task | see the suite's validate task |
| AGP 8.5 to 9.4 | Compose Preview Screenshot Testing plugin `com.android.compose.screenshot` (deprecated standalone plugin; say so) | `./gradlew :designsystem:updateDebugScreenshotTest` | `./gradlew :designsystem:validateDebugScreenshotTest` |
| Any AGP, JVM, no device | Paparazzi (`app.cash.paparazzi`) | `./gradlew :designsystem:recordPaparazziDebug` | `./gradlew :designsystem:verifyPaparazziDebug` |
| SwiftUI | swift-snapshot-testing (pointfree) | `isRecording = true` once | `swift test` / Xcode test |

Standalone plugin setup:

```properties
# gradle.properties
android.experimental.enableScreenshotTest=true
```

```kotlin
// designsystem/build.gradle.kts
plugins { alias(libs.plugins.screenshot) }
android { experimentalProperties["android.experimental.enableScreenshotTest"] = true }
dependencies {
    screenshotTestImplementation(libs.screenshot.validation.api)
    screenshotTestImplementation(libs.androidx.compose.ui.tooling)
}
```

```kotlin
// designsystem/src/screenshotTest/kotlin/…/TagScreenshots.kt
@PreviewTest
@Preview(name = "light")
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "font 1.5", fontScale = 1.5f)
@Composable
fun TagScreenshot() = ElectronTheme { /* every variant */ }
```

Overlays are captured through their surface composables (`DialogBody`), since windows cannot render off-device. Review every recorded image before committing it: a golden image is a design decision.

## Catalog

An application module that depends on the design system only, with one section per component showing every variant and state, overlays opened from buttons, and a light/dark toggle. It uses the public API exclusively; if a section needs an internal, the public API is missing something.

## CI gate (optional, ask first)

```yaml
# .github/workflows/three-layer-ui.yml
name: Three-layer UI audit
on: [pull_request]
jobs:
  audit:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-python@v5
        with: { python-version: "3.12" }
      - run: python3 tools/three-layer-ui/audit.py .   # fails on blockers
```

Copy `scripts/audit.py` into the project (for example `tools/three-layer-ui/audit.py`) when the user wants the gate.
