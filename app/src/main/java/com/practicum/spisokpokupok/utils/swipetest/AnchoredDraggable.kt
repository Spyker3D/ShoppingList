package com.practicum.spisokpokupok.utils.swipetest

import android.transition.SidePropagation
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.overscroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import com.practicum.spisokpokupok.lists.domain.model.PurchaseList
import androidx.compose.ui.unit.dp


//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun DraggableElement(purchaseList: PurchaseList) {
//    val elementDragState = remember {
//        AnchoredDraggableState(initialValue = SwipetoReplyValue.Resting)
//    }
//    val messageOverscroll = ScrollableDefaults.overscrollEffect()
//
//    val density = LocalDensity.current
//    val anchors = remember(density) {
//        replyOffset = with(density) { 48.dp.toPx() }
//        DraggableAnchors {
//            SwipeToReplyValue.Resting at 0f
//            SwipeToReplyValue.Replying at replyOffset
//        }
//    }
//    SideEffect { elementDragState.updateAnchors(anchors) }
//
//    Box(
//        Modifier
//            .anchoredDraggable(
//                elementDragState,
//                Orientation.Horizontal,
//                overscrollEffect = messageOverscroll
//            )
//            .overscroll(messageOverscroll)
//            .offset {
//                IntOffset(
//                    x = elementDragState.requireOffset().rountToInt(),
//                    y = 0
//                )
//            }
//    )
//}

