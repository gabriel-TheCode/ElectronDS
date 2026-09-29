package com.electron.designsystem.components.sheetheader.primitives

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.utils.focusRing

/**
 * Sheet header block: close affordance, centered title, optional trailing
 * text action. Uses IconButton so the touch target meets the minimum
 * 48dp requirement through Material semantics instead of a hand-built Box.
 */
@Composable
internal fun SheetHeaderPrimitive(
    title: String,
    resetLabel: String?,
    closeContentDescription: String?,
    testTag: String,
    onCloseClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = ElectronTheme.colors
    val closeInteraction = remember { MutableInteractionSource() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(ElectronDimens.sheetHeaderHeight)
            .testTag(testTag)
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
            IconButton(
                onClick = onCloseClick,
                interactionSource = closeInteraction,
                modifier = Modifier
                    .size(ElectronDimens.minTouchTarget)
                    .focusRing(closeInteraction, ElectronShapes.pill)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = closeContentDescription,
                    tint = colors.content.primary,
                    modifier = Modifier.size(ElectronDimens.iconMd)
                )
            }
        }

        Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.Center) {
            Text(
                text = title,
                style = ElectronTheme.typography.titleMedium,
                color = colors.content.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
            if (resetLabel != null) {
                TextButton(onClick = onResetClick) {
                    Text(
                        text = resetLabel,
                        style = ElectronTheme.typography.labelLarge,
                        color = colors.brand.primary
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(ElectronDimens.minTouchTarget))
            }
        }
    }
}
