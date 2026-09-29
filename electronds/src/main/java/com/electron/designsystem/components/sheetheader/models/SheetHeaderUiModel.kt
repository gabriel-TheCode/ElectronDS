package com.electron.designsystem.components.sheetheader.models

import androidx.compose.runtime.Immutable

/**
 * Sheet header UI model.
 *
 * Refactored from the legacy flat sheet header, which hardcoded
 * 48.dp and 20.dp values inline and signalled "no reset button" with an
 * empty string. Here the optional action is a nullable field and every
 * dimension comes from tokens.
 */
public sealed class SheetHeaderUiModel {

    @Immutable
    public data class Default(
        val title: String,
        val resetLabel: String? = null,
        val closeContentDescription: String? = null,
        val testTag: String = "electron_sheet_header"
    ) : SheetHeaderUiModel()
}
