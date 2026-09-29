package com.electron.designsystem.components.badge.primitives

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Pill-shaped marker. Renders a dot when [text] is null.
 * A changing count rolls vertically (up when it grows, down when it
 * shrinks), so a new notification is noticed without a flashing badge.
 */
@Composable
internal fun BadgePrimitive(
    backgroundColor: Color,
    contentColor: Color,
    textStyle: TextStyle,
    testTag: String,
    modifier: Modifier = Modifier,
    text: String? = null,
    contentDescription: String? = null
) {
    val semanticsModifier = if (contentDescription != null) {
        Modifier.semantics { this.contentDescription = contentDescription }
    } else {
        Modifier
    }
    if (text == null) {
        Box(
            modifier = modifier
                .size(ElectronDimens.badgeDot)
                .background(backgroundColor, ElectronShapes.pill)
                .then(semanticsModifier)
                .testTag(testTag)
        )
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .height(ElectronDimens.badgeHeight)
                .defaultMinSize(minWidth = ElectronDimens.badgeHeight)
                .background(backgroundColor, ElectronShapes.pill)
                .padding(horizontal = ElectronSpacing.xs)
                .then(semanticsModifier)
                .testTag(testTag)
        ) {
            AnimatedContent(
                targetState = text,
                transitionSpec = {
                    val grows = (targetState.filter(Char::isDigit).toIntOrNull() ?: 0) >=
                        (initialState.filter(Char::isDigit).toIntOrNull() ?: 0)
                    val direction = if (grows) 1 else -1
                    (slideInVertically(tween(ElectronMotion.quick, easing = ElectronMotion.easeEnter)) { it * direction } +
                        fadeIn(tween(ElectronMotion.quick))) togetherWith
                        (slideOutVertically(tween(ElectronMotion.instant, easing = ElectronMotion.easeExit)) { -it * direction } +
                            fadeOut(tween(ElectronMotion.instant)))
                },
                label = "badgeCount"
            ) { value ->
                Text(text = value, style = textStyle, color = contentColor, maxLines = 1)
            }
        }
    }
}
