package com.electron.catalog.app

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import com.electron.catalog.CatalogScreen
import com.electron.catalog.demo.DemoRoute
import com.electron.catalog.home.HomeScreen
import com.electron.designsystem.tokens.ElectronMotion

/**
 * App root. Navigation is part of the MVI state: the destination is a field
 * of [AppState], changed only by intents, so back handling and transitions
 * are plain functions of that state.
 */
@Composable
fun ElectronCatalogApp(
    state: AppState,
    isDarkTheme: Boolean,
    onIntent: (AppIntent) -> Unit
) {
    BackHandler(enabled = state.destination != Destination.Home) {
        onIntent(AppIntent.NavigateBack)
    }
    val onDarkThemeChange: (Boolean) -> Unit = { onIntent(AppIntent.SetDarkTheme(it)) }
    val onBack: () -> Unit = { onIntent(AppIntent.NavigateBack) }

    AnimatedContent(
        targetState = state.destination,
        transitionSpec = {
            // Going deeper slides in from the end; going home slides back.
            val direction = if (targetState == Destination.Home) -1 else 1
            (slideInHorizontally(tween(ElectronMotion.emphasized, easing = ElectronMotion.easeStandard)) { direction * it / 4 } +
                fadeIn(tween(ElectronMotion.standard, easing = ElectronMotion.easeEnter))) togetherWith
                (slideOutHorizontally(tween(ElectronMotion.emphasized, easing = ElectronMotion.easeStandard)) { -direction * it / 4 } +
                    fadeOut(tween(ElectronMotion.quick, easing = ElectronMotion.easeExit)))
        },
        label = "destination"
    ) { destination ->
        when (destination) {
            Destination.Home -> HomeScreen(isDarkTheme = isDarkTheme, onIntent = onIntent)
            Destination.Components -> CatalogScreen(
                isDarkTheme = isDarkTheme,
                onThemeToggled = onDarkThemeChange,
                onBackClick = onBack
            )
            Destination.Demo -> DemoRoute(
                isDarkTheme = isDarkTheme,
                onBackClick = onBack,
                onDarkThemeChange = onDarkThemeChange
            )
        }
    }
}
