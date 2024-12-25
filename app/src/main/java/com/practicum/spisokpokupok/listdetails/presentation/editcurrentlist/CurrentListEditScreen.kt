package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practicum.buyinglist.R
import com.practicum.buyinglist.R.drawable.ic_arrow_right
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.presentation.newlist.AddItem
import com.practicum.spisokpokupok.listdetails.presentation.newlist.BottomBar
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.utils.ActionIcon
import com.practicum.spisokpokupok.utils.SwipeState
import com.practicum.spisokpokupok.utils.SwipeableRightItem
import com.practicum.spisokpokupok.utils.TaskDetailTopAppBar
import kotlinx.coroutines.launch

@Composable
fun CurrentListEditScreen(
    onBackPressed: () -> Unit,
    onNavigateToSuccessScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: LIstEditUIState,
    action: (ListEditAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskDetailTopAppBar(
                modifier = modifier.padding(top = 16.dp),
                onBack = onBackPressed,
                onSort = { action(ListEditAction.OnSortClick) },
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
                        onNavigateToSuccessScreen(state.title)
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
            modifier =
                modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
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
                onDeleteClick = { action(ListEditAction.OnDeleteClick(it)) },
                onTaskNameChange = { index, newName ->
                    action(ListEditAction.OnTaskNameChange(index, newName))
                },
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
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        Switch(
            modifier = modifier.padding(horizontal = 16.dp),
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
    onDeleteClick: (Int) -> Unit,
    onTaskNameChange: (Int, String) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin)),
    ) {
        LazyColumn {
            items(tasks, key = { it.id }) { task ->
                val swipeState = remember { SwipeState() }
                val scope = rememberCoroutineScope()
                SwipeableRightItem(
                    modifier = Modifier.animateItem(),
                    swipeState = swipeState,
                    numberOfIcons = 1,
                    actions = {
                        Spacer(modifier = Modifier.padding(start = 4.dp))
                        ActionIcon(
                            onClick = {
                                onDeleteClick(tasks.indexOf(task))
                                scope.launch {
                                    swipeState.hide()
                                }
                            },
                            backgroundColor = MaterialTheme.colorScheme.onError,
                            icon = painterResource(id = R.drawable.ic_delete_blue),
                            modifier = Modifier.fillMaxHeight(),
                        )
                    },
                    swipeableContent = {
                        TaskItem(
                            task = task,
                            onCheckedChange = { onTaskCheckedChange(tasks.indexOf(task)) },
                            onTaskClick = {
                                onTaskClick(tasks.indexOf(task))
                            },
                            onClearClick = {
                                onClearClick(tasks.indexOf(task))
                            },
                            onValueChange = { onTaskNameChange(tasks.indexOf(task), it) },
                        )
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
    onValueChange: (String) -> Unit,
    task: TaskUiState,
    onCheckedChange: () -> Unit,
    onClearClick: () -> Unit,
    onTaskClick: () -> Unit,
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
            TaskNameTextField(task, onValueChange, isRedacted, onTaskClick)
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
                        task.quantityType.abbreviation,
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

@Composable
private fun TaskNameTextField(
    task: TaskUiState,
    onValueChange: (String) -> Unit,
    isRedacted: Boolean,
    onTaskClick: () -> Unit,
) {
    val textState = remember { mutableStateOf(TextFieldValue(task.name)) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    DisposableEffect(Unit) {
        onDispose {
            focusManager.clearFocus()
        }
    }
    BasicTextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            onValueChange(it.text)
        },
        modifier =
            Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.horizontal_margin),
                ).focusRequester(focusRequester)
                .focusTarget()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                        onTaskClick()
                    }
                }.clickable {
                    focusRequester.requestFocus()
                },
        singleLine = true,
        maxLines = 1,
        textStyle =
            TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                textDecoration =
                    if (isRedacted) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    },
            ),
    )
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
                onDeleteClick = {},
                onTaskNameChange = { _, _ -> },
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
                onDeleteClick = {},
                onTaskNameChange = { _, _ -> },
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
                onValueChange = {},
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
                onValueChange = {},
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
                onNavigateToSuccessScreen = {},
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
