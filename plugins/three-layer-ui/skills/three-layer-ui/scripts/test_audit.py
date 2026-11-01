"""Self-check of audit.py on a tiny project with one known violation per rule. Run: python3 test_audit.py"""
import json
import subprocess
import sys
import tempfile
from pathlib import Path

FILES = {
    "designsystem/build.gradle.kts": "kotlin { }\n",
    "designsystem/src/main/kotlin/acme/designsystem/components/tag/AcmeTag.kt": """package acme.designsystem.components.tag
@Composable
public fun AcmeTag(uiModel: TagUiModel, tap: () -> Unit) { }
""",
    "designsystem/src/main/kotlin/acme/designsystem/components/tag/models/TagUiModel.kt": """package acme.designsystem.components.tag.models
public sealed class TagUiModel {
    public data class Text(val text: String, val onTap: () -> Unit, val color: Color) : TagUiModel()
}
""",
    "designsystem/src/main/kotlin/acme/designsystem/components/tag/variants/TagText.kt": """package acme.designsystem.components.tag.variants
import acme.designsystem.components.chip.primitives.ChipPrimitive
@Composable
public fun TagText() { Text("Hello", modifier = Modifier.padding(12.dp)) }
""",
    "designsystem/src/main/kotlin/acme/designsystem/components/badge/variants/BadgeDefault.kt": "package acme\n",
    "app/src/main/kotlin/acme/ui/home/HomeScreen.kt": """package acme.ui.home
import acme.domain.models.HouseholdDomainModel
import acme.designsystem.components.tag.variants.TagText
@Composable
fun HomeScreen(store: Store) { val s by store.state.collectAsStateWithLifecycle(); Text("Rien à régler") }
""",
    # Kotlin Multiplatform marker: a shared module.
    "shared/src/commonMain/kotlin/acme/Shared.kt": "package acme\n",
    "ios/Kit/Sources/AcmeDesignSystem/Components/Tag/AcmeTag.swift": """import SwiftUI
public struct AcmeTag: View {
    public var body: some View { TagPill() }
}
#Preview { AcmeTag() }
""",
    "ios/Kit/Sources/AcmeDesignSystem/Components/Tag/Models/TagUiModel.swift": "public enum TagUiModel: Hashable { case pill(String) }\n",
    "ios/Kit/Sources/AcmeDesignSystem/Components/Tag/Variants/TagPill.swift": """import SwiftUI
struct TagPill: View {
    var body: some View { Image(systemName: "clock").font(.system(size: AcmeDimens.icon)) }
}
""",
    "ios/Kit/Sources/AcmeDesignSystem/Components/Chip/Models/ChipUiModel.swift": "public enum ChipUiModel: Hashable { case fact(String) }\n",
    "ios/Kit/Sources/AcmeDesignSystem/Components/Chip/Variants/ChipFact.swift": """import SwiftUI
struct ChipFact: View {
    var body: some View { TagPill() }
}
""",
    "ios/App/Features/Home/HomeUiMapper.swift": """import Shared
extension Tone {
    var label: String {
        switch self {
        case .success: "Tout est en ordre"
        default: "À régler"
        }
    }
}
""",
}

EXPECTED = {
    ("DS-02", "major"),    # no explicitApi
    ("DS-02", "blocker"),  # public variant
    ("DS-05", "major"),    # badge has no entry
    ("DS-06", "major"),    # badge has no models/, Color in a model
    ("DS-06", "blocker"),  # lambda in a model
    ("DS-07", "minor"),    # `tap` callback
    ("DS-08", "blocker"),  # imports chip primitives
    ("DS-09", "major"),    # literal in a variant
    ("DS-10", "major"),    # 12.dp
    ("DS-12", "minor"),    # no preview
    ("FT-01", "blocker"),  # domain model in the UI
    ("FT-02", "major"),    # screen collects state
    ("FT-04", "blocker"),  # screen imports a variant
    ("DS-08", "blocker"),  # Swift: Chip names Tag's internal TagPill
    ("DS-15", "minor"),    # Swift: .system(size:)
    ("FT-07", "major"),    # wording in platform UI code of a KMP app (Kotlin and Swift)
    ("FT-08", "minor"),    # KMP + SwiftUI without SKIE, and `default:` in a Swift switch
}

with tempfile.TemporaryDirectory() as tmp:
    root = Path(tmp)
    for path, text in FILES.items():
        (root / path).parent.mkdir(parents=True, exist_ok=True)
        (root / path).write_text(text)
    run = subprocess.run([sys.executable, str(Path(__file__).with_name("audit.py")), str(root), "--format", "json"],
                         capture_output=True, text=True)
    found = {(f["rule"], f["severity"]) for f in json.loads(run.stdout)["findings"]}
    missing = EXPECTED - found
    assert not missing, f"not detected: {sorted(missing)}"
    assert run.returncode == 1, "blockers must exit with 1"
print(f"ok: {len(EXPECTED)} rule checks detected")
