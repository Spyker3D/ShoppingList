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

data class LIstEditUIState(
    val title: String = "",
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
        private val _bottomSheetState = MutableStateFlow(BottomSheetState())
        private val _isLoading = MutableStateFlow(false)
        private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
        private val _allItemsChecked: MutableStateFlow<Boolean> = MutableStateFlow(false)

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
                _allItemsChecked,
            ) { isLoading, userMessage, tasksAsync, bottomSheetState, allItemsChecked ->
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
                            allItemsChecked = checkAllItemsAreChecked(),
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

        private fun checkAllItemsAreChecked(): Boolean {
            uiState.value.items.forEach {
                if (!it.isCompleted) return false
            }
            return true
        }

        fun consumeAction(action: ListEditAction) {
            when (action) {
                ListEditAction.OnAddNewProduct -> addNewItem()
                is ListEditAction.OnCheckClick ->
                    changeItemStatus(action.taskId)

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
                is ListEditAction.OnTaskClick -> showBottomSheet(action.id)
                is ListEditAction.OnTaskNameChange -> changeTaskName(action.index, action.title)
                ListEditAction.OnDeleteCompletedTasks -> deleteCompletedTasks()
                ListEditAction.CompleteList -> moveListToCompletedLists()
            }
        }

        private fun moveListToCompletedLists() {
            viewModelScope.launch {
                completeListUseCase(listId)
            }
        }

        private fun changeItemStatus(taskId: String) {
            viewModelScope.launch {
                changeItemStatusUseCase(taskId)
                updateItemsPosition()
            }
        }

        private suspend fun updateItemsPosition() {
            uiState.value.items.forEachIndexed { index, task ->
                if (task.isCompleted) return@forEachIndexed
                if (task.position != index) {
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

        private fun showBottomSheet(id: String) {
            val task = uiState.value.items.find { it.id == id }
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
            if (_allItemsChecked.value) {
                viewModelScope.launch {
                    uiState.value.items.forEach {
                        if (it.isCompleted) {
                            changeItemStatusUseCase(it.id)
                        }
                    }
                }
            } else {
                viewModelScope.launch {
                    uiState.value.items.forEach {
                        if (!it.isCompleted) {
                            changeItemStatusUseCase(it.id)
                        }
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
