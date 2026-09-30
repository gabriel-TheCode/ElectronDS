package com.electron.catalog

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.electron.catalog.app.AppViewModel
import com.electron.catalog.app.ElectronCatalogApp
import com.electron.designsystem.foundation.ElectronTheme

class CatalogActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // The system splash shows the logo on the canvas color, then hands
        // over to the home screen painted in the same color.
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val state by appViewModel.state.collectAsStateWithLifecycle()
            val isDarkTheme = state.darkThemeOverride ?: isSystemInDarkTheme()

            // Status and navigation bar icons follow the app theme, which the
            // user can change independently of the system.
            DisposableEffect(isDarkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { isDarkTheme },
                    navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { isDarkTheme }
                )
                onDispose {}
            }

            ElectronTheme(darkTheme = isDarkTheme) {
                ElectronCatalogApp(
                    state = state,
                    isDarkTheme = isDarkTheme,
                    onIntent = appViewModel::onIntent
                )
            }
        }
    }
}
