package com.practicum.spisokpokupok.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.ui.theme.cyan

@Composable
fun NewListScreen(
    modifier: Modifier = Modifier,
    onNavigateToCurrentLists: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Новый список",
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
        )
        Icon(
            modifier = Modifier
                .padding(bottom = 24.dp)
                .size(48.dp)
                .clickable { onNavigateToCurrentLists() },
            painter = painterResource(id = R.drawable.ic_add),
            tint = cyan,
            contentDescription = null,
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun NewListScreenPreview() {
    ToDoListTheme {
        NewListScreen(onNavigateToCurrentLists = {}, onBackPressed = {})
    }
}
