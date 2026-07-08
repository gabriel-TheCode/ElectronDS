package com.electron.designsystem.components.card.models

import androidx.compose.runtime.Immutable

/** Severity of a status card; the design system maps it to colors and icon. */
public enum class CardSeverity { Info, Success, Warning, Error }

/**
 * Card UI models.
 *
 * Refactor notes versus the legacy cards package:
 * - The legacy content cards were flat slot composables with hardcoded
 *   internal spacing and an embedded tertiary button. They are replaced by
 *   [Default], which describes the header and optional action as data while
 *   the body remains a content slot at the component level.
 * - The legacy warning card hardcoded the warning color; it becomes [Status]
 *   with a semantic [CardSeverity].
 * - The legacy error card mixed feature content (illustration and copy layout)
 *   into the design system; that composition now belongs to feature code,
 *   built from ElectronCard plus ElectronButton.
 */
public sealed class CardUiModel {

    @Immutable
    public data class Default(
        val title: String? = null,
        val actionLabel: String? = null,
        val testTag: String = "electron_card"
    ) : CardUiModel()

    @Immutable
    public data class Status(
        val message: String,
        val severity: CardSeverity = CardSeverity.Info,
        val testTag: String = "electron_card_status"
    ) : CardUiModel()
}
