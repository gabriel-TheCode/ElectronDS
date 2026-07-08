package com.electron.designsystem.components.fab.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * FAB UI models. Refactored from the legacy flat extended FAB,
 * which hardcoded its typography override and container color inline.
 */
public sealed class FabUiModel {

    @Immutable
    public data class Extended(
        val text: String,
        val icon: ImageVector,
        val isExpanded: Boolean = true,
        val iconContentDescription: String? = null,
        val testTag: String = "electron_fab_extended"
    ) : FabUiModel()

    @Immutable
    public data class Compact(
        val icon: ImageVector,
        val iconContentDescription: String?,
        val testTag: String = "electron_fab_compact"
    ) : FabUiModel()
}
