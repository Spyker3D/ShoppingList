package com.practicum.spisokpokupok.listdetails.presentation.editcompletedlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.presentation.newlist.AddItem
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun CompletedListEditScreen(
    listId: String,
    modifier: Modifier = Modifier,
    onNavigateToCurrentLists: () -> Unit,
    onBackPressed: () -> Unit,
    listOfItems: List<Task>,
    listName: String,
    moveFromCompletedToActualList: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            CompletedListTopBar(
                onBackPressed = onBackPressed,
                title = listName,
            )
        },
        bottomBar = {
            CompletedListBottomBar(
                modifier = modifier,
                onNavigateToCurrentLists = onNavigateToCurrentLists,
                moveFromCompletedToActualList = moveFromCompletedToActualList,
                listId = listId
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding(),
                )
                .padding(start = 16.dp, end = 16.dp, top = 48.dp),
        ) {
            LazyColumn(
                modifier =
                modifier
                    .padding(top = 0.dp)
                    .fillMaxHeight(),
            ) {
                itemsIndexed(items = listOfItems) { _, item ->
                    CompletedListItem(
                        modifier = Modifier,
                        task = item
                    )
                }
                item {
                    AddItem(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .clickable { /* добавить addNewItem */ },
                    )
                }
            }
        }
    }
}

@Composable
private fun CompletedListTopBar(
    modifier: Modifier = Modifier.height(76.dp),
    onBackPressed: () -> Unit,
    title: String,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_back_ic),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.clickable {
                onBackPressed()
            },
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun CompletedListBottomBar(
    modifier: Modifier,
    onNavigateToCurrentLists: () -> Unit,
    moveFromCompletedToActualList: (String) -> Unit,
    listId: String
) {
    BottomAppBar(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        containerColor = MaterialTheme.colorScheme.surface
    )
    {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            onClick = {
                moveFromCompletedToActualList(listId)
                onNavigateToCurrentLists()
            },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Восстановить список",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.roboto_regular))
            )
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun CompletedListEditScreenPreview() {
    ToDoListTheme {
        CompletedListEditScreen(
            listId = "123",
            onNavigateToCurrentLists = {},
            onBackPressed = {},
            listOfItems = listOf(
                Task(
                    goodName = "Молоко",
                    isCompleted = true,
                    quantity = 1,
                    quantityType = QuantityType.LITRE,
                    id = "123",
                    position = 0,
                ),
                Task(
                    goodName = "хлеб",
                    isCompleted = true,
                    quantity = 1,
                    quantityType = QuantityType.PIECE,
                    id = "543",
                    position = 1,
                ),
            ),
            listName = "Корм для собак",
            moveFromCompletedToActualList = { }
        )
    }
}
