package com.electron.designsystem.components.tooltip.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Plain tooltip: inverted colors (dark on light theme, light on dark) so it
 * reads as a transient overlay, small label radius, no shadow needed.
 */
@Composable
internal fun PlainTooltipSurface(
    text: String,
    containerColor: Color,
    contentColor: Color,
    textStyle: TextStyle,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .widthIn(max = ElectronDimens.tooltipMaxWidth)
            .background(containerColor, ElectronShapes.tag)
            .padding(horizontal = ElectronSpacing.sm, vertical = ElectronSpacing.xs)
            .testTag(testTag)
    ) {
        Text(text = text, style = textStyle, color = contentColor)
    }
}

/** Rich tooltip: raised popover surface, title, text, optional action slot. */
@Composable
internal fun RichTooltipSurface(
    text: String,
    title: String?,
    containerColor: Color,
    borderColor: Color,
    titleStyle: TextStyle,
    titleColor: Color,
    textStyle: TextStyle,
    textColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .widthIn(max = ElectronDimens.tooltipMaxWidth)
            .shadow(ElectronElevation.floating, ElectronShapes.popover)
            .background(containerColor, ElectronShapes.popover)
            .border(ElectronDimens.borderWidth, borderColor, ElectronShapes.popover)
            .padding(
                start = ElectronSpacing.lg,
                end = ElectronSpacing.lg,
                top = ElectronSpacing.md,
                bottom = if (action != null) ElectronSpacing.xs else ElectronSpacing.md
            )
            .testTag(testTag)
    ) {
        if (title != null) {
            Text(text = title, style = titleStyle, color = titleColor)
            Spacer(modifier = Modifier.height(ElectronSpacing.xs))
        }
        Text(text = text, style = textStyle, color = textColor)
        if (action != null) {
            Spacer(modifier = Modifier.height(ElectronSpacing.xs))
            action()
        }
    }
}

/**
 * Anchors a tooltip to [content] with Material's TooltipBox, which owns
 * positioning and triggers (long-press on touch, hover with a mouse,
 * focus with a keyboard). Only the surfaces are Electron's.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TooltipAnchorPrimitive(
    isRich: Boolean,
    tooltip: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TooltipBox(
        positionProvider = if (isRich) {
            TooltipDefaults.rememberRichTooltipPositionProvider(ElectronSpacing.sm)
        } else {
            TooltipDefaults.rememberPlainTooltipPositionProvider(ElectronSpacing.sm)
        },
        tooltip = { tooltip() },
        state = rememberTooltipState(isPersistent = isRich),
        modifier = modifier,
        content = content
    )
}
