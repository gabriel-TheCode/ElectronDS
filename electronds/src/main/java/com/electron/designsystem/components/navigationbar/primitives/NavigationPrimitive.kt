package com.electron.designsystem.components.navigationbar.primitives

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.electron.designsystem.tokens.ElectronDimens
import com.electron.designsystem.tokens.ElectronMotion
import com.electron.designsystem.tokens.ElectronShapes
import com.electron.designsystem.tokens.ElectronSpacing
import com.electron.designsystem.utils.focusRing
import com.electron.designsystem.utils.pressScale

internal data class NavigationColors(
    val container: Color,
    val divider: Color,
    val indicator: Color,
    val selectedIcon: Color,
    val icon: Color,
    val selectedLabel: Color,
    val label: Color
)

/**
 * One destination: an icon inside an indicator pill, label below. The pill
 * grows from icon width to full width when selected and fades its tint in,
 * so the selection visibly moves to the new item. The whole cell is the
 * touch target; the press scale and focus ring apply to the pill, which is
 * where the eye is.
 */
@Composable
internal fun NavigationItemPrimitive(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    colors: NavigationColors,
    labelStyle: TextStyle,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badge: (@Composable () -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) ElectronDimens.navigationIndicatorWidth else ElectronDimens.navigationIndicatorHeight,
        animationSpec = tween(ElectronMotion.standard, easing = ElectronMotion.easeStandard),
        label = "navIndicatorWidth"
    )
    val indicatorColor by animateColorAsState(
        targetValue = if (isSelected) colors.indicator else Color.Transparent,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "navIndicatorColor"
    )
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) colors.selectedIcon else colors.icon,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "navIcon"
    )
    val labelColor by animateColorAsState(
        targetValue = if (isSelected) colors.selectedLabel else colors.label,
        animationSpec = tween(ElectronMotion.quick, easing = ElectronMotion.easeStandard),
        label = "navLabel"
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .selectable(
                selected = isSelected,
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .padding(vertical = ElectronSpacing.sm)
            .testTag(testTag)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .pressScale(interactionSource)
                .focusRing(interactionSource, ElectronShapes.pill)
                .size(ElectronDimens.navigationIndicatorWidth, ElectronDimens.navigationIndicatorHeight)
        ) {
            Box(
                modifier = Modifier
                    .size(indicatorWidth, ElectronDimens.navigationIndicatorHeight)
                    .background(indicatorColor, ElectronShapes.pill)
            )
            Box {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(ElectronDimens.iconLg)
                )
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = ElectronSpacing.xs, y = -ElectronSpacing.xxs)
                    ) { badge() }
                }
            }
        }
        Spacer(modifier = Modifier.height(ElectronSpacing.xs))
        Text(
            text = label,
            style = labelStyle,
            color = labelColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

/** Bottom bar: items share the width; background runs under the system navigation bar. */
@Composable
internal fun BottomNavigationPrimitive(
    colors: NavigationColors,
    testTag: String,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.container)
            .testTag(testTag)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(ElectronDimens.separatorHeight)
                .background(colors.divider)
        )
        Row(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxWidth()
                .height(ElectronDimens.navigationBarHeight)
                .selectableGroup()
        ) {
            content(Modifier.weight(1f).fillMaxHeight())
        }
    }
}

/** Rail: items stacked from the top, for tablets and TV (D-pad moves vertically). */
@Composable
internal fun RailNavigationPrimitive(
    colors: NavigationColors,
    testTag: String,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .background(colors.container)
            .testTag(testTag)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ElectronSpacing.xs),
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Vertical + WindowInsetsSides.Start))
                .fillMaxHeight()
                .width(ElectronDimens.navigationRailWidth)
                .padding(top = ElectronSpacing.lg)
                .selectableGroup()
        ) {
            content(Modifier.fillMaxWidth())
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(ElectronDimens.separatorHeight)
                .background(colors.divider)
        )
    }
}
