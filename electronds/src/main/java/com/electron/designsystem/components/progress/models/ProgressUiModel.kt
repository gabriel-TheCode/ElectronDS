package com.electron.designsystem.components.progress.models

import androidx.compose.runtime.Immutable

/** Semantic tone of the progress fill. */
public enum class ProgressTone { Brand, Accent, Success, Warning, Error }

/** Token-based sizes for the circular indicator. */
public enum class ProgressSize { Sm, Md, Lg }

/**
 * Progress UI models. A null [Linear.progress] / [Circular.progress]
 * renders an indeterminate indicator; otherwise the value is clamped to
 * the 0f..1f range.
 */
public sealed class ProgressUiModel {

    @Immutable
    public data class Linear(
        val progress: Float? = null,
        val tone: ProgressTone = ProgressTone.Brand,
        val contentDescription: String? = null,
        val testTag: String = "electron_progress_linear"
    ) : ProgressUiModel()

    @Immutable
    public data class Circular(
        val progress: Float? = null,
        val size: ProgressSize = ProgressSize.Md,
        val tone: ProgressTone = ProgressTone.Brand,
        val contentDescription: String? = null,
        val testTag: String = "electron_progress_circular"
    ) : ProgressUiModel()
}
