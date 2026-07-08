package com.electron.designsystem.components.tag.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

public enum class TagSize { Sm, Md }

/** Visual treatment of the tag container. */
public enum class TagStyle { Filled, Tinted, Outlined }

/** Semantic tone: the design system maps it to colors per theme. */
public enum class TagTone { Brand, Accent, Neutral, Success, Warning, Error, Info }

/**
 * Tag UI models.
 *
 * The legacy models exposed raw `radius: Dp`, `allPadding: Dp?` and
 * `fontWeight: FontWeight` fields, letting every feature restyle tags
 * arbitrarily. Those knobs are gone: shape, padding and weight are owned
 * by the design system and driven by [TagSize] and [TagStyle].
 */
public sealed class TagUiModel {

    @Immutable
    public data class Text(
        val text: String,
        val size: TagSize = TagSize.Md,
        val style: TagStyle = TagStyle.Tinted,
        val tone: TagTone = TagTone.Neutral,
        val leadingIcon: ImageVector? = null,
        val testTag: String = "electron_tag_text"
    ) : TagUiModel()

    @Immutable
    public data class Icon(
        val icon: ImageVector,
        val contentDescription: String?,
        val size: TagSize = TagSize.Md,
        val style: TagStyle = TagStyle.Tinted,
        val tone: TagTone = TagTone.Neutral,
        val testTag: String = "electron_tag_icon"
    ) : TagUiModel()
}
