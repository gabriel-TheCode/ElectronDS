package com.electron.designsystem.components.badge.primitives

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
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/** Pill-shaped marker. Renders a dot when [text] is null. */
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
            Text(text = text, style = textStyle, color = contentColor, maxLines = 1)
        }
    }
}
