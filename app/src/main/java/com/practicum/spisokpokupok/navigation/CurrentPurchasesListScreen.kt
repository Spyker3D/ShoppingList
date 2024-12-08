package com.practicum.spisokpokupok.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.presentation.currentlists.CurrentListViewModel
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun CurrentPurchasesListScreen(
    modifier: Modifier = Modifier,
    onNavigateToNewList: (String) -> Unit,
    onNavigateToCompletedLists: () -> Unit,
    viewModel: CurrentListViewModel = hiltViewModel(),
    onItemClicked: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        val list =
            ShoppingList(
                id = "123",
                name = "Тестовый список",
                isFavorite = false,
                isCompleted = false,
            )
        val items = viewModel.listStream.collectAsState()
        Text(
            text = "Все ваши списки",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
        )
        LazyColumn(
            modifier = modifier.fillMaxSize().weight(0.8f),
        ) {
            items(
                items.value.size,
                key = { items.value.get(it).id },
            ) {
                Text(
                    text = items.value[it].name,
                    color = MaterialTheme.colorScheme.primary,
                )
                Button(
                    onClick = { onItemClicked(items.value[it].id) },
                ) {
                    Text(text = "Открыть")
                }
                Log.d("TAG", "CurrentPurchasesListScreen: ${items.value[it].name}")
            }
        }
        Button(
            modifier =
            modifier
                .fillMaxSize(
                    fraction = 0.2f,
                ).weight(0.2f),
            onClick = {
                viewModel.addList("Новый список")
                viewModel.fetchLists()
            },
        ) {
            Text(text = "Новый список")
        }
    }
}

    @Preview(
        backgroundColor = 0xFFFFFFFF,
        showBackground = true
    )
    @Composable
    fun CurrentPurchasesListScreenPreview() {
        ToDoListTheme {
            CurrentPurchasesListScreen(
                onNavigateToNewList = {},
                onNavigateToCompletedLists = {},
                onItemClicked = {},
                viewModel = hiltViewModel(),
            )
        }
    }

