#!/usr/bin/env bash
# Copies the README preview images from the app's Paparazzi golden images,
# so the README always shows the current UI.
#
#   scripts/update-readme-screenshots.sh           copy the images
#   scripts/update-readme-screenshots.sh --check   fail if they are out of date
set -euo pipefail

cd "$(dirname "$0")/.."
GOLDENS="app/src/test/snapshots/images/com.electron.catalog.screenshot_AppScreenshotTest"
TARGET="docs/screenshots"

# README image  <-  golden image
PAIRS=(
  "home_light.png:home[Light-Phone].png"
  "home_dark.png:home[Dark-Phone].png"
  "dashboard_light.png:demoDashboard[Light-Phone].png"
  "dashboard_dark.png:demoDashboard[Dark-Phone].png"
)

mkdir -p "$TARGET"
stale=0
for pair in "${PAIRS[@]}"; do
  image="${pair%%:*}"
  golden="${GOLDENS}_${pair#*:}"
  if [ ! -f "$golden" ]; then
    echo "Missing golden image: $golden" >&2
    exit 1
  fi
  if [ "${1:-}" = "--check" ]; then
    if ! cmp -s "$golden" "$TARGET/$image"; then
      echo "Out of date: $TARGET/$image" >&2
      stale=1
    fi
  else
    cp "$golden" "$TARGET/$image"
  fi
done

if [ "$stale" -ne 0 ]; then
  echo "Run scripts/update-readme-screenshots.sh and commit the result." >&2
  exit 1
fi
