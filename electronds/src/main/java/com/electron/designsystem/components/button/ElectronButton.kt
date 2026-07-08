package com.electron.designsystem.components.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.electron.designsystem.components.button.models.ButtonSize
import com.electron.designsystem.components.button.models.ButtonState
import com.electron.designsystem.components.button.models.ButtonUiModel
import com.electron.designsystem.components.button.variants.ButtonLink
import com.electron.designsystem.components.button.variants.ButtonLoading
import com.electron.designsystem.components.button.variants.ButtonPrimary
import com.electron.designsystem.components.button.variants.ButtonSecondary
import com.electron.designsystem.components.button.variants.ButtonTertiary
import com.electron.designsystem.utils.ElectronPreviewSurface

/**
 * ElectronButton
 *
 * Purpose: the only button API exposed to features. The UI model provided
 * by the screen selects the variant; the design system decides how each
 * variant renders.
 *
 * API:
 * - [uiModel]: visual configuration (sealed [ButtonUiModel]).
 * - [onClick]: signal emitted upward, never interpreted here.
 *
 * Usage:
 * ```
 * ElectronButton(
 *     uiModel = ButtonUiModel.Primary(text = "Confirm transfer", isFullWidth = true),
 *     onClick = { viewModel.onEvent(PaymentUiEvent.ConfirmClicked) }
 * )
 * ```
 */
@Composable
public fun ElectronButton(
    uiModel: ButtonUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiModel) {
        is ButtonUiModel.Primary -> ButtonPrimary(uiModel, onClick, modifier)
        is ButtonUiModel.Secondary -> ButtonSecondary(uiModel, onClick, modifier)
        is ButtonUiModel.Tertiary -> ButtonTertiary(uiModel, onClick, modifier)
        is ButtonUiModel.Link -> ButtonLink(uiModel, onClick, modifier)
        is ButtonUiModel.Loading -> ButtonLoading(uiModel, modifier)
    }
}

@Preview(showBackground = true, name = "Buttons / Light")
@Composable
private fun ElectronButtonPreview() {
    ElectronPreviewSurface {
        ElectronButton(ButtonUiModel.Primary(text = "Primary"), onClick = {})
        ElectronButton(ButtonUiModel.Primary(text = "Success", state = ButtonState.Success), onClick = {})
        ElectronButton(ButtonUiModel.Primary(text = "Disabled", isEnabled = false), onClick = {})
        ElectronButton(ButtonUiModel.Secondary(text = "Secondary"), onClick = {})
        ElectronButton(ButtonUiModel.Tertiary(text = "Tertiary", size = ButtonSize.Medium), onClick = {})
        ElectronButton(ButtonUiModel.Link(text = "Link button"), onClick = {})
        ElectronButton(ButtonUiModel.Loading(text = "Charging"), onClick = {})
    }
}

@Preview(showBackground = true, name = "Buttons / Dark", backgroundColor = 0xFF0B0F1A)
@Composable
private fun ElectronButtonDarkPreview() {
    ElectronPreviewSurface(darkTheme = true) {
        ElectronButton(ButtonUiModel.Primary(text = "Primary"), onClick = {})
        ElectronButton(ButtonUiModel.Secondary(text = "Secondary"), onClick = {})
        ElectronButton(ButtonUiModel.Primary(text = "Error", state = ButtonState.Error), onClick = {})
    }
}
