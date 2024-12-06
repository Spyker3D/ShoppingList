package com.practicum.spisokpokupok.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.compose.ToDoListTheme


@Composable
fun CurrentPurchasesListScreen(
    modifier: Modifier = Modifier,
    onNavigateToNewList: (String) -> Unit,
    onNavigateToCompletedLists: () -> Unit,
    onItemClicked: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Все ваши списки",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun CurrentPurchasesListScreenPreview() {
    ToDoListTheme {
        CurrentPurchasesListScreen(
            onNavigateToNewList = {},
            onNavigateToCompletedLists = {},
            onItemClicked = {}
        )
    }
}