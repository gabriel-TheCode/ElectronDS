package com.electron.catalog.screenshot

import androidx.compose.runtime.Composable
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.electron.catalog.app.AppIntent
import com.electron.catalog.demo.DemoScreen
import com.electron.catalog.demo.DemoState
import com.electron.catalog.demo.DemoTab
import com.electron.catalog.home.HomeScreen
import com.electron.designsystem.foundation.ElectronTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

enum class AppTheme(val isDark: Boolean) { Light(false), Dark(true) }

enum class AppDevice(val config: DeviceConfig) {
    Phone(DeviceConfig.PIXEL_5),

    /** Pixel 5 width, tall enough to review a whole scrolling page at once. */
    PhoneFullPage(DeviceConfig.PIXEL_5.copy(screenHeight = 4000)),
    Tablet(DeviceConfig.PIXEL_C)
}

/**
 * The app's own screens: home and the Volt demo, in both themes, on phone
 * and tablet. The full-page phone variant shows the home screen from top to
 * bottom, since it scrolls on a real phone.
 */
@RunWith(Parameterized::class)
class AppScreenshotTest(
    private val theme: AppTheme,
    private val device: AppDevice
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}-{1}")
        fun parameters(): List<Array<Any>> =
            AppTheme.entries.flatMap { theme -> AppDevice.entries.map { device -> arrayOf<Any>(theme, device) } }
    }

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = device.config,
        renderingMode = SessionParams.RenderingMode.NORMAL,
        showSystemUi = false,
        maxPercentDifference = 0.1
    )

    @Test
    fun home() = snapshot {
        HomeScreen(isDarkTheme = theme.isDark, onIntent = { _: AppIntent -> })
    }

    @Test
    fun demoDashboard() = demo(DemoState(tab = DemoTab.Home))

    @Test
    fun demoCharging() = demo(DemoState(tab = DemoTab.Charging))

    @Test
    fun demoAccount() = demo(DemoState(tab = DemoTab.Account))

    private fun demo(state: DemoState) {
        // The full-page variant only matters for the scrolling home screen.
        if (device == AppDevice.PhoneFullPage) return
        snapshot {
            DemoScreen(
                state = state,
                isDarkTheme = theme.isDark,
                onIntent = {},
                onBackClick = {},
                onDarkThemeChange = {}
            )
        }
    }

    private fun snapshot(content: @Composable () -> Unit) {
        paparazzi.snapshot {
            ElectronTheme(darkTheme = theme.isDark) { content() }
        }
    }
}
