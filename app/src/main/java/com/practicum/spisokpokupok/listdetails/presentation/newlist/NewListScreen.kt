package com.practicum.spisokpokupok.listdetails.presentation.newlist

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.quantityTypeToString
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.AddNewItemBottomSheet
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.EditableTextField
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.TaskElement
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun NewListScreen(
    modifier: Modifier = Modifier,
    onNavigateToCurrentLists: () -> Unit,
    onBackPressed: () -> Unit,
    action: (NewListAction) -> Unit,
    state: NewListUIState,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            NewListTopBar(
                onBackPressed = onBackPressed,
                title = state.title,
            )
        },
        bottomBar = {
            BottomBar(
                bottomSheetIsVisible = state.bottomSheetState.isVisible,
                quantity = state.bottomSheetState.quantity.toString(),
                quantityType = state.bottomSheetState.quantityType,
                modifier = modifier,
                position = state.bottomSheetState.index,
                onBottomButtonClick = {
                    action(NewListAction.OnSaveList)
                    onNavigateToCurrentLists()
                },
                onDecreeseClick = {
                    action(NewListAction.OnDecreaseClick(it))
                },
                onEncreeseClick = {
                    action(NewListAction.OnIncreaseClick(it))
                },
                onQuantityTypeChange = {
                    action(NewListAction.OnQuantityTypeChange(it))
                },
                onSaveTask = {
                    action(NewListAction.OnSaveTask)
                },
                bottomButtonTitle = "Сохранить список",
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
                value = state.title,
                onValueChange = { title ->
                    action(NewListAction.OnTitleChange(title))
                },
            )

            Spacer(
                modifier = Modifier.padding(bottom = 20.dp),
            )
            LazyColumn(
                modifier =
                    modifier
                        .padding(top = 10.dp)
                        .fillMaxHeight(),
            ) {
                items(state.productItems.size) { index ->
                    val item = state.productItems[index]

                    if (item.isNameRedacted) {
                        TitleTextField(
                            modifier = modifier,
                            value = item.name,
                            onValueChange = { title ->
                                action(NewListAction.OnTaskNameChange(index, title))
                            },
                        )
                    } else {
                        TaskElement(
                            name = item.name,
                            onElementClick = {
                                action(
                                    NewListAction.OnTaskClick(
                                        index,
                                    ),
                                )
                            },
                            modifier = Modifier,
                            quantity = item.quantity.toString(),
                            quantityType = quantityTypeToString(item.quantityType),
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
                                    Alignment.CenterHorizontally,
                                ).clickable {
                                    action(NewListAction.OnAddNewProduct)
                                },
                    )
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    position: Int,
    bottomSheetIsVisible: Boolean,
    quantity: String,
    quantityType: QuantityType,
    modifier: Modifier,
    bottomButtonTitle: String,
    onBottomButtonClick: () -> Unit,
    onDecreeseClick: (Int) -> Unit,
    onEncreeseClick: (Int) -> Unit,
    onQuantityTypeChange: (QuantityType) -> Unit,
    onSaveTask: () -> Unit,
) {
    Column(
        modifier =
            modifier.padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp,
            ),
    ) {
        if (bottomSheetIsVisible) {
            AddNewItemBottomSheet(
                quantityType = quantityType,
                modifier = modifier,
                counter = quantity,
                onDecreeseClick = { onDecreeseClick(position) },
                onEncreeseClick = { onEncreeseClick(position) },
                onQuantityTypeChange = onQuantityTypeChange,
            )
        }
        Spacer(
            modifier = Modifier.padding(vertical = 32.dp),
        )
        Button(
            colors =
                ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.secondary,
                ),
            modifier = modifier.fillMaxWidth(),
            onClick = {
                if (bottomSheetIsVisible) {
                    onSaveTask()
                } else {
                    onBottomButtonClick()
                }
            },
        ) {
            if (bottomSheetIsVisible) {
                Text("Готово")
            } else {
                Text(bottomButtonTitle)
            }
        }
    }
}

@Composable
fun NewListTopBar(
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
            tint = MaterialTheme.colorScheme.onSurface,
            modifier =
                Modifier.clickable {
                    onBackPressed()
                },
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
    value: String,
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
            action = {},
            state = NewListUIState(),
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
