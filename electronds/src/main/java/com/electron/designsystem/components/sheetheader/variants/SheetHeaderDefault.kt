package com.electron.designsystem.components.sheetheader.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.components.sheetheader.primitives.SheetHeaderPrimitive

@Composable
internal fun SheetHeaderDefault(
    uiModel: SheetHeaderUiModel.Default,
    onCloseClick: () -> Unit,
    onResetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SheetHeaderPrimitive(
        title = uiModel.title,
        resetLabel = uiModel.resetLabel,
        closeContentDescription = uiModel.closeContentDescription,
        testTag = uiModel.testTag,
        onCloseClick = onCloseClick,
        onResetClick = onResetClick,
        modifier = modifier
    )
}
