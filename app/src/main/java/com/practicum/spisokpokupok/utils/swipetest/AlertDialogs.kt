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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R


@Composable
fun SwipeableList() {
    val items = remember { mutableStateListOf("Item 1", "Item 2", "Item 3") }
    val swipedOffsets = remember { mutableStateMapOf<String, Float>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            SwipeableItem(
                item = item,
                currentOffset = swipedOffsets[item] ?: 0f,
                onSwipeComplete = { offset ->
                    swipedOffsets[item] = offset
                }
            )
        }
    }
}

@Composable
fun SwipeableItem(
    item: String,
    currentOffset: Float,
    onSwipeComplete: (Float) -> Unit
) {
    var swipeOffset by remember { mutableStateOf(currentOffset) }
    val maxOffset = 150f // Maximum offset for swipe
    val paperclipVisibleThreshold = 50f // Threshold for keeping the offset

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, delta ->
                        // Update swipe offset within the range
                        swipeOffset = (swipeOffset + delta).coerceIn(0f, maxOffset)
                    },
                    onDragEnd = {
                        // Fix position if swiped beyond threshold
                        if (swipeOffset >= paperclipVisibleThreshold) {
                            onSwipeComplete(swipeOffset)
                        } else {
                            swipeOffset = 0f
                            onSwipeComplete(0f)
                        }
                    }
                )
            }
    ) {
        // Background content for swipe (paperclip icon)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_blue_paperclip),
                contentDescription = "Paperclip",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .alpha(if (swipeOffset > 0f) 1f else 0f) // Show only during swipe
            )
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




