package com.electron.designsystem.components.tag.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.tag.models.TagUiModel
import com.electron.designsystem.components.tag.primitives.TagPrimitive

@Composable
internal fun TagText(
    uiModel: TagUiModel.Text,
    modifier: Modifier = Modifier
) {
    val colors = resolveTagColors(uiModel.style, uiModel.tone)
    val metrics = uiModel.size.metrics()
    TagPrimitive(
        text = uiModel.text,
        leadingIcon = uiModel.leadingIcon,
        backgroundColor = colors.background,
        contentColor = colors.content,
        borderColor = colors.border,
        horizontalPadding = metrics.horizontalPadding,
        verticalPadding = metrics.verticalPadding,
        iconSize = metrics.iconSize,
        textStyle = metrics.textStyle,
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
