package com.electron.designsystem.components.dialog.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.Dialog
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Modal surface: optional visual, title, message and an end-aligned
 * actions row. [onDismissRequest] fires on back press or scrim tap.
 *
 * Rhythm: title and message are one statement (sm apart); the actions are
 * the answer and sit clearly apart (xl), so the eye reads, then decides.
 */
@Composable
internal fun DialogPrimitive(
    title: String,
    titleStyle: TextStyle,
    titleColor: Color,
    message: String,
    messageStyle: TextStyle,
    messageColor: Color,
    containerColor: Color,
    testTag: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    visual: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .shadow(ElectronElevation.overlay, ElectronShapes.dialog)
                .background(containerColor, ElectronShapes.dialog)
                .padding(ElectronSpacing.xl)
                .testTag(testTag)
        ) {
            if (visual != null) {
                visual()
                Spacer(modifier = Modifier.height(ElectronSpacing.lg))
            }
            Text(text = title, style = titleStyle, color = titleColor)
            Spacer(modifier = Modifier.height(ElectronSpacing.sm))
            Text(text = message, style = messageStyle, color = messageColor)
            Spacer(modifier = Modifier.height(ElectronSpacing.xl))
            Row(
                horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                content = actions
            )
        }
    }
}
