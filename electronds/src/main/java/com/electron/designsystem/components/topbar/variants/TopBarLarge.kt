package com.electron.designsystem.components.topbar.variants

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.components.topbar.models.TopBarUiModel
import com.electron.designsystem.components.topbar.primitives.TopBarPrimitive
import com.electron.designsystem.foundation.ElectronTheme

@Composable
internal fun TopBarLarge(
    uiModel: TopBarUiModel.Large,
    onNavigationClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = ElectronTheme.colors
    TopBarPrimitive(
        backgroundColor = c.background.canvas,
        titleColor = c.content.primary,
        largeTitle = uiModel.title,
        largeTitleStyle = ElectronTheme.typography.headlineLarge,
        navigation = navigationSlot(
            uiModel.navigation,
            uiModel.navigationContentDescription,
            uiModel.testTag,
            onNavigationClick
        ),
        action = actionSlot(uiModel.action, uiModel.testTag, onActionClick),
        testTag = uiModel.testTag,
        modifier = modifier
    )
}
