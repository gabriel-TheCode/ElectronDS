#!/usr/bin/env python3
"""Static audit of a design system and its features against the three-layer UI architecture
(Primitive, Variant, Component) and the clean UI boundary (features consume UI models only).

Checks Jetpack Compose (Kotlin) and SwiftUI sources. It finds what a reviewer would flag by
reading files; it does not replace the manual review in references/rules.md (meaning leaks,
composites reaching internals through odd paths, accessibility, visual parity).

Usage:
    python3 audit.py <repo> [--ds PATH ...] [--features PATH ...] [--format md|json] [--out FILE]

Exit code: 1 when at least one blocker is found (usable in CI), else 0.
"""
import argparse
import json
import re
import sys
from dataclasses import asdict, dataclass
from pathlib import Path

SKIP_DIRS = {"build", ".gradle", ".git", ".idea", "node_modules", ".kotlin", "DerivedData", ".build", ".swiftpm", "Pods"}
SEVERITY_ORDER = {"blocker": 0, "major": 1, "minor": 2}


@dataclass
class Finding:
    rule: str
    severity: str
    file: str
    line: int
    message: str


def walk(root: Path, suffix: str):
    for path in root.rglob(f"*{suffix}"):
        if any(part in SKIP_DIRS for part in path.parts):
            continue
        if "/test/" in str(path) or "/androidTest/" in str(path) or "/screenshotTest/" in str(path) or "Tests/" in str(path):
            continue
        yield path


def rel(root: Path, path: Path) -> str:
    try:
        return str(path.relative_to(root))
    except ValueError:
        return str(path)


def lines_of(path: Path):
    try:
        return path.read_text(encoding="utf-8").splitlines()
    except (UnicodeDecodeError, OSError):
        return []


# ---------------------------------------------------------------- discovery

def find_design_systems(root: Path):
    """Directories that hold a `components` folder inside a design-system-looking module."""
    found = []
    for comp in root.rglob("components"):
        if not comp.is_dir() or any(p in SKIP_DIRS for p in comp.parts):
            continue
        text = str(comp).lower()
        if "designsystem" in text.replace("-", "").replace("_", "") or re.search(r"(^|/)[a-z]*ds(/|$)", text) or "designsystem" in text:
            found.append(comp.parent)
    for comp in root.rglob("Components"):
        if comp.is_dir() and not any(p in SKIP_DIRS for p in comp.parts) and "designsystem" in str(comp).lower():
            found.append(comp.parent)
    unique = []
    for path in sorted(set(found), key=lambda p: len(str(p))):
        if not any(str(path).startswith(str(u) + "/") for u in unique):
            unique.append(path)
    return unique


def find_features(root: Path, design_systems):
    ds = [str(d) for d in design_systems]
    features = []
    for name in ("ui", "presentation", "feature", "features", "Screens", "Features"):
        for path in root.rglob(name):
            if path.is_dir() and not any(p in SKIP_DIRS for p in path.parts) and not any(str(path).startswith(d) for d in ds) \
                    and (any(path.rglob("*.kt")) or any(path.rglob("*.swift"))):
                if "/core/presentation" in str(path) or "commonMain" in str(path) or re.search(r"/(test|androidTest|screenshotTest)/", str(path)):
                    continue
                features.append(path)
    # On a case-insensitive disk, rglob("features") also matches Features/: keep the name as it is on disk.
    features = [p for p in features if p.name in {c.name for c in p.parent.iterdir()}]
    unique = []
    for path in sorted(set(features), key=lambda p: len(str(p))):
        if not any(str(path).startswith(str(u) + "/") for u in unique):
            unique.append(path)
    return unique


def module_root(path: Path):
    for parent in [path, *path.parents]:
        if (parent / "build.gradle.kts").exists() or (parent / "build.gradle").exists() or (parent / "Package.swift").exists():
            return parent
    return None


# ---------------------------------------------------------------- Compose checks

RAW_DP = re.compile(r"(?<![\w.])(\d+(?:\.\d+)?)\.dp\b")
RAW_SP = re.compile(r"(?<![\w.])(\d+(?:\.\d+)?)\.sp\b")
RAW_COLOR = re.compile(r"\bColor\(0x[0-9A-Fa-f]{6,8}\)|\bColor\(\s*red\s*=|\bColor\.(Red|Blue|Green|Black|White|Gray|Yellow|Cyan|Magenta|DarkGray|LightGray)\b")
COMPOSABLE_FUN = re.compile(r"^\s*(public\s+|internal\s+|private\s+)?fun\s+(<[^>]+>\s+)?([A-Z]\w*)\s*\(")
LAMBDA_TYPE = re.compile(r":\s*\(?\s*(@Composable\s*)?\([^)]*\)\s*->")
STYLE_TYPES = re.compile(r":\s*(Dp|Color|TextStyle|FontWeight|Shape|TextUnit|PaddingValues)\??\s*[,=)]")
TEXT_LITERAL = re.compile(r"\bText\(\s*\"[^\"]*[A-Za-zÀ-ÿ]{3,}[^\"]*\"")


def preceding_annotations(lines, index):
    annotations = []
    j = index - 1
    while j >= 0 and (lines[j].strip().startswith("@") or lines[j].strip() == ""):
        annotations.append(lines[j].strip())
        j -= 1
    return annotations


def parameters(text):
    """The parameter list of the first declaration in text, up to its balanced closing parenthesis."""
    start = text.find("(")
    depth = 0
    for j in range(start, len(text)):
        depth += {"(": 1, ")": -1}.get(text[j], 0)
        if depth == 0:
            return text[start:j + 1]
    return text[start:]


def top_level_parameters(signature):
    """(name, type) pairs of a parameter list, ignoring the parameters of lambda types inside it."""
    params, depth, current = [], 0, ""
    for ch in signature[1:-1]:
        depth += {"(": 1, "<": 1, "{": 1, ")": -1, ">": -1, "}": -1}.get(ch, 0) if ch != ">" or not current.endswith("-") else 0
        if ch == "," and depth == 0:
            params.append(current)
            current = ""
        else:
            current += ch
    params.append(current)
    pairs = []
    for param in params:
        name, _, type_ = param.partition(":")
        name = name.split()[-1] if name.split() else ""
        pairs.append((name, type_.split("=")[0].strip()))
    return pairs


def audit_compose_ds(root: Path, ds: Path, findings):
    module = module_root(ds)
    if module:
        build = module / "build.gradle.kts"
        if not build.exists():
            build = module / "build.gradle"
        if build.exists() and "explicitApi" not in build.read_text():
            findings.append(Finding("DS-02", "major", rel(root, build), 1,
                                    "explicitApi() is not enabled: the public surface is not enforced by the compiler."))

    components = ds / "components"
    # Dark theme covered by screenshot tests (Paparazzi, Compose Preview Screenshot Testing, Roborazzi) counts as a dark preview.
    tests = [t for d in ("src/test", "src/screenshotTest") if module for t in (module / d).rglob("*.kt")]
    dark_snapshots = any(re.search(r"dark|night|UI_MODE_NIGHT", t.read_text(), re.IGNORECASE) for t in tests)
    # Flat files at the design system root that hold several public composables: not in components/<name>/.
    for path in ds.glob("*.kt"):
        lines = lines_of(path)
        publics = [i for i, l in enumerate(lines) if COMPOSABLE_FUN.match(l) and not l.strip().startswith(("private", "internal"))
                   and "@Composable" in " ".join(preceding_annotations(lines, i)) and "@Preview" not in " ".join(preceding_annotations(lines, i))]
        if len(publics) > 0:
            findings.append(Finding("DS-01", "major", rel(root, path), publics[0] + 1,
                                    f"{len(publics)} public composable(s) outside components/<name>/: move each into its component folder (entry, models, variants, primitives)."))

    if not components.exists():
        findings.append(Finding("DS-01", "blocker", rel(root, ds), 1, "No components/ folder: the three layers have no structure to live in."))
        return

    for comp in sorted(p for p in components.iterdir() if p.is_dir()):
        kts = [p for p in comp.glob("*.kt")]
        # The entry is named after its folder (LrcEmptyState.kt in emptystate/); state holders and defaults are not entries.
        entry = [p for p in kts if p.name.lower().endswith(comp.name.lower() + ".kt")] or \
                [p for p in kts if not p.name.endswith(("State.kt", "Defaults.kt"))]
        if not entry:
            findings.append(Finding("DS-05", "major", rel(root, comp), 1, "No entry file at the component root (Prefix + Name, the single public API)."))
        if not (comp / "models").exists():
            findings.append(Finding("DS-06", "major", rel(root, comp), 1, "No models/ folder: the component is driven by raw parameters instead of a sealed UI model."))
        if not (comp / "variants").exists():
            findings.append(Finding("DS-04", "minor", rel(root, comp), 1, "No variants/ folder: every component has at least a default variant."))

        for path in comp.rglob("*.kt"):
            lines = lines_of(path)
            layer = "variants" if "/variants/" in str(path) else "primitives" if "/primitives/" in str(path) else "models" if "/models/" in str(path) else "entry"
            in_preview = False
            for i, line in enumerate(lines):
                stripped = line.strip()
                annotations = preceding_annotations(lines, i)
                if COMPOSABLE_FUN.match(line) and "@Composable" in " ".join(annotations):
                    in_preview = "@Preview" in " ".join(annotations)
                    visibility = "private" if stripped.startswith("private") else "internal" if stripped.startswith("internal") else "public"
                    if layer in ("variants", "primitives") and visibility == "public":
                        findings.append(Finding("DS-02", "blocker", rel(root, path), i + 1,
                                                f"{layer[:-1].capitalize()} composable is public: variants and primitives must be internal."))
                if layer == "models":
                    if LAMBDA_TYPE.search(line) and ("val " in line or "var " in line):
                        findings.append(Finding("DS-06", "blocker", rel(root, path), i + 1, "UI model holds a lambda: signals are component parameters, never model fields."))
                    if STYLE_TYPES.search(line) and ("val " in line or "var " in line):
                        findings.append(Finding("DS-06", "major", rel(root, path), i + 1, "UI model holds a raw styling value (Dp, Color, TextStyle…): use a semantic enum the variant resolves."))
                if layer != "models" and not in_preview and not stripped.startswith("//") and not stripped.startswith("*"):
                    for regex, what in ((RAW_DP, "dp"), (RAW_SP, "sp"), (RAW_COLOR, "color")):
                        match = regex.search(line)
                        if match and not (what == "dp" and match.group(0) in ("0.dp",)):
                            findings.append(Finding("DS-10", "major", rel(root, path), i + 1, f"Raw {what} value `{match.group(0)}` in a component: take it from the tokens."))
                    if layer in ("variants", "primitives") and TEXT_LITERAL.search(line):
                        findings.append(Finding("DS-09", "major", rel(root, path), i + 1, "Copy written inside the design system: text comes from the UI model."))
                if stripped.startswith("import ") and (".variants." in stripped or ".primitives." in stripped):
                    imported = stripped.split(".components.")[-1].split(".")[0] if ".components." in stripped else None
                    if imported and imported != comp.name:
                        findings.append(Finding("DS-08", "blocker", rel(root, path), i + 1,
                                                f"Imports the internals of `{imported}`: composites use other components through their public entry only."))

        for path in entry:
            text = path.read_text()
            lines = text.splitlines()
            publics = [i for i, l in enumerate(lines) if COMPOSABLE_FUN.match(l) and not l.strip().startswith(("private", "internal"))
                       and "@Composable" in " ".join(preceding_annotations(lines, i)) and "@Preview" not in " ".join(preceding_annotations(lines, i))]
            for i in publics:
                signature = parameters(" ".join(lines[i:i + 20]))
                for name, type_ in top_level_parameters(signature):
                    if "->" in type_ and not type_.startswith("@Composable") and not name.startswith("on"):
                        findings.append(Finding("DS-07", "minor", rel(root, path), i + 1, f"Callback `{name}` is not named on + action."))
            if "@Preview" not in text:
                findings.append(Finding("DS-12", "minor", rel(root, path), 1, "No @Preview for the component."))
            elif "dark" not in text.lower() and not dark_snapshots:
                findings.append(Finding("DS-12", "minor", rel(root, path), 1, "No dark-theme preview."))


DOMAIN_IMPORT = re.compile(r"^import\s+[\w.]*\.(domain|data)\.[\w.]+|^import\s+[\w.]*\w+(DomainModel|Dto|Entity)\b")
MATERIAL_IMPORT = re.compile(r"^import\s+androidx\.compose\.material3\.")


def audit_compose_features(root: Path, feature: Path, ds_packages, findings):
    for path in walk(feature, ".kt"):
        lines = lines_of(path)
        text = "\n".join(lines)
        for i, line in enumerate(lines):
            stripped = line.strip()
            if DOMAIN_IMPORT.match(stripped):
                findings.append(Finding("FT-01", "blocker", rel(root, path), i + 1, "The UI imports a domain/data model: it must consume UI models only (map at the boundary)."))
            if stripped.startswith("import ") and any(pkg in stripped for pkg in ds_packages) and (".variants." in stripped or ".primitives." in stripped):
                findings.append(Finding("FT-04", "blocker", rel(root, path), i + 1, "A screen imports design-system internals (variant or primitive)."))
            if ds_packages and MATERIAL_IMPORT.match(stripped):
                findings.append(Finding("FT-04", "minor", rel(root, path), i + 1, "Material imported in a feature while a design system exists: use its component, or add the missing one."))
        if re.search(r"collectAsState(WithLifecycle)?\(", text) and re.search(r"\bfun\s+\w+Screen\s*\(", text) and not re.search(r"\bfun\s+\w+Route\s*\(", text):
            findings.append(Finding("FT-02", "major", rel(root, path), 1, "The screen collects state itself: split it into an XRoute (collects, no UI) and a stateless XScreen (previewable)."))


# ---------------------------------------------------------------- SwiftUI checks

SWIFT_PUBLIC = re.compile(r"^\s*public\s+(struct|enum|class|func|var|let|extension)\b")
SWIFT_CLOSURE_PROP = re.compile(r"^\s*(public\s+)?(let|var)\s+\w+\s*:\s*(@escaping\s+)?\(?\([^)]*\)\s*(async\s+)?->")
SWIFT_RAW = re.compile(r"\.padding\(\s*\d+|\.frame\((width|height|minHeight|maxWidth):\s*\d+|Color\(\s*red:|Color\(\.sRGB|\.font\(\.system\(size:\s*\d+|cornerRadius:\s*\d+")


def audit_swift_ds(root: Path, ds: Path, findings):
    components = ds / "Components"
    if not components.exists():
        components = ds / "components"
    if not components.exists():
        findings.append(Finding("DS-01", "blocker", rel(root, ds), 1, "No Components/ folder in the SwiftUI design system."))
        return
    for path in ds.glob("*.swift"):
        text = path.read_text()
        if "Generated" in text[:200]:
            continue
        if len(re.findall(r"public struct \w+\s*:\s*View", text)) > 1:
            findings.append(Finding("DS-01", "major", rel(root, path), 1, "Several public views in one flat file: one folder per component."))
    for comp in sorted(p for p in components.iterdir() if p.is_dir()):
        if not (comp / "Models").exists() and not (comp / "models").exists():
            findings.append(Finding("DS-06", "major", rel(root, comp), 1, "No Models/ folder: the view is driven by raw parameters instead of a UI model."))
        for path in comp.rglob("*.swift"):
            layer = "Variants" if "/Variants/" in str(path) else "Primitives" if "/Primitives/" in str(path) else "Models" if "/Models/" in str(path) else "entry"
            lines = lines_of(path)
            in_preview = False
            for i, line in enumerate(lines):
                stripped = line.strip()
                if stripped.startswith("#Preview"):
                    in_preview = True
                if layer in ("Variants", "Primitives") and SWIFT_PUBLIC.match(line):
                    findings.append(Finding("DS-02", "blocker", rel(root, path), i + 1, f"{layer[:-1]} declaration is public: keep it internal."))
                if layer == "Models" and SWIFT_CLOSURE_PROP.match(line):
                    findings.append(Finding("DS-06", "blocker", rel(root, path), i + 1, "UI model stores a closure: signals are view parameters."))
                if layer != "Models" and not in_preview and SWIFT_RAW.search(line):
                    findings.append(Finding("DS-10", "major", rel(root, path), i + 1, f"Raw value `{SWIFT_RAW.search(line).group(0)}`: take it from the tokens."))


def audit_swift_features(root: Path, feature: Path, findings):
    for path in walk(feature, ".swift"):
        for i, line in enumerate(lines_of(path)):
            if re.search(r"\b\w+(DomainModel|Dto|Entity)\b", line):
                findings.append(Finding("FT-01", "blocker", rel(root, path), i + 1, "The UI uses a domain/data type: consume UI models only."))


# ---------------------------------------------------------------- report

def render_markdown(root: Path, design_systems, features, findings):
    counts = {s: sum(1 for f in findings if f.severity == s) for s in SEVERITY_ORDER}
    out = ["# Three-layer UI audit", "", f"Repository: `{root}`", ""]
    out.append("Design systems: " + (", ".join(f"`{rel(root, d)}`" for d in design_systems) or "none found"))
    out.append("Feature roots: " + (", ".join(f"`{rel(root, f)}`" for f in features) or "none found"))
    out += ["", f"**{counts['blocker']} blocker(s), {counts['major']} major, {counts['minor']} minor** (static checks only; complete with the manual review).", ""]
    by_rule = {}
    for f in sorted(findings, key=lambda f: (SEVERITY_ORDER[f.severity], f.rule, f.file, f.line)):
        by_rule.setdefault((f.rule, f.severity), []).append(f)
    for (rule, severity), items in by_rule.items():
        out += [f"## {rule} · {severity} · {len(items)}", "", "| File | Line | Finding |", "|---|---|---|"]
        out += [f"| `{f.file}` | {f.line} | {f.message} |" for f in items[:60]]
        if len(items) > 60:
            out.append(f"| … | | {len(items) - 60} more |")
        out.append("")
    return "\n".join(out)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("repo", type=Path)
    parser.add_argument("--ds", type=Path, action="append", help="design system source root (holds components/); repeatable")
    parser.add_argument("--features", type=Path, action="append", help="feature UI root; repeatable")
    parser.add_argument("--format", choices=("md", "json"), default="md")
    parser.add_argument("--out", type=Path)
    args = parser.parse_args()

    root = args.repo.resolve()
    design_systems = [p.resolve() for p in args.ds] if args.ds else find_design_systems(root)
    features = [p.resolve() for p in args.features] if args.features else find_features(root, design_systems)
    findings = []
    ds_packages = set()
    for ds in design_systems:
        if any(ds.rglob("*.kt")):
            audit_compose_ds(root, ds, findings)
            for kt in ds.rglob("*.kt"):
                for line in lines_of(kt)[:3]:
                    if line.startswith("package "):
                        ds_packages.add(line.split()[1].split(".components")[0].split(".foundation")[0])
                break
        if any(ds.rglob("*.swift")):
            audit_swift_ds(root, ds, findings)
    for feature in features:
        audit_compose_features(root, feature, ds_packages, findings)
        audit_swift_features(root, feature, findings)

    if args.format == "json":
        report = json.dumps({"designSystems": [rel(root, d) for d in design_systems], "features": [rel(root, f) for f in features],
                             "findings": [asdict(f) for f in findings]}, indent=2, ensure_ascii=False)
    else:
        report = render_markdown(root, design_systems, features, findings)
    if args.out:
        args.out.write_text(report, encoding="utf-8")
    else:
        print(report)
    sys.exit(1 if any(f.severity == "blocker" for f in findings) else 0)


if __name__ == "__main__":
    main()
