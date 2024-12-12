package com.practicum.spisokpokupok.utils.swipetest

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp


@Composable
fun SwipeableList2() {
    val items = remember { mutableStateListOf("Item 1", "Item 2", "Item 3") }
    val swipedOffsets = remember { mutableStateMapOf<String, Float>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = items) { item ->
            SwipeableItem(
                item = item,
                currentOffset = swipedOffsets[item] ?: 0f,
                onSwipeRightComplete = { offset ->
                    swipedOffsets[item] = offset
                },
                onSwipeLeftComplete = {
                    items.remove(item)
                }
            )
        }
    }
}

@Composable
fun SwipeableItem(
    item: String,
    currentOffset: Float,
    onSwipeRightComplete: (Float) -> Unit,
    onSwipeLeftComplete: () -> Unit
) {
    var swipeOffset by remember { mutableStateOf(currentOffset) }
    val maxRightOffset = 40f // Maximum offset for swiping to the right
    val maxLeftOffset = -150f // Maximum offset for swiping to the left
    val paperclipVisibleThreshold = 20f // Threshold for keeping the offset

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, delta ->
                        // Update swipe offset within the range
                        swipeOffset = (swipeOffset + delta).coerceIn(maxLeftOffset, maxRightOffset)
                    },
                    onDragEnd = {
                        when {
                            // Swiped far enough to the right
                            swipeOffset >= paperclipVisibleThreshold -> {
                                swipeOffset = maxRightOffset
                                onSwipeRightComplete(swipeOffset)
                            }
                            // Swiped far enough to the left
                            swipeOffset <= maxLeftOffset * 0.5 -> {
                                onSwipeLeftComplete()
                            }
                            else -> {
                                swipeOffset = 0f
                                onSwipeRightComplete(0f)
                            }
                        }
                    }
                )
            }
    ) {
        // Background content for swipe
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            if (swipeOffset > 0) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_blue_paperclip),
                    contentDescription = "Paperclip",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .alpha(if (swipeOffset > 0f) 1f else 0f) // Show only during right swipe
                )
            } else if (swipeOffset < 0) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete_blue),
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .alpha(if (swipeOffset < 0f) 1f else 0f) // Show only during left swipe
                )
            }
        }

        // Foreground content (list item)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(x = swipeOffset.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {

            Text(
                text = item,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
