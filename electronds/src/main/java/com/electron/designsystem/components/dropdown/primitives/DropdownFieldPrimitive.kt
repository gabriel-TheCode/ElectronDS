package com.electron.designsystem.components.dropdown.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

internal data class DropdownColors(
    val container: Color,
    val border: Color,
    val activeBorder: Color,
    val errorBorder: Color,
    val label: Color,
    val value: Color,
    val placeholder: Color,
    val chevron: Color,
    val supporting: Color,
    val error: Color
)

/**
 * Select field with the exact silhouette of ElectronInputField (label
 * above, 48dp field, same radius and border states), so a form mixing text
 * fields and dropdowns reads as one family. "Open" uses the focus
 * treatment (2dp brand border) and the chevron turns to point at the menu.
 * [menu] is placed inside the field box, so the menu anchors to the field
 * itself rather than to the label or helper text.
 */
@Composable
internal fun DropdownFieldPrimitive(
    valueText: String?,
    label: String?,
    placeholder: String?,
    supportingText: String?,
    isError: Boolean,
    isEnabled: Boolean,
    isExpanded: Boolean,
    colors: DropdownColors,
    labelStyle: TextStyle,
    valueStyle: TextStyle,
    supportingStyle: TextStyle,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    menu: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isActive = isEnabled && (isExpanded || isFocused)
    val borderColor by animateColorAsState(
        targetValue = when {
            !isEnabled -> Color.Transparent
            isError -> colors.errorBorder
            isActive -> colors.activeBorder
            else -> colors.border
        },
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "dropdownBorder"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isActive) ElectronDimens.borderWidthFocus else ElectronDimens.borderWidth,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "dropdownBorderWidth"
    )
    val chevronRotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(ElectronMotion.standard, easing = ElectronMotion.easeStandard),
        label = "dropdownChevron"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { if (isError && supportingText != null) error(supportingText) }
            .testTag(testTag)
    ) {
        if (label != null) {
            Text(text = label, style = labelStyle, color = colors.label)
            Spacer(modifier = Modifier.height(ElectronSpacing.sm))
        }
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.sm),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = ElectronDimens.fieldHeight)
                    .clip(ElectronShapes.field)
                    .background(colors.container)
                    .border(borderWidth, borderColor, ElectronShapes.field)
                    .clickable(
                        enabled = isEnabled,
                        role = Role.DropdownList,
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onClick
                    )
                    .padding(start = ElectronSpacing.lg, end = ElectronSpacing.md)
            ) {
                Text(
                    text = valueText ?: placeholder.orEmpty(),
                    style = valueStyle,
                    color = if (valueText != null) colors.value else colors.placeholder,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null,
                    tint = colors.chevron,
                    modifier = Modifier
                        .size(ElectronDimens.iconMd)
                        .rotate(chevronRotation)
                )
            }
            menu()
        }
        if (supportingText != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(ElectronSpacing.xs),
                modifier = Modifier.padding(top = ElectronSpacing.sm)
            ) {
                if (isError) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        tint = colors.error,
                        modifier = Modifier.size(ElectronDimens.iconSm)
                    )
                }
                Text(
                    text = supportingText,
                    style = supportingStyle,
                    color = if (isError) colors.error else colors.supporting
                )
            }
        }
    }
}
