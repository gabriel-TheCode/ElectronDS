package com.electron.designsystem.components.emptystate.primitives

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.electron.designsystem.tokens.ElectronSpacing

/** Centered column: visual slot, title, message, then an actions slot. */
@Composable
internal fun EmptyStatePrimitive(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color,
    message: String?,
    messageStyle: TextStyle,
    messageColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    visual: (@Composable () -> Unit)? = null,
    actions: (@Composable ColumnScope.() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = ElectronSpacing.xl, vertical = ElectronSpacing.xxl)
            .testTag(testTag)
    ) {
        if (visual != null) {
            visual()
            Spacer(modifier = Modifier.height(ElectronSpacing.sm))
        }
        Text(text = title, style = titleStyle, color = titleColor, textAlign = TextAlign.Center)
        if (message != null) {
            Text(text = message, style = messageStyle, color = messageColor, textAlign = TextAlign.Center)
        }
        if (actions != null) {
            Spacer(modifier = Modifier.height(ElectronSpacing.md))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(ElectronSpacing.xs),
                content = actions
            )
        }
    }
}
