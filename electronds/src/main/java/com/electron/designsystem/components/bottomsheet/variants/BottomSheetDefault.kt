package com.electron.designsystem.components.bottomsheet.variants

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.bottomsheet.models.BottomSheetUiModel
import com.electron.designsystem.components.bottomsheet.primitives.BottomSheetPrimitive
import com.electron.designsystem.components.bottomsheet.primitives.BottomSheetSurface
import com.electron.designsystem.components.sheetheader.ElectronSheetHeader
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.foundation.ElectronTheme
import kotlinx.coroutines.launch

/**
 * The header close button slides the sheet out first and emits
 * onDismissRequest once it is hidden, so closing from the button looks the
 * same as swiping down, instead of the sheet vanishing in one frame.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetDefault(
    uiModel: BottomSheetUiModel.Default,
    onDismissRequest: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val c = ElectronTheme.colors
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val hideThenDismiss: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) onDismissRequest()
        }
    }
    BottomSheetPrimitive(
        sheetState = sheetState,
        containerColor = c.background.surfaceRaised,
        contentColor = c.content.primary,
        scrimColor = c.background.scrim,
        testTag = uiModel.testTag,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        BottomSheetDefaultBody(uiModel, hideThenDismiss, onResetClick, content)
    }
}

/** Sheet body shared by the window and by previews and screenshot tests. */
@Composable
internal fun BottomSheetDefaultBody(
    uiModel: BottomSheetUiModel.Default,
    onCloseClick: () -> Unit,
    onResetClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val title = uiModel.title
    BottomSheetSurface(
        handleColor = ElectronTheme.colors.border.default,
        header = if (title != null) {
            {
                ElectronSheetHeader(
                    uiModel = SheetHeaderUiModel.Default(
                        title = title,
                        resetLabel = uiModel.resetLabel,
                        closeContentDescription = uiModel.closeContentDescription
                    ),
                    onCloseClick = onCloseClick,
                    onResetClick = onResetClick
                )
            }
        } else {
            null
        },
        content = content
    )
}
