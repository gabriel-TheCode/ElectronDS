package com.electron.designsystem.components.bottomsheet.models

import androidx.compose.runtime.Immutable

/**
 * Bottom sheet UI model. Like dialogs, visibility is not part of the model:
 * the screen shows the sheet by composing it and hides it by removing it.
 * The body is a content slot; the frame (handle, header) is described here.
 */
public sealed class BottomSheetUiModel {

    /** Sheet with a drag handle and, when [title] is set, an ElectronSheetHeader. */
    @Immutable
    public data class Default(
        val title: String? = null,
        val resetLabel: String? = null,
        val closeContentDescription: String? = null,
        val testTag: String = "electron_bottom_sheet"
    ) : BottomSheetUiModel()
}
