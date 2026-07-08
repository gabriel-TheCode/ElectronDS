package com.electron.designsystem.components.sheetheader.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.sheetheader.models.SheetHeaderSignal
import com.electron.designsystem.components.sheetheader.models.SheetHeaderUiModel
import com.electron.designsystem.components.sheetheader.primitives.SheetHeaderPrimitive

@Composable
internal fun SheetHeaderDefault(
    uiModel: SheetHeaderUiModel.Default,
    onSignal: (SheetHeaderSignal) -> Unit,
    modifier: Modifier = Modifier
) {
    SheetHeaderPrimitive(
        title = uiModel.title,
        resetLabel = uiModel.resetLabel,
        closeContentDescription = uiModel.closeContentDescription,
        testTag = uiModel.testTag,
        onCloseClick = { onSignal(SheetHeaderSignal.CloseClicked) },
        onResetClick = { onSignal(SheetHeaderSignal.ResetClicked) },
        modifier = modifier
    )
}
