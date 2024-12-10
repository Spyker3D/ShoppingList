package com.practicum.spisokpokupok.lists.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.spisokpokupok.lists.presentation.completedlists.CompletedPurchasesListScreen
import com.practicum.spisokpokupok.lists.presentation.currentlists.CurrentPurchasesListScreen

@Composable
fun HorizontalPagerScreen(
    modifier: Modifier = Modifier,
    onNavigateToNewList: () -> Unit,
    onItemCurrentClicked: (String) -> Unit,
    onItemCompletedClicked: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        when (page) {
            0 -> CurrentPurchasesListScreen(
                onNavigateToNewList = onNavigateToNewList,
                onItemClicked = onItemCurrentClicked
            )
            1 -> CompletedPurchasesListScreen(
                onNavigateToNewList = onNavigateToNewList,
                onItemClicked = onItemCompletedClicked
            )
        }
    }
}