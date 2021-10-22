package com.example.dogbox.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import com.example.dogbox.utils.minWithIndex

/* TODO: https://medium.com/mobile-app-development-publication/staggeredverticalgrid-of-android-jetpack-compose-fa565e5363e1*/
/* TODO: https://github.com/android/compose-samples/blob/73d7f25815e6936e0e815ce975905a6f10744c36/Jetsnack/app/src/main/java/com/example/jetsnack/ui/components/Grid.kt*/

@Composable
fun StaggeredVerticalGrid (
    modifier: Modifier = Modifier,
    columns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->

        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val placeableXY: MutableMap<Placeable, Pair<Int, Int>> = mutableMapOf()

        // Calculate for columns and subsequently the items in them
        val columnWidth = constraints.maxWidth/columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)

        // Determine the shortest column and add an element to that column
        val columnHeights = IntArray(columns) { 0 }
        val placeables = measurables.map { measurable ->
            val shortColumnIndex = columnHeights.minWithIndex()
            val placeable = measurable.measure(itemConstraints)
            placeableXY[placeable] = Pair(columnWidth*shortColumnIndex, columnHeights[shortColumnIndex])
            columnHeights[shortColumnIndex] += placeable.height
            placeable
        }

        // Longest column determines the height of the grid
        val totalHeight = columnHeights
            .maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight

        // Place all elements according to their stored positions
        layout(width = constraints.maxWidth, height = totalHeight) {
            for(placeable in placeables) {
                placeable.place(
                    x = placeableXY.getValue(placeable).first,
                    y = placeableXY.getValue(placeable).second
                )
            }
        }
    }
}