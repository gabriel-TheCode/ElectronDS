package com.electron.designsystem.components.checkbox.primitives

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.ElectronInteraction
import com.electron.designsystem.utils.highlightOutset

/**
 * Checkbox row: the whole row toggles, so the label is part of the touch
 * target and screen readers announce a single checkbox. The highlight
 * overflows [ElectronInteraction.HighlightOutset] on both sides, so the
 * box stays aligned with the rest of the screen.
 */
@Composable
internal fun CheckboxPrimitive(
    isChecked: Boolean,
    isEnabled: Boolean,
    colors: CheckboxColors,
    label: String?,
    labelStyle: TextStyle,
    labelColor: Color,
    testTag: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .highlightOutset()
            .defaultMinSize(minHeight = ElectronDimens.minTouchTarget)
            .clip(ElectronShapes.control)
            .toggleable(
                value = isChecked,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = isEnabled,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .padding(horizontal = ElectronInteraction.HighlightOutset)
            .testTag(testTag)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = null,
            enabled = isEnabled,
            colors = colors
        )
        if (label != null) {
            Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            Text(text = label, style = labelStyle, color = labelColor)
        }
    }
}
