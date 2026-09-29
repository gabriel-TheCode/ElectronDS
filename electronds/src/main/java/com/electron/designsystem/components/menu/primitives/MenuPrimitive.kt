package com.electron.designsystem.components.menu.primitives

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

internal data class MenuItemColors(
    val content: Color,
    val icon: Color,
    val trailing: Color,
    val check: Color
)

/**
 * Below the anchor, start-aligned; flips above when the menu would
 * overflow the bottom of the window, and is clamped horizontally so it
 * never leaves the screen.
 */
private class AnchoredPositionProvider(private val gapPx: Int) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val preferredX = if (layoutDirection == LayoutDirection.Ltr) {
            anchorBounds.left
        } else {
            anchorBounds.right - popupContentSize.width
        }
        val x = preferredX.coerceIn(0, (windowSize.width - popupContentSize.width).coerceAtLeast(0))
        val below = anchorBounds.bottom + gapPx
        val y = if (below + popupContentSize.height <= windowSize.height) {
            below
        } else {
            (anchorBounds.top - gapPx - popupContentSize.height).coerceAtLeast(0)
        }
        return IntOffset(x, y)
    }
}

/**
 * Floating panel: raised surface, popover radius, hairline border and a
 * floating shadow (the one place a shadow earns its keep: it tells the
 * user the menu sits above the page). Scrolls when taller than the window.
 */
@Composable
internal fun MenuPanel(
    containerColor: Color,
    borderColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .widthIn(min = ElectronDimens.menuMinWidth, max = ElectronDimens.menuMaxWidth)
            .width(IntrinsicSize.Max)
            .shadow(ElectronElevation.floating, ElectronShapes.popover)
            .background(containerColor, ElectronShapes.popover)
            .border(ElectronDimens.borderWidth, borderColor, ElectronShapes.popover)
            .verticalScroll(rememberScrollState())
            .padding(vertical = ElectronSpacing.xs)
            .testTag(testTag),
        content = content
    )
}

/** 48dp entry: leading icon, label, trailing text or selection check. */
@Composable
internal fun MenuItemPrimitive(
    label: String,
    labelStyle: TextStyle,
    trailingStyle: TextStyle,
    colors: MenuItemColors,
    isEnabled: Boolean,
    isSelected: Boolean,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingText: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = ElectronDimens.menuItemHeight)
            .clickable(enabled = isEnabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = ElectronSpacing.lg)
            .testTag(testTag)
    ) {
        if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = colors.icon,
                modifier = Modifier.size(ElectronDimens.iconMd)
            )
        }
        Text(
            text = label,
            style = labelStyle,
            color = colors.content,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        if (trailingText != null) {
            Text(text = trailingText, style = trailingStyle, color = colors.trailing, maxLines = 1)
        }
        if (isSelected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = colors.check,
                modifier = Modifier.size(ElectronDimens.iconMd)
            )
        }
    }
}

/**
 * Popup host. Stays composed while the exit animation runs, then leaves
 * composition. Enters with a short fade + 4% scale from the top-start
 * corner (it unfolds from its anchor) and exits faster than it enters.
 * The popup is focusable so the D-pad and the keyboard move into it.
 */
@Composable
internal fun MenuPopup(
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val transitionState = remember { MutableTransitionState(false) }
    transitionState.targetState = isExpanded
    if (!transitionState.currentState && !transitionState.targetState) return

    val gapPx = with(LocalDensity.current) { ElectronSpacing.xs.roundToPx() }
    Popup(
        popupPositionProvider = remember(gapPx) { AnchoredPositionProvider(gapPx) },
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true)
    ) {
        AnimatedVisibility(
            visibleState = transitionState,
            enter = fadeIn(tween(ElectronMotion.quick, easing = ElectronMotion.easeEnter)) +
                scaleIn(
                    animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeEnter),
                    initialScale = 0.96f,
                    transformOrigin = TransformOrigin(0f, 0f)
                ),
            exit = fadeOut(tween(ElectronMotion.instant, easing = ElectronMotion.easeExit)) +
                scaleOut(
                    animationSpec = tween(ElectronMotion.instant, easing = ElectronMotion.easeExit),
                    targetScale = 0.96f,
                    transformOrigin = TransformOrigin(0f, 0f)
                )
        ) {
            content()
        }
    }
}
