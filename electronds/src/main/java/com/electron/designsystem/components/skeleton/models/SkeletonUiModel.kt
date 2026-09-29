package com.electron.designsystem.components.skeleton.models

import androidx.compose.runtime.Immutable
import com.electron.designsystem.components.avatar.models.AvatarSize

/**
 * Skeleton (loading placeholder) UI models. Each placeholder mirrors the
 * geometry of the content it stands for, so nothing moves when the data
 * arrives. [contentDescription] is announced by screen readers (for
 * example "Loading sessions"); leave it null on all but one placeholder
 * of a group to avoid repeated announcements.
 */
public sealed class SkeletonUiModel {

    /** Paragraph of [lines] body lines; the last one is shorter. */
    @Immutable
    public data class Text(
        val lines: Int = 3,
        val contentDescription: String? = null,
        val testTag: String = "electron_skeleton_text"
    ) : SkeletonUiModel()

    /** Stand-in for an ElectronAvatar of the same [size]. */
    @Immutable
    public data class Circle(
        val size: AvatarSize = AvatarSize.Md,
        val contentDescription: String? = null,
        val testTag: String = "electron_skeleton_circle"
    ) : SkeletonUiModel()

    /** Full-width media block with the given width / height [aspectRatio]. */
    @Immutable
    public data class Block(
        val aspectRatio: Float = 16f / 9f,
        val contentDescription: String? = null,
        val testTag: String = "electron_skeleton_block"
    ) : SkeletonUiModel()

    /** Stand-in for an ElectronListItem row. */
    @Immutable
    public data class ListItem(
        val hasLeading: Boolean = true,
        val hasSubtitle: Boolean = true,
        val contentDescription: String? = null,
        val testTag: String = "electron_skeleton_list_item"
    ) : SkeletonUiModel()

    /** Stand-in for an ElectronMetricCard. */
    @Immutable
    public data class MetricCard(
        val contentDescription: String? = null,
        val testTag: String = "electron_skeleton_metric_card"
    ) : SkeletonUiModel()
}
