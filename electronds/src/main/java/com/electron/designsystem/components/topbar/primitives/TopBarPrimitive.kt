package com.electron.designsystem.components.topbar.primitives

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronSpacing

/**
 * 48dp icon button with an optional badge overlay. IconButton provides the
 * minimum touch target and ripple through Material semantics.
 */
@Composable
internal fun TopBarIconButton(
    onClick: () -> Unit,
    testTag: String,
    icon: @Composable () -> Unit,
    badge: (@Composable () -> Unit)? = null
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(ElectronDimens.minTouchTarget)
            .testTag(testTag)
    ) {
        Box {
            icon()
            if (badge != null) {
                Box(modifier = Modifier.align(Alignment.TopEnd)) { badge() }
            }
        }
    }
}

/**
 * Bar layout: navigation slot, title block, action slot, and an optional
 * large headline row below.
 */
@Composable
internal fun TopBarPrimitive(
    backgroundColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    titleStyle: TextStyle = TextStyle.Default,
    titleColor: Color = Color.Unspecified,
    subtitle: String? = null,
    subtitleStyle: TextStyle = TextStyle.Default,
    subtitleColor: Color = Color.Unspecified,
    largeTitle: String? = null,
    largeTitleStyle: TextStyle = TextStyle.Default,
    navigation: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null
) {
    // The background extends under the status bar and the content starts
    // below it, so the bar works in edge-to-edge screens without extra code.
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(ElectronDimens.topBarHeight)
                .padding(horizontal = ElectronSpacing.xs)
        ) {
            if (navigation != null) {
                navigation()
            } else {
                Spacer(modifier = Modifier.width(ElectronSpacing.md))
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = ElectronSpacing.xs)
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        style = titleStyle,
                        color = titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = subtitleStyle,
                        color = subtitleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            action?.invoke()
        }
        if (largeTitle != null) {
            Text(
                text = largeTitle,
                style = largeTitleStyle,
                color = titleColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    start = ElectronSpacing.lg,
                    end = ElectronSpacing.lg,
                    bottom = ElectronSpacing.md
                )
            )
        }
    }
}
