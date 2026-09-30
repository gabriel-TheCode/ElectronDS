package com.electron.designsystem.components.radiobutton.primitives

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
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
import com.electron.designsystem.utils.focusRing

/** Radio row: the whole row is selectable, label included. */
@Composable
internal fun RadioButtonPrimitive(
    isSelected: Boolean,
    isEnabled: Boolean,
    colors: RadioButtonColors,
    label: String?,
    labelStyle: TextStyle,
    labelColor: Color,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .defaultMinSize(minHeight = ElectronDimens.minTouchTarget)
            .clip(ElectronShapes.control)
            .focusRing(interactionSource, ElectronShapes.control)
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = isEnabled,
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(end = ElectronSpacing.sm)
            .testTag(testTag)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            enabled = isEnabled,
            colors = colors
        )
        if (label != null) {
            Spacer(modifier = Modifier.width(ElectronSpacing.sm))
            Text(text = label, style = labelStyle, color = labelColor)
        }
    }
}
