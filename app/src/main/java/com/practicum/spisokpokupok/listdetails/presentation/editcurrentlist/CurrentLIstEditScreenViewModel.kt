package com.practicum.spisokpokupok.listdetails.presentation.editcurrentlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.usecases.ChangeItemStatusUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.CompleteListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.CreateTaskUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.DeleteTaskUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.GetListTitleUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.ObserveTasksUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.UpdateTaskUseCase
import com.practicum.spisokpokupok.listdetails.presentation.newlist.BottomSheetState
import com.practicum.spisokpokupok.navigation.CurrentListEditRoute
import com.practicum.spisokpokupok.utils.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SortType {
    NONE,
    BY_NAME_ASCENDING,
    BY_NAME_DESCENDING,
}

data class LIstEditUIState(
    val title: String = "",
    val sortType: SortType = SortType.NONE,
    val loading: Boolean = false,
    val allItemsChecked: Boolean = false,
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val items: List<TaskUiState> = emptyList(),
    val userMessage: Int? = null,
)

data class TaskUiState(
    val position: Int,
    val id: String = "",
    val name: String = "",
    val isCompleted: Boolean = false,
    val isNameError: Boolean = false,
    val errorNameMessage: String = "",
    val isNameRedacted: Boolean = false,
    val quantity: Int = 1,
    val quantityType: QuantityType = QuantityType.UNKNOWN,
)

@HiltViewModel
class CurrentLIstEditScreenViewModel
    @Inject
    constructor(
        handle: SavedStateHandle,
        val observeTasksUseCase: ObserveTasksUseCase,
        val getListTitleUseCase: GetListTitleUseCase,
        val deleteTaskUseCase: DeleteTaskUseCase,
        val updateTaskUseCase: UpdateTaskUseCase,
        val changeItemStatusUseCase: ChangeItemStatusUseCase,
        val createTaskUseCase: CreateTaskUseCase,
        val completeListUseCase: CompleteListUseCase,
    ) : ViewModel() {
        private val currentListEditRoute = handle.toRoute<CurrentListEditRoute>()
        private val listId = currentListEditRoute.id
        private val itemsStream = observeTasksUseCase(listId)
        private val _sortType = MutableStateFlow(SortType.NONE)
        private val _bottomSheetState = MutableStateFlow(BottomSheetState())
        private val _isLoading = MutableStateFlow(false)
        private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
        private val _tasksAsync =
            itemsStream
                .map {
                    Async.Success(it)
                }.catch<Async<List<Task>>> {
                    emit(Async.Error(R.string.loading_error))
                }

        val uiState: StateFlow<LIstEditUIState> =
            combine(
                _isLoading,
                _userMessage,
                _tasksAsync,
                _bottomSheetState,
                _sortType,
            ) { isLoading, userMessage, tasksAsync, bottomSheetState, sortType ->
                val title =
                    getListTitleUseCase(listId)
                when (tasksAsync) {
                    Async.Loading -> {
                        LIstEditUIState(loading = true)
                    }

                    is Async.Error -> {
                        LIstEditUIState(userMessage = tasksAsync.errorMessage)
                    }

                    is Async.Success -> {
                        LIstEditUIState(
                            sortType = sortType,
                            allItemsChecked =
                                tasksAsync.data.all {
                                    it.isCompleted
                                },
                            title = title,
                            items =
                                tasksAsync.data
                                    .map {
                                        TaskUiState(
                                            id = it.id,
                                            position = it.position,
                                            name = it.goodName,
                                            isCompleted = it.isCompleted,
                                            quantity = it.quantity,
                                            quantityType = it.quantityType,
                                        )
                                    }.sortedBy { it.position }
                                    .sortedBy { it.isCompleted },
                            loading = isLoading,
                            userMessage = userMessage,
                            bottomSheetState = bottomSheetState,
                        )
                    }
                }
            }.stateIn(
                scope = viewModelScope,
                started =
                    kotlinx.coroutines.flow.SharingStarted
                        .WhileSubscribed(5000),
                initialValue =
                    LIstEditUIState(
                        loading = true,
                    ),
            )

        fun consumeAction(action: ListEditAction) {
            when (action) {
                ListEditAction.OnAddNewProduct -> addNewItem()
                is ListEditAction.OnCheckClick -> {
                    changeItemStatus(action.index)
                }

                ListEditAction.OnChooseAllItems -> completeAllItems()
                is ListEditAction.OnDecreaseClick -> decreaseQuantity(action.position)
                is ListEditAction.OnDeleteClick -> deleteItem(action.position)
                is ListEditAction.OnEncreaseClick -> encreaseQuantity(action.position)
                is ListEditAction.OnQuantityTypeChange ->
                    changeQuantityType(
                        action.position,
                        action.quantityType,
                    )

                ListEditAction.OnSaveTask -> closeBottomSheet()
                is ListEditAction.OnTaskClick -> showBottomSheet(action.index)
                is ListEditAction.OnTaskNameChange -> changeTaskName(action.index, action.title)
                ListEditAction.OnDeleteCompletedTasks -> deleteCompletedTasks()
                ListEditAction.CompleteList -> moveListToCompletedLists()
                is ListEditAction.OnClearTaskNameClick -> clearTaskName(action.index)
                ListEditAction.OnSortClick -> sortItemsByAlphabetically()
            }
        }

        private fun sortItemsByAlphabetically() {
            if ((uiState.value.sortType == SortType.BY_NAME_DESCENDING) ||
                uiState.value.sortType == SortType.NONE
            ) {
                val sortedList = uiState.value.items.sortedBy { it.name }
                viewModelScope.launch {
                    sortedList.forEachIndexed { index, task ->
                        updateTaskUseCase(
                            quantity = task.quantity,
                            quantityType = task.quantityType,
                            position = index,
                            taskId = task.id,
                            taskName = task.name,
                            completed = task.isCompleted,
                        )
                    }
                }
                _sortType.update {
                    SortType.BY_NAME_ASCENDING
                }
            } else {
                val sortedList = uiState.value.items.sortedByDescending { it.name }
                viewModelScope.launch {
                    sortedList.forEachIndexed { index, task ->
                        updateTaskUseCase(
                            quantity = task.quantity,
                            quantityType = task.quantityType,
                            position = index,
                            taskId = task.id,
                            taskName = task.name,
                            completed = task.isCompleted,
                        )
                    }
                }
                _sortType.update {
                    SortType.BY_NAME_DESCENDING
                }
            }
        }

        private fun clearTaskName(index: Int) {
            viewModelScope.launch {
                val task = uiState.value.items[index]
                updateTaskUseCase(
                    quantity = task.quantity,
                    quantityType = task.quantityType,
                    position = task.position,
                    taskId = task.id,
                    taskName = "",
                    completed = task.isCompleted,
                )
            }
        }

        private fun moveListToCompletedLists() {
            viewModelScope.launch {
                completeListUseCase(listId)
            }
        }

        private fun changeItemStatus(index: Int) {
            val taskId = uiState.value.items[index].id
            viewModelScope.launch {
                changeItemStatusUseCase(taskId)
            }
        }

        private suspend fun updateItemsPosition() {
            val items = uiState.value.items
            items.forEachIndexed { index, task ->
                if (task.isCompleted) return@forEachIndexed
                updateTaskUseCase(
                    quantity = task.quantity,
                    quantityType = task.quantityType,
                    position = index,
                    taskId = task.id,
                    taskName = task.name,
                    completed = task.isCompleted,
                )
            }
        }

        private fun deleteCompletedTasks() {
            viewModelScope.launch {
                uiState.value.items.forEach {
                    if (it.isCompleted) {
                        deleteTaskUseCase(it.id)
                    }
                }
            }
        }

        private fun changeTaskName(
            index: Int,
            title: String,
        ) {
            viewModelScope.launch {
                val task = uiState.value.items[index]
                updateTaskUseCase(
                    quantity = task.quantity,
                    quantityType = task.quantityType,
                    position = task.position,
                    taskId = task.id,
                    taskName = title,
                    completed = task.isCompleted,
                )
            }
        }

        private fun showBottomSheet(index: Int) {
            val task = uiState.value.items[index]
            _bottomSheetState.update {
                it.copy(
                    isVisible = true,
                    quantity = task?.quantity ?: 1,
                    quantityType = task?.quantityType ?: QuantityType.UNKNOWN,
                    index = task?.position ?: -1,
                )
            }
        }

        private fun closeBottomSheet() {
            _bottomSheetState.update {
                it.copy(
                    isVisible = false,
                    quantity = 1,
                    quantityType = QuantityType.UNKNOWN,
                    index = -1,
                )
            }
        }

        private fun changeQuantityType(
            position: Int,
            quantityType: QuantityType,
        ) {
            viewModelScope.launch {
                val task = uiState.value.items[position]
                updateTaskUseCase(
                    quantity = task.quantity,
                    quantityType = quantityType,
                    position = task.position,
                    taskId = task.id,
                    taskName = task.name,
                    completed = task.isCompleted,
                )
                _bottomSheetState.update {
                    it.copy(
                        quantityType = quantityType,
                    )
                }
            }
        }

        private fun encreaseQuantity(position: Int) {
            viewModelScope.launch {
                val task = uiState.value.items[position]
                val newQuantity = task.quantity + 1
                updateTaskUseCase(
                    quantity = newQuantity,
                    quantityType = task.quantityType,
                    position = task.position,
                    taskId = task.id,
                    taskName = task.name,
                    completed = task.isCompleted,
                )
                _bottomSheetState.update {
                    it.copy(
                        quantity = newQuantity,
                    )
                }
            }
        }

        private fun decreaseQuantity(position: Int) {
            viewModelScope.launch {
                val task = uiState.value.items[position]
                if (task.quantity > 1) {
                    val newQuantity = task.quantity - 1
                    updateTaskUseCase(
                        quantity = newQuantity,
                        quantityType = task.quantityType,
                        position = task.position,
                        taskId = task.id,
                        taskName = task.name,
                        completed = task.isCompleted,
                    )
                    _bottomSheetState.update {
                        it.copy(
                            quantity = newQuantity,
                        )
                    }
                }
            }
        }

        private fun completeAllItems() {
            if (uiState.value.items.any {
                    !it.isCompleted
                }
            ) {
                viewModelScope.launch {
                    uiState.value.items.forEach {
                        if (!it.isCompleted) {
                            changeItemStatusUseCase(it.id)
                        }
                    }
                }
            } else {
                viewModelScope.launch {
                    uiState.value.items.forEach {
                        changeItemStatusUseCase(it.id)
                    }
                }
            }
        }

        private fun addNewItem() {
            viewModelScope.launch {
                createTaskUseCase(
                    listId = listId,
                    quantity = 1,
                    quantityType = QuantityType.KILOGRAM,
                    taskName = "",
                    position = uiState.value.items.size,
                )
            }
        }

        private fun deleteItem(position: Int) {
            viewModelScope.launch {
                val id = uiState.value.items[position].id
                deleteTaskUseCase(id)
            }
        }
    }
