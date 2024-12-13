package com.practicum.spisokpokupok.lists.presentation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practicum.spisokpokupok.lists.presentation.completedlists.CompletedPurchasesListScreen
import com.practicum.spisokpokupok.lists.presentation.currentlists.CurrentListViewModel
import com.practicum.spisokpokupok.lists.presentation.currentlists.CurrentPurchasesListScreen

@Composable
fun HorizontalPagerScreen(
    modifier: Modifier = Modifier,
    initialPage: Int = 0,
    onNavigateToNewList: () -> Unit,
    onItemCurrentClicked: (String) -> Unit,
    onItemCompletedClicked: (String) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { 2 })
    val currentListViewModel: CurrentListViewModel = hiltViewModel()

    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        when (page) {
            0 -> {
                CurrentPurchasesListScreen(
                    onNavigateToNewList = onNavigateToNewList,
                    onItemClicked = onItemCurrentClicked,
                    shoppingList = currentListViewModel.listStream.collectAsState().value,
                    onDeleteItem = currentListViewModel::deleteList,
                    onFavoriteItem = { id, isFavorite ->
                        currentListViewModel.updateFavoriteStatus(
                            listId = id,
                            isFavorite = isFavorite
                        )
                    }
                )
            }

            1 -> CompletedPurchasesListScreen(
                onNavigateToNewList = onNavigateToNewList,
                onItemClicked = onItemCompletedClicked,
                onDeleteItem = currentListViewModel::deleteList
            )
        }
    }
}