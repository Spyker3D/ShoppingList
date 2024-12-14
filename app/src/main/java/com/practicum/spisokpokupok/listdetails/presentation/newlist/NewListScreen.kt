package com.practicum.spisokpokupok.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.presentation.newlist.NewListViewModel
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.AddNewItemBottomSheet
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.EditableTextField
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.TaskElement
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun NewListScreen(
    modifier: Modifier = Modifier,
    onNavigateToCurrentLists: () -> Unit,
    onBackPressed: () -> Unit,
    viewModel: NewListViewModel = hiltViewModel(),
) {
    val newListUiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            NewListTopBar(
                onNavigateToCurrentLists = onNavigateToCurrentLists,
                onBackPressed = onBackPressed,
                title = newListUiState.title,
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
                    ).padding(horizontal = 16.dp),
        ) {
            TitleTextField(
                modifier = modifier,
                value = newListUiState.title,
                onValueChange = { title ->
                    viewModel.onTitleChange(title)
                },
            )

            Spacer(
                modifier =
                    Modifier
                        .padding(bottom = 20.dp),
            )
            LazyColumn(
                modifier = modifier.padding(top = 10.dp),
            ) {
                items(newListUiState.productItems.size) { index ->
                    val item = newListUiState.productItems[index]

                    if (item.isNameRedacted) {
                        TitleTextField(
                            modifier = modifier,
                            value = item.name,
                            onValueChange = { title ->
                                viewModel.onTitleChange(title)
                            },
                        )
                    } else {
                        TaskElement(
                            name = item.name,
                            onElementClick = { viewModel.redactItem(index) },
                            modifier = Modifier,
                            quantity = item.quantity,
                            quantityType = item.quantityType,
                        )
                    }
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface,
                        thickness = 1.dp,
                    )
                }
                item {
                    AddItem(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .align(
                                    androidx.compose.ui.Alignment.CenterHorizontally,
                                ).clickable {
                                    viewModel.addNewItem()
                                },
                    )
                }
            }
            Spacer(modifier.weight(1f))
            if (newListUiState.bottomSheetState.isVisible) {
                AddNewItemBottomSheet(
                    onApproveClick = {},
                    onDecreeseClick = {},
                    onIncreeseClick = {},
                    counter = newListUiState.bottomSheetState.quantity.toString(),
                )
            }
        }
    }
}

@Composable
fun NewListTopBar(
    modifier: Modifier = Modifier.height(76.dp),
    onNavigateToCurrentLists: () -> Unit,
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
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    EditableTextField(
        modifier = modifier,
        index = 0,
        value = value,
        onValueChange = onValueChange,
    )
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun NewListScreenPreview() {
    ToDoListTheme {
        NewListScreen(
            onNavigateToCurrentLists = {},
            onBackPressed = {},
            viewModel = NewListViewModel(),
        )
    }
}

@Composable
fun AddItem(modifier: Modifier) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plus_ic),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Добавить продукт",
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
