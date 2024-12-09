package com.practicum.spisokpokupok.lists.presentation.currentlists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.practicum.spisokpokupok.lists.presentation.completedlists.CompletedPurchasesListScreen

@Composable
fun HorizontalPager() {
    val pagerState = rememberPagerState(pageCount = { 2 })

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        when (page) {
            0 -> CurrentPurchasesListScreen(
                onNavigateToNewList = { },
                onNavigateToCompletedLists = { },
                onItemClicked = { }
            )
            1 -> CompletedPurchasesListScreen(
                onNavigateToNewList = {  },
                onNavigateToCurrentLists = {  },
                onItemClicked = {  }
            )
        }
    }

}