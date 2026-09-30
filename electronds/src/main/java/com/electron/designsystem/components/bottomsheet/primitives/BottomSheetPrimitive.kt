package com.electron.designsystem.components.bottomsheet.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Visible body of a sheet: drag handle, optional header, content, and a
 * bottom breathing space. Kept separate from the window so it can be
 * rendered on its own (screenshot tests, previews).
 */
@Composable
internal fun BottomSheetSurface(
    handleColor: Color,
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = ElectronSpacing.sm, bottom = ElectronSpacing.xs)
        ) {
            Box(
                modifier = Modifier
                    .size(ElectronDimens.sheetHandleWidth, ElectronDimens.sheetHandleHeight)
                    .background(handleColor, ElectronShapes.pill)
            )
        }
        header?.invoke()
        content()
        Spacer(modifier = Modifier.height(ElectronSpacing.lg))
    }
}

/**
 * Modal window around [BottomSheetSurface]. Material's own drag handle is
 * disabled (the surface draws a quieter one) and tonal elevation is off,
 * so the sheet is the raised surface color, not a tinted one. The sheet
 * width is capped by Material (640dp), which keeps it readable on tablets
 * instead of spanning the whole screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetPrimitive(
    sheetState: SheetState,
    containerColor: Color,
    contentColor: Color,
    scrimColor: Color,
    testTag: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = ElectronShapes.sheet,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = ElectronElevation.flat,
        scrimColor = scrimColor,
        dragHandle = null,
        modifier = modifier.testTag(testTag),
        content = content
    )
}
