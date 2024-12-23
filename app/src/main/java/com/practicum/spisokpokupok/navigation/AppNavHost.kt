package com.practicum.spisokpokupok.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.practicum.spisokpokupok.listdetails.presentation.editcompletedlist.CompletedListEditScreen
import com.practicum.spisokpokupok.listdetails.presentation.editcompletedlist.EditCompletedListViewModel
import com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist.CurrentLIstEditScreenViewModel
import com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist.CurrentListEditScreen
import com.practicum.spisokpokupok.listdetails.presentation.newlist.NewListScreen
import com.practicum.spisokpokupok.listdetails.presentation.newlist.NewListViewModel
import com.practicum.spisokpokupok.lists.presentation.InfiniteHorizontalPager
import kotlinx.serialization.Serializable

const val MAX_PAGES = Short.MAX_VALUE.toInt()
const val PAGE_COUNT = 2
const val PAGER_INITIAL_POSITION = MAX_PAGES / PAGE_COUNT + MAX_PAGES % PAGE_COUNT
const val PAGER_COMPLETED_LISTS = PAGER_INITIAL_POSITION + 1

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = HorizontalPagerRoute(targetPage = PAGER_INITIAL_POSITION),
    ) {
        composable<HorizontalPagerRoute> {
            val args = it.toRoute<HorizontalPagerRoute>()

            InfiniteHorizontalPager(
                initialPage = args.targetPage,
                onNavigateToNewList = { navController.navigate(route = NewListRoute) },
                onItemCurrentClicked = { id ->
                    navController.navigate(
                        route =
                        CurrentListEditRoute(
                            id = id,
                        ),
                    )
                },
                onItemCompletedClicked = { id ->
                    navController.navigate(
                        route =
                        CompletedListEditRoute(
                            listId = id,
                        ),
                    )
                },
                pageCount = PAGE_COUNT,
                maxPages = MAX_PAGES,
            )
        }

        composable<NewListRoute> {
            val viewModel: NewListViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            NewListScreen(
                onNavigateToCurrentLists = {
                    navController.navigate(
                        route =
                            HorizontalPagerRoute(
                                targetPage = PAGER_INITIAL_POSITION,
                            ),
                    ) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                },
                onBackPressed = { navController.popBackStack() },
                action = viewModel::consumeAction,
                state = state,
            )
        }

        composable<CurrentListEditRoute> {
            val arguments = it.toRoute<CurrentListEditRoute>()
            val viewModel: CurrentLIstEditScreenViewModel = hiltViewModel(it)
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            CurrentListEditScreen(
                onNavigateToCompletedList = {
                    navController.navigate(
                        route =
                            HorizontalPagerRoute(
                                targetPage = PAGER_COMPLETED_LISTS,
                            ),
                    ) {
                        popUpTo<HorizontalPagerRoute>() {
                            inclusive = true
                        }
                    }
                },
                onBackPressed = { navController.popBackStack() },
                state = state,
                action = viewModel::consumeAction,
            )
        }

        composable<CompletedListEditRoute> {
            val args = it.toRoute<CompletedListEditRoute>()
            val viewModel: EditCompletedListViewModel = hiltViewModel(it)

            CompletedListEditScreen(
                listId = args.listId,
                onNavigateToCurrentLists = {
                    navController.navigate(
                        route =
                            HorizontalPagerRoute(
                                targetPage = PAGER_INITIAL_POSITION,
                            ),
                    ) {
                        popUpTo(navController.graph.id) {
                                inclusive = true
                        }
                    }
                },
                onBackPressed = { navController.popBackStack() },
                listOfItems = viewModel.listOfItems,
                listName = viewModel.listName,
                moveFromCompletedToActualList = viewModel::moveFromCompletedToActualLists,
            )
        }
    }
}

@Serializable
data class HorizontalPagerRoute(
    val targetPage: Int = PAGER_INITIAL_POSITION,
)

@Serializable
object NewListRoute

@Serializable
data class CurrentListEditRoute(
    val id: String,
)

@Serializable
data class CompletedListEditRoute(
    val listId: String,
)
