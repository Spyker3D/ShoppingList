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
import com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist.CurrentLIstEditScreenViewModel
import com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist.CurrentListEditScreen
import com.practicum.spisokpokupok.listdetails.presentation.newlist.NewListScreen
import com.practicum.spisokpokupok.listdetails.presentation.newlist.NewListViewModel
import com.practicum.spisokpokupok.lists.presentation.HorizontalPagerScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = HorizontalPagerRoute(targetPage = 0),
    ) {
        composable<HorizontalPagerRoute> {
            val args = it.toRoute<HorizontalPagerRoute>()
            HorizontalPagerScreen(
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
                onItemCompletedClicked = { id -> navController.navigate(route = CompletedListEdit(id = id)) },
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
                                targetPage = 0,
                            ),
                    )
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
                                targetPage = 1,
                            ),
                    )
                },
                onBackPressed = { navController.popBackStack() },
                state = state,
                action = viewModel::consumeAction,
            )
        }
        composable<CompletedListEdit> {
            val args = it.toRoute<CompletedListEdit>()
            CompletedListEditScreen(
                onNavigateToCurrentPurchasesListScreen = { navController.navigate(route = CurrentPurchasesListRoute) },
                onBackPressed = { navController.popBackStack() },
                args = args,
            )
        }
    }
}

@Serializable
data class HorizontalPagerRoute(
    val targetPage: Int = 0,
)

@Serializable
object CurrentPurchasesListRoute

@Serializable
object CompletedPurchasesListRoute

@Serializable
object NewListRoute

@Serializable
data class CurrentListEditRoute(
    val id: String,
)

@Serializable
data class CompletedListEdit(
    val id: String,
)
