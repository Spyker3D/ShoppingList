package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R
import com.practicum.buyinglist.R.drawable.ic_arrow_right
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.presentation.newlist.AddItem
import com.practicum.spisokpokupok.listdetails.presentation.newlist.BottomBar
import com.practicum.spisokpokupok.listdetails.presentation.newlist.component.NewTaskEditableTextField
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.utils.TaskDetailTopAppBar

@Composable
fun CurrentListEditScreen(
    onBackPressed: () -> Unit,
    onNavigateToCompletedList: () -> Unit,
    modifier: Modifier = Modifier,
    state: LIstEditUIState,
    action: (ListEditAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskDetailTopAppBar(
                onBack = onBackPressed,
                onSort = {},
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
                    if (state.allItemsChecked) {
                        action(ListEditAction.CompleteList)
                        onNavigateToCompletedList()
                    } else {
                        action(ListEditAction.OnDeleteCompletedTasks)
                    }
                },
                onDecreeseClick = {
                    action(ListEditAction.OnDecreaseClick(it))
                },
                onEncreeseClick = {
                    action(ListEditAction.OnEncreaseClick(it))
                },
                onQuantityTypeChange = { position, quantityType ->
                    action(ListEditAction.OnQuantityTypeChange(position, quantityType))
                },
                onSaveTask = {
                    action(ListEditAction.OnSaveTask)
                },
                bottomButtonTitle = stringResource(R.string.tasks_are_completed),
                isConfirmButtonActive = state.allItemsChecked,
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
            ) {
                ChooseAllTasks(
                    checked = state.allItemsChecked,
                    onCheckedChange = {
                        action(ListEditAction.OnChooseAllItems)
                    },
                    modifier = modifier.height(56.dp),
                    title = stringResource(R.string.choose_all_tasks),
                )
            }
            TasksContent(
                tasks = state.items,
                onTaskClick = { action(ListEditAction.OnTaskClick(it)) },
                onTaskCheckedChange = { action(ListEditAction.OnCheckClick(it)) },
                modifier =
                    Modifier.padding(
                        horizontal =
                            paddingValues.calculateStartPadding(
                                LocalLayoutDirection.current,
                            ),
                    ),
                loading = state.loading,
                onAddNewProduct = { action(ListEditAction.OnAddNewProduct) },
                onClearClick = { action(ListEditAction.OnClearTaskNameClick(it)) },
            )
        }
    }
}

@Composable
fun ChooseAllTasks(
    checked: Boolean,
    onCheckedChange: () -> Unit,
    modifier: Modifier,
    title: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier,
    ) {
        Switch(
            checked = checked,
            onCheckedChange = {
                onCheckedChange()
            },
            colors =
                SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.surface,
                    checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                ),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun TasksContent(
    loading: Boolean,
    tasks: List<TaskUiState>,
    onTaskClick: (Int) -> Unit,
    onTaskCheckedChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onAddNewProduct: () -> Unit,
    onClearClick: (Int) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin)),
    ) {
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { onTaskCheckedChange(tasks.indexOf(task)) },
                    onTaskClick = { onTaskClick(tasks.indexOf(task)) },
                    onClearClick = {
                        onClearClick(tasks.indexOf(task))
                    },
                )
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
                                onAddNewProduct()
                            },
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    isRedacted: Boolean = false,
    onValueChange: (String) -> Unit = {},
    task: TaskUiState,
    onCheckedChange: () -> Unit,
    onTaskClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                    vertical = dimensionResource(id = R.dimen.list_item_padding),
                ).clickable { onTaskClick() },
    ) {
        Image(
            painter =
                painterResource(
                    if (task.isCompleted) {
                        R.drawable.checkboxe_on
                    } else {
                        R.drawable.checkboxes_off
                    },
                ),
            contentDescription = "checkbox",
            modifier =
                Modifier.clickable {
                    onCheckedChange()
                },
        )
        Column(
            modifier =
                Modifier.weight(1f),
        ) {
            if (isRedacted) {
                NewTaskEditableTextField(
                    value = task.name,
                    onValueChange = { onValueChange(it) },
                    isError = false,
                    errorMessage = "",
                    modifier =
                        Modifier.padding(
                            start = dimensionResource(id = R.dimen.horizontal_margin),
                        ),
                    onClearClick = {
                        onClearClick()
                    },
                )
            } else {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier =
                        Modifier.padding(
                            start = dimensionResource(id = R.dimen.horizontal_margin),
                        ),
                    textDecoration =
                        if (task.isCompleted) {
                            TextDecoration.LineThrough
                        } else {
                            null
                        },
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = task.quantity.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier =
                        Modifier.padding(
                            start = dimensionResource(id = R.dimen.horizontal_margin),
                        ),
                )
                Text(
                    text =
                        when (task.quantityType) {
                            QuantityType.KILOGRAM -> {
                                "кг"
                            }

                            QuantityType.LITRE -> {
                                "л"
                            }

                            QuantityType.PACK -> {
                                "уп"
                            }

                            QuantityType.PIECE -> {
                                "шт"
                            }

                            QuantityType.UNKNOWN -> {
                                ""
                            }
                        },
                    style = MaterialTheme.typography.bodySmall,
                    modifier =
                        Modifier.padding(horizontal = 4.dp),
                )
            }
        }
        Icon(
            painter = painterResource(ic_arrow_right),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.alpha(0.5f),
        )
    }
}

@Preview
@Composable
private fun TasksContentPreview() {
    ToDoListTheme {
        Surface {
            TasksContent(
                loading = false,
                tasks =
                    listOf(
                        TaskUiState(
                            id = "1",
                            name = "Task 1",
                            isCompleted = false,
                            quantity = 2,
                            quantityType = QuantityType.KILOGRAM,
                            position = 1,
                        ),
                        TaskUiState(
                            id = "2",
                            name = "Task 2",
                            isCompleted = true,
                            quantity = 3,
                            quantityType = QuantityType.LITRE,
                            position = 2,
                        ),
                        TaskUiState(
                            id = "3",
                            name = "Task 3",
                            isCompleted = false,
                            quantity = 4,
                            quantityType = QuantityType.PACK,
                            position = 3,
                        ),
                    ),
                onTaskClick = { },
                onTaskCheckedChange = {},
                onAddNewProduct = { },
                onClearClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun TasksContentEmptyPreview() {
    ToDoListTheme {
        Surface {
            TasksContent(
                loading = false,
                tasks = emptyList(),
                onTaskClick = { },
                onTaskCheckedChange = {},
                onAddNewProduct = { },
                onClearClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun TaskItemPreview() {
    ToDoListTheme {
        Surface {
            TaskItem(
                task =
                    TaskUiState(
                        id = "ID",
                        name = "Title",
                        isCompleted = false,
                        quantity = 2,
                        quantityType = QuantityType.KILOGRAM,
                        position = 1,
                    ),
                onCheckedChange = { },
                onTaskClick = { },
                onClearClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun TaskItemCompletedPreview() {
    ToDoListTheme {
        Surface {
            TaskItem(
                task =
                    TaskUiState(
                        id = "ID",
                        name = "Title",
                        isCompleted = true,
                        quantity = 2,
                        quantityType = QuantityType.KILOGRAM,
                        position = 1,
                    ),
                onCheckedChange = { },
                onTaskClick = { },
                onClearClick = {},
            )
        }
    }
}

@Preview(device = "id:Nexus S")
@Composable
private fun CurrentListEditScreenPreview() {
    ToDoListTheme {
        Surface {
            CurrentListEditScreen(
                onBackPressed = { },
                onNavigateToCompletedList = { },
                action = { },
                state =
                    LIstEditUIState(
                        title = "Title",
                        items =
                            listOf(
                                TaskUiState(
                                    id = "1",
                                    name = "Task 1",
                                    isCompleted = false,
                                    quantity = 2,
                                    quantityType = QuantityType.KILOGRAM,
                                    position = 1,
                                ),
                                TaskUiState(
                                    id = "2",
                                    name = "Task 2",
                                    isCompleted = true,
                                    quantity = 3,
                                    quantityType = QuantityType.LITRE,
                                    position = 2,
                                ),
                                TaskUiState(
                                    id = "3",
                                    name = "Task 3",
                                    isCompleted = false,
                                    quantity = 4,
                                    quantityType = QuantityType.PACK,
                                    position = 3,
                                ),
                            ),
                    ),
            )
        }
    }
}
