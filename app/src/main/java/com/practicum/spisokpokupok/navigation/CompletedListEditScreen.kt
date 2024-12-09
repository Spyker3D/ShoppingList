package com.practicum.spisokpokupok.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun CompletedListEditScreen(
    modifier: Modifier = Modifier,
    onNavigateToCurrentPurchasesListScreen: () -> Unit,
    onBackPressed: () -> Unit,
    args: CompletedListEdit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Завершенный список №1",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun CompletedListEditScreenPreview() {
    ToDoListTheme {
        CompletedListEditScreen(
            onNavigateToCurrentPurchasesListScreen = {},
            onBackPressed = {},
            args = CompletedListEdit(id = 1214)
        )
    }
}