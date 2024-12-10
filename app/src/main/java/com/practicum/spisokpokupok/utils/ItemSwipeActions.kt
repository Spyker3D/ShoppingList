package com.practicum.spisokpokupok.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.practicum.spisokpokupok.R
import kotlinx.coroutines.delay

@Composable
fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    onAttach: (T) -> Unit,
    animationDuration: Int = 500,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    var isAttached by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value -> // прописать слева направо
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> {
                    isRemoved = true
                    true
                }
                SwipeToDismissBoxValue.StartToEnd -> {
                    isAttached = true
                    true
                }
                else -> {
                    false
                }
            }
        }
    )

    AnimatedVisibility(
        visible = !isRemoved,
        exit = shrinkVertically(
            animationSpec = tween(durationMillis = animationDuration),
            shrinkTowards = Alignment.Top
        ) + fadeOut()
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {
                SwipeBackground(swipeDismissState = state)
            },
            content = { content(item) },
        )
    }

    LaunchedEffect(key1 = isRemoved || isAttached) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
            isRemoved = false
        } else if (isAttached) {
            onAttach(item)
            isAttached = false
        }
    }
}

@Composable
fun SwipeBackground(
    swipeDismissState: SwipeToDismissBoxState
) {
    val color = Color.Transparent
    val isSwipingToDelete = swipeDismissState.targetValue == SwipeToDismissBoxValue.EndToStart
    val isSwipingToAttach = swipeDismissState.targetValue == SwipeToDismissBoxValue.StartToEnd


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp)
    ) {
        if (isSwipingToDelete) {
            Icon(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.ic_delete_blue),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        } else if (isSwipingToAttach) {
            Icon(
                modifier = Modifier.align(Alignment.CenterStart),
                painter = painterResource(id = R.drawable.ic_blue_paperclip),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
