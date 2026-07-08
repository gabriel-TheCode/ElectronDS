package com.electron.designsystem.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.electron.designsystem.foundation.ElectronTheme
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * Internal helper so every component preview renders on the Electron canvas
 * in both light and dark themes without duplicating boilerplate.
 */
@Composable
internal fun ElectronPreviewSurface(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    ElectronTheme(darkTheme = darkTheme) {
        Column(
            modifier = Modifier
                .background(ElectronTheme.colors.background.canvas)
                .padding(ElectronSpacing.lg),
            verticalArrangement = Arrangement.spacedBy(ElectronSpacing.md)
        ) {
            content()
        }
    }
}
