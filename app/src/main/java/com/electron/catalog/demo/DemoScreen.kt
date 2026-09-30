package com.electron.catalog.demo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.electron.designsystem.components.dialog.ElectronDialog
import com.electron.designsystem.components.navigationbar.ElectronNavigationBar
import com.electron.designsystem.components.topbar.ElectronTopBar
import com.electron.designsystem.layout.ElectronScaffold
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronSpacing

/** Width from which the demo uses a navigation rail instead of a bottom bar. */
internal val ExpandedWidth = 600.dp

/** Content column cap on tablets, so cards and rows keep a readable length. */
private val ContentMaxWidth = 640.dp

@Composable
fun DemoRoute(
    isDarkTheme: Boolean,
    onBackClick: () -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    viewModel: DemoViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DemoScreen(
        state = state,
        isDarkTheme = isDarkTheme,
        onIntent = viewModel::onIntent,
        onBackClick = onBackClick,
        onDarkThemeChange = onDarkThemeChange
    )
}

/**
 * Volt's frame. The same destinations render as a bottom bar on phones and
 * as a rail beside the content on tablets.
 */
@Composable
fun DemoScreen(
    state: DemoState,
    isDarkTheme: Boolean,
    onIntent: (DemoIntent) -> Unit,
    onBackClick: () -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val onTabSelected: (Int) -> Unit = { onIntent(DemoIntent.SelectTab(it)) }
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        if (maxWidth >= ExpandedWidth) {
            Row(modifier = Modifier.fillMaxSize()) {
                ElectronNavigationBar(uiModel = state.toRailUiModel(), onItemSelected = onTabSelected)
                DemoFrame(
                    state = state,
                    isDarkTheme = isDarkTheme,
                    onIntent = onIntent,
                    onBackClick = onBackClick,
                    onDarkThemeChange = onDarkThemeChange,
                    bottomBar = {},
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            DemoFrame(
                state = state,
                isDarkTheme = isDarkTheme,
                onIntent = onIntent,
                onBackClick = onBackClick,
                onDarkThemeChange = onDarkThemeChange,
                bottomBar = {
                    ElectronNavigationBar(uiModel = state.toBottomBarUiModel(), onItemSelected = onTabSelected)
                }
            )
        }
    }

    if (state.isDisconnectDialogVisible) {
        ElectronDialog(
            uiModel = DisconnectDialogUiModel,
            onConfirmClick = { onIntent(DemoIntent.ConfirmDisconnect) },
            onDismissRequest = { onIntent(DemoIntent.DismissDisconnect) }
        )
    }
}

@Composable
private fun DemoFrame(
    state: DemoState,
    isDarkTheme: Boolean,
    onIntent: (DemoIntent) -> Unit,
    onBackClick: () -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    bottomBar: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    ElectronScaffold(
        modifier = modifier,
        topBar = {
            ElectronTopBar(
                uiModel = state.toTopBarUiModel(),
                onNavigationClick = onBackClick,
                onActionClick = { onIntent(DemoIntent.OpenAlerts) }
            )
        },
        bottomBar = bottomBar
    ) { padding ->
        // Switching tabs cross-fades the content; the frame stays put.
        AnimatedContent(
            targetState = state.tab,
            transitionSpec = {
                fadeIn(tween(ElectronMotion.standard, easing = ElectronMotion.easeEnter)) togetherWith
                    fadeOut(tween(ElectronMotion.quick, easing = ElectronMotion.easeExit))
            },
            label = "demoTab",
            modifier = Modifier.padding(padding)
        ) { tab ->
            // Capped and start-aligned: on tablets the column lines up under
            // the top bar title instead of floating in the middle.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = ElectronSpacing.lg)
                    .padding(top = ElectronSpacing.xs, bottom = ElectronSpacing.xl)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md),
                    modifier = Modifier
                        .widthIn(max = ContentMaxWidth)
                        .fillMaxWidth()
                ) {
                    when (tab) {
                        DemoTab.Home -> DemoHomeTab(state, onIntent)
                        DemoTab.Charging -> DemoChargingTab(state, onIntent)
                        DemoTab.Account -> DemoAccountTab(state, isDarkTheme, onIntent, onDarkThemeChange)
                    }
                }
            }
        }
    }
}
