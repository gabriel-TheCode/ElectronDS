package com.electron.designsystem.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.foundation.ElectronTheme

/**
 * ElectronScaffold
 *
 * Purpose: page frame with top bar, bottom bar, FAB and content slots,
 * painted on the Electron canvas color. The bottom bar slot takes an
 * ElectronNavigationBar in its `Bottom` variant; on tablets and TV, place
 * the `Rail` variant beside the content instead.
 *
 * Refactor note: the legacy scaffold accepted `isNetworkConnected`
 * and swapped content for an error view internally. That is feature
 * behavior living inside the design system. Whether to show an offline
 * state is a decision that belongs to the screen:
 *
 * ```
 * ElectronScaffold(topBar = { ... }) { padding ->
 *     when (uiState) {
 *         is Offline -> OfflineContent(padding)
 *         is Loaded -> HomeContent(padding, uiState)
 *     }
 * }
 * ```
 */
@Composable
public fun ElectronScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        containerColor = ElectronTheme.colors.background.canvas,
        contentColor = ElectronTheme.colors.content.primary,
        modifier = modifier,
        content = content
    )
}
