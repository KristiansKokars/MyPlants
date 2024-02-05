package com.kristianskokars.myplants.core.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFold
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastForEachIndexed
import androidx.compose.ui.util.fastMap
import com.kristianskokars.myplants.core.presentation.components.TabRowDefaults.tabIndicatorOffset
import com.kristianskokars.myplants.ui.theme.Accent500

/** Modified version of Material3 tab row, to change the indicator and child width behaviour. */
@Composable
fun MyPlantsTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        if (selectedTabIndex < tabPositions.size) {
            SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        }
    },
    tabs: @Composable () -> Unit
) {
    TabRowWithSubcomposeImpl(modifier, containerColor, contentColor, indicator, tabs)
}

@Composable
fun SecondaryIndicator(
    modifier: Modifier = Modifier,
    height: Dp = 3.dp,
    color: Color = Accent500
) {
    Box(
        modifier
            .width(24.dp)
            .height(height)
            .background(color = color, shape = RoundedCornerShape(4.dp))
    )
}

object TabRowDefaults {
    fun Modifier.tabIndicatorOffset(
        currentTabPosition: TabPosition
    ): Modifier = composed(
        inspectorInfo = debugInspectorInfo {
            name = "tabIndicatorOffset"
            value = currentTabPosition
        }
    ) {
        val currentTabWidth by animateDpAsState(
            targetValue = currentTabPosition.width,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "CurrentTabWidth"
        )
        val indicatorOffset by animateDpAsState(
            targetValue = currentTabPosition.left,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "IndicatorOffset"
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(currentTabWidth * 0.5f)
    }
}

private enum class TabSlots {
    Tabs,
    Indicator
}

@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp, val contentWidth: Dp) {
    private val right: Dp get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false
        return contentWidth == other.contentWidth
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + contentWidth.hashCode()
        return result
    }

    override fun toString(): String {
        return "TabPosition(left=$left, right=$right, width=$width, contentWidth=$contentWidth)"
    }
}

@Composable
private fun TabRowWithSubcomposeImpl(
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit,
    tabs: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.selectableGroup(),
        color = containerColor,
        contentColor = contentColor
    ) {
        SubcomposeLayout { constraints ->
            val tabRowWidth = constraints.maxWidth
            val tabMeasurables = subcompose(TabSlots.Tabs, tabs)
            val tabCount = tabMeasurables.size
            var tabWidth = 0
            if (tabCount > 0) {
                tabWidth = (tabRowWidth / tabCount)
            }
            val tabRowHeight = tabMeasurables.fastFold(initial = 0) { max, curr ->
                maxOf(curr.maxIntrinsicHeight(tabWidth), max)
            } - 4.dp.roundToPx()

            val tabPlaceables = tabMeasurables.fastMap {
                it.measure(
                    constraints
                )
            }

            val contentWidths = mutableListOf<Dp>()
            val tabPositions = List(tabCount) { index ->
                val contentWidth = tabMeasurables[index].maxIntrinsicWidth(tabRowHeight).toDp()
                // Enforce minimum touch target of 24.dp
                val indicatorWidth = maxOf(contentWidth, 24.dp)
                val leftOffset = contentWidths.fold(0.dp) { acc, dp -> acc + dp }
                contentWidths.add(contentWidth + 24.dp)
                TabPosition(leftOffset, contentWidth, indicatorWidth)
            }

            var xPosition = 0
            val tabWidths = mutableListOf<Int>()
            layout(tabRowWidth, tabRowHeight) {
                tabPlaceables.fastForEach { placeable ->
                    placeable.placeRelative(xPosition, 0)
                    val width = placeable.width + 24.dp.roundToPx() // TODO: move parameter out
                    xPosition += width
                    tabWidths.add(placeable.width)
                }

                subcompose(TabSlots.Indicator) {
                    indicator(tabPositions)
                }.fastForEachIndexed { index, measurable ->
                    measurable.measure(Constraints.fixed(tabWidths[index], tabRowHeight)).placeRelative(0, 0)
                }
            }
        }
    }
}
