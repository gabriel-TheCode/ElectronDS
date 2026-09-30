package com.electron.catalog

import com.electron.catalog.app.AppIntent
import com.electron.catalog.app.AppState
import com.electron.catalog.app.Destination
import com.electron.catalog.app.reduce
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppReducerTest {

    @Test
    fun `starts on home and follows the system theme`() {
        val state = AppState()
        assertEquals(Destination.Home, state.destination)
        assertNull(state.darkThemeOverride)
    }

    @Test
    fun `entries open their destination and back returns home`() {
        val components = AppState().reduce(AppIntent.OpenComponents)
        assertEquals(Destination.Components, components.destination)
        assertEquals(Destination.Demo, components.reduce(AppIntent.NavigateBack).reduce(AppIntent.OpenDemo).destination)
        assertEquals(Destination.Home, components.reduce(AppIntent.NavigateBack).destination)
    }

    @Test
    fun `the theme choice survives navigation`() {
        val state = AppState()
            .reduce(AppIntent.SetDarkTheme(true))
            .reduce(AppIntent.OpenDemo)
            .reduce(AppIntent.NavigateBack)
        assertEquals(true, state.darkThemeOverride)
    }
}
