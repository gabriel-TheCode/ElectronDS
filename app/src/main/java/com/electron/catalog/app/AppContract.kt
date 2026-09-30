package com.electron.catalog.app

/** Top-level screens. The home screen is the root; the others go back to it. */
enum class Destination { Home, Components, Demo }

/** App-wide state: where the user is, and the theme they picked. */
data class AppState(
    val destination: Destination = Destination.Home,
    /** Null follows the system setting until the user picks a theme. */
    val darkThemeOverride: Boolean? = null
)

sealed interface AppIntent {
    data object OpenComponents : AppIntent
    data object OpenDemo : AppIntent
    data object NavigateBack : AppIntent
    data class SetDarkTheme(val isEnabled: Boolean) : AppIntent
}

internal fun AppState.reduce(intent: AppIntent): AppState = when (intent) {
    AppIntent.OpenComponents -> copy(destination = Destination.Components)
    AppIntent.OpenDemo -> copy(destination = Destination.Demo)
    AppIntent.NavigateBack -> copy(destination = Destination.Home)
    is AppIntent.SetDarkTheme -> copy(darkThemeOverride = intent.isEnabled)
}
