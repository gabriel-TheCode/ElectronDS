package com.electron.designsystem.components.card.primitives

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronElevation
import com.electron.designsystem.tokens.ElectronShapes

/**
 * Card surface block: shape, border and elevation only.
 * Electron cards prefer a hairline border over a shadow.
 */
@Composable
internal fun CardPrimitive(
    containerColor: Color,
    borderColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        shape = ElectronShapes.card,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = ElectronElevation.flat),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = ElectronDimens.borderWidth,
                color = borderColor,
                shape = ElectronShapes.card
            )
            .testTag(testTag)
    ) {
        Column(content = content)
    }
}
