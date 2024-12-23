package com.practicum.spisokpokupok.listdetails.presentation.newlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.AddNewItemBottomSheet
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.TaskElement
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.TitleEditableTextField
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme

@Composable
fun NewListScreen(
    onNavigateToCurrentLists: () -> Unit,
    onBackPressed: () -> Unit,
    action: (NewListAction) -> Unit,
    state: NewListUIState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            NewListTopBar(
                onBackPressed = onBackPressed,
                title = state.titleState.titleOnTop,
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
                onQuantityTypeChange = { position, quantityType ->
                    action(NewListAction.OnQuantityTypeChange(position, quantityType))
                },
                onSaveTask = {
                    action(NewListAction.OnSaveTask(it))
                },
                bottomButtonTitle = "Сохранить список",
                isConfirmButtonActive = state.isConfirmButtonActive,
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
            NewListTitle(
                state = state,
                action = action,
                modifier = modifier,
            )
            LazyColumn(
                modifier =
                    modifier
                        .padding(top = 10.dp)
                        .fillMaxHeight(),
            ) {
                items(state.productItems.size) { index ->
                    val item = state.productItems[index]
                    TaskElement(
                        name = item.label.ifBlank { item.name },
                        quantity = item.quantity.toString(),
                        quantityType = item.quantityType,
                        isRedacted = item.isNameRedacted,
                        onElementClick = {
                            action(NewListAction.OnTaskClick(index))
                        },
                        onValueChange = {
                            action(NewListAction.OnTaskNameChange(index, it))
                        },
                        modifier = modifier.height(76.dp).fillMaxWidth().animateItem(),
                        onClearClick = {
                            action(NewListAction.OnClearTaskNameClick(index))
                        },
                        isError = item.isNameError,
                        errorMesage = item.errorMessage,
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
fun NewListTitle(
    action: (NewListAction) -> Unit,
    state: NewListUIState,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            val focusManager = LocalFocusManager.current
            TitleEditableTextField(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            action(NewListAction.OnTitleClick)
                        },
                value = state.titleState.title,
                onValueChange = {
                    action(NewListAction.OnTitleChange(it))
                },
                label = "Название",
                topLabel = "Введите название списка",
                isRedacted = state.titleState.isRedacted,
                isError = state.titleState.isError,
                errorMessage = state.titleState.errorMessage,
                keyboardActions =
                    KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            action(NewListAction.SaveTitle)
                        },
                    ),
                onClearClick = { action(NewListAction.OnDeleteTitleClick) },
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
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
    onQuantityTypeChange: (Int, QuantityType) -> Unit,
    onSaveTask: (Int) -> Unit,
    isConfirmButtonActive: Boolean,
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
                onQuantityTypeChange = { onQuantityTypeChange(position, it) },
            )
        }
        Spacer(
            modifier = Modifier.padding(vertical = 32.dp),
        )
        Button(
            enabled = if (bottomSheetIsVisible) true else isConfirmButtonActive,
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
                    onSaveTask(position)
                } else {
                    onBottomButtonClick()
                }
            },
        ) {
            if (bottomSheetIsVisible) {
                Text(
                    text = "Готово",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                )
            } else {
                Text(
                    text = bottomButtonTitle,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun NewListTopBar(
    modifier: Modifier = Modifier.height(76.dp).padding(top = 16.dp),
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

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
private fun NewListScreenPreview() {
    ToDoListTheme {
        NewListScreen(
            onNavigateToCurrentLists = {},
            onBackPressed = {},
            action = {},
            state =
                NewListUIState(
                    productItems =
                        listOf(
                            NewListItemUiState(),
                            NewListItemUiState(),
                        ),
                    isConfirmButtonActive = true,
                ),
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
