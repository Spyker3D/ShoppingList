package com.practicum.spisokpokupok.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.practicum.spisokpokupok.lists.presentation.completedlists.CompletedPurchasesListScreen
import com.practicum.spisokpokupok.lists.presentation.currentlists.CurrentPurchasesListScreen
import com.practicum.spisokpokupok.lists.presentation.HorizontalPagerScreen
import kotlinx.serialization.Serializable


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = HorizontalPager
    ) {
        composable<HorizontalPager> {
            HorizontalPagerScreen(
                onNavigateToNewList = { navController.navigate(route = NewList) },
                onItemCurrentClicked = { id -> navController.navigate(route = CurrentListEdit(id = id))},
                onItemCompletedClicked = { id -> navController.navigate(route = CompletedListEdit(id = id))}
            )
        }

        composable<CurrentPurchasesList> {
            CurrentPurchasesListScreen(
                onNavigateToNewList = { navController.navigate(route = NewList) },
                onItemClicked = { id -> navController.navigate(route = CurrentListEdit(id = id))}
            )
        }

        composable<CompletedPurchasesList> {
            CompletedPurchasesListScreen(
                onNavigateToNewList = { navController.navigate(route = NewList) },
                onItemClicked = { id -> navController.navigate(route = CompletedListEdit(id = id))}
            )
        }

        composable<NewList> {
            NewListScreen(
                onNavigateToCurrentLists = { navController.navigate(route = CurrentPurchasesList)},
                onBackPressed = { navController.popBackStack() }
            )
        }

        composable<CurrentListEdit> {
            val args = it.toRoute<CurrentListEdit>()
            CurrentListEditScreen(
                onNavigateToCompletedList = { navController.navigate(route = CompletedPurchasesList)},
                onBackPressed = { navController.popBackStack() },
                args = args
            )
        }

        composable<CompletedListEdit> {
            val args = it.toRoute<CompletedListEdit>()
            CompletedListEditScreen(
                onNavigateToCurrentPurchasesListScreen = { navController.navigate(route = CurrentPurchasesList) },
                onBackPressed = { navController.popBackStack() },
                args = args
            )
        }

    }
}

@Serializable
object HorizontalPager

@Serializable
object CurrentPurchasesList

@Serializable
object CompletedPurchasesList

@Serializable
object NewList

@Serializable
data class CurrentListEdit(val id: String)

@Serializable
data class CompletedListEdit(val id: String)
