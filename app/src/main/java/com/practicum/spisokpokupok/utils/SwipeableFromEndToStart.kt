package com.practicum.spisokpokupok.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SwipeableRightItem(
    swipeState: SwipeState,
    numberOfIcons: Int,
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    swipeableContent: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val iconWidth = with(LocalDensity.current) { 60.dp.toPx() }
    val maxSwipeDistance = iconWidth * numberOfIcons

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            actions()
        }
        val density = LocalDensity.current
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(end = with(density) { -swipeState.offset.value.toDp() })
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newOffset =
                                        (swipeState.offset.value + dragAmount)
                                            .coerceIn(-maxSwipeDistance, 0f)
                                    swipeState.offset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                when {
                                    swipeState.offset.value < -iconWidth * numberOfIcons / 2 -> {
                                        scope.launch {
                                            swipeState.offset.animateTo(-maxSwipeDistance)
                                        }
                                    }

                                    else -> {
                                        scope.launch {
                                            swipeState.offset.animateTo(0f)
                                        }
                                    }
                                }
                            },
                        )
                    },
        ) {
            swipeableContent()
        }
    }
}

class SwipeState {
    val offset = Animatable(initialValue = 0f)

    suspend fun hide() {
        offset.animateTo(0f)
    }
}
