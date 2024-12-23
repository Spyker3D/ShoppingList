package com.practicum.spisokpokupok.listdetails.presentation.newlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.buyinglist.R
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.usecases.CreateListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.CreateTaskUseCase
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.usecases.GetActualListsUseCase
import com.practicum.spisokpokupok.utils.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewListUIState(
    val lists: List<ShoppingList> = emptyList(),
    val loading: Boolean = false,
    val userMessage: Int? = null,
    val titleState: TitleState = TitleState(),
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val productItems: List<NewListItemUiState> = emptyList(),
    val isConfirmButtonActive: Boolean = false,
)

data class BottomSheetState(
    val index: Int = 0,
    val isVisible: Boolean = false,
    val quantity: Int = 1,
    val quantityType: QuantityType = QuantityType.UNKNOWN,
)

data class NewListItemUiState(
    val index: Int,
    val name: String = "",
    val label: String = "",
    val quantity: Int = 1,
    val quantityType: QuantityType = QuantityType.UNKNOWN,
    val isNameError: Boolean = false,
    val errorMessage: String = "",
    val isNameRedacted: Boolean = false,
)

data class TitleState(
    val titleOnTop: String = "",
    val title: String = "",
    val isError: Boolean = false,
    val isRedacted: Boolean = false,
    val errorMessage: String = "",
)

@HiltViewModel
class NewListViewModel
    @Inject
    constructor(
        private val createTaskUseCase: CreateTaskUseCase,
        private val createListUseCase: CreateListUseCase,
        private val getActualListsUseCase: GetActualListsUseCase,
    ) : ViewModel() {
        private var currentIndex = 0
        private val actualListsStream = getActualListsUseCase()
        private val _bottomSheetState = MutableStateFlow(BottomSheetState())
        private val _title = MutableStateFlow(TitleState())
        private val _productItems =
            MutableStateFlow(
                listOf<NewListItemUiState>(),
            )
        private val _listsAcync =
            actualListsStream
                .map {
                    Async.Success(it)
                }.catch<Async<List<ShoppingList>>> {
                    emit(Async.Error(R.string.loading_error))
                }
        val uiState: StateFlow<NewListUIState> =
            combine(
                _bottomSheetState,
                _title,
                _productItems,
                _listsAcync,
            ) {
                bottomSheetState,
                title,
                productItems,
                listsAsync,
                ->
                when (listsAsync) {
                    Async.Loading -> {
                        NewListUIState(loading = true)
                    }

                    is Async.Error -> {
                        NewListUIState(userMessage = listsAsync.errorMessage)
                    }

                    is Async.Success -> {
                        NewListUIState(
                            lists = listsAsync.data,
                            loading = false,
                            titleState = title,
                            bottomSheetState = bottomSheetState,
                            productItems = productItems,
                            isConfirmButtonActive =
                                !title.isError
                                    and productItems.all { !it.isNameError }
                                    and title.title.isNotBlank() and
                                    productItems.any { it.name.isNotBlank() },
                        )
                    }
                }
            }.onStart {
                _title.update {
                    it.copy(
                        titleOnTop = "Новый список",
                    )
                }
                _productItems.update {
                    val newList = it.toMutableList()
                    newList.add(
                        NewListItemUiState(
                            index = currentIndex,
                        ),
                    )
                    currentIndex++
                    newList.add(
                        NewListItemUiState(
                            index = currentIndex,
                        ),
                    )
                    currentIndex++
                    it.mapIndexed { index, item ->
                        item.copy(
                            label = "Продукт ${index + 1}",
                            isNameRedacted = false,
                            isNameError = false,
                            quantityType = QuantityType.KILOGRAM,
                        )
                    }
                }
            }.stateIn(
                initialValue =
                    NewListUIState(
                        isConfirmButtonActive = false,
                    ),
                scope = viewModelScope,
                started =
                    SharingStarted
                        .WhileSubscribed(5000),
            )

        fun consumeAction(action: NewListAction) {
            when (action) {
                NewListAction.OnTitleClick -> redactTitle()
                NewListAction.OnAddNewProduct -> addNewItem()
                is NewListAction.OnTaskClick -> redactItem(action.index)
                is NewListAction.OnTitleChange -> {
                    changeTitle(action.title)
                }

                is NewListAction.OnDecreaseClick -> decreaseQuantity(action.position)
                is NewListAction.OnIncreaseClick -> encreaseQuantity(action.position)
                is NewListAction.OnQuantityTypeChange ->
                    updateQuantityType(
                        action.position,
                        action.quantityType,
                    )

                NewListAction.OnSaveList -> saveList()
                is NewListAction.OnSaveTask -> {
                    saveTask(action.index)
                }

                is NewListAction.OnTaskNameChange -> {
                    changeTaskName(action.index, action.title)
                }

                NewListAction.OnAcceptTitleClick -> acceptTitle()
                NewListAction.OnDeleteTitleClick -> emptyTitle()
                NewListAction.SaveTitle -> saveTitle()
                is NewListAction.OnClearTaskNameClick -> clearTaskName(action.index)
                is NewListAction.OnDeleteTaskClick -> deleteTask(action.itemIndex)
            }
        }

        private fun deleteTask(itemIndex: Int) {
            if ((itemIndex == _bottomSheetState.value.index) or _productItems.value.isEmpty()) {
                _bottomSheetState.update {
                    it.copy(
                        isVisible = false,
                    )
                }
            }
            _productItems.update {
                val productItems = it.toMutableList()
                productItems.removeAt(itemIndex)
                productItems
            }
        }

        private fun clearTaskName(index: Int) {
            _productItems.update {
                val productItems = it.toMutableList()
                productItems[index] =
                    productItems[index].copy(
                        name = "",
                        isNameRedacted = true,
                        isNameError = true,
                        errorMessage = "Вы не ввели название",
                    )
                productItems
            }
        }

        private fun saveTitle() {
            if (!uiState.value.titleState.isError) {
                _title.update {
                    if (it.title.isEmpty()) {
                        it.copy(
                            isError = true,
                            errorMessage = "Вы не ввели название",
                        )
                    } else {
                        it.copy(
                            titleOnTop = it.title,
                        )
                    }
                }
            }
        }

        private fun emptyTitle() {
            _title.update {
                it.copy(
                    title = "",
                    isError = true,
                    errorMessage = "Вы не ввели название",
                    isRedacted = true,
                    titleOnTop = "Новый список",
                )
            }
        }

        private fun redactTitle() {
            _title.update {
                it.copy(
                    isRedacted = true,
                )
            }
            _bottomSheetState.update {
                it.copy(
                    isVisible = false,
                )
            }
            _productItems.update {
                it.map {
                    it.copy(
                        isNameRedacted = false,
                    )
                }
            }
        }

        private fun acceptTitle() {
            _title.update {
                it.copy(
                    isRedacted = false,
                    isError = false,
                )
            }
        }

        private fun changeTaskName(
            index: Int,
            name: String,
        ) {
            if (name.length > MAX_TASK_LENGTH) {
                _productItems.update {
                    val productItems = it.toMutableList()
                    productItems[index] =
                        productItems[index].copy(
                            name = name.substring(0, MAX_TASK_LENGTH),
                        )
                    productItems
                }
            } else if (name.isBlank()) {
                _productItems.update {
                    val productItems = it.toMutableList()
                    productItems[index] =
                        productItems[index].copy(
                            name = "",
                            isNameError = true,
                            isNameRedacted = true,
                            errorMessage = "Вы не ввели название",
                        )
                    productItems
                }
            } else {
                _productItems.update {
                    val productItems = it.toMutableList()
                    productItems[index] =
                        productItems[index].copy(
                            name = name,
                            isNameError = false,
                        )
                    productItems
                }
            }
        }

        private fun updateQuantityType(
            position: Int,
            quantityType: QuantityType,
        ) {
            _productItems.update {
                val productItems = it.toMutableList()
                productItems[position] =
                    productItems[position].copy(
                        quantityType = quantityType,
                    )
                productItems
            }
            _bottomSheetState.update {
                it.copy(
                    quantityType = quantityType,
                )
            }
        }

        private fun saveTask(index: Int) {
            closeBottomSheet()
            if (_productItems.value[index].name.isEmpty()) {
                _productItems.update {
                    val productItems = it.toMutableList()
                    productItems[index] =
                        productItems[index].copy(
                            isNameError = true,
                            isNameRedacted = true,
                            errorMessage = "Вы не ввели название",
                        )
                    productItems
                }
            } else {
                _productItems.update {
                    val productItems = it.toMutableList()
                    productItems[index] =
                        productItems[index].copy(
                            isNameRedacted = false,
                            isNameError = false,
                            errorMessage = "",
                        )
                    productItems
                }
            }
        }

        private fun encreaseQuantity(position: Int) {
            _productItems.update {
                val productItems = it.toMutableList()
                productItems[position] =
                    productItems[position].copy(
                        quantity = productItems[position].quantity + 1,
                    )
                productItems
            }
            _bottomSheetState.update {
                it.copy(
                    quantity = it.quantity + 1,
                )
            }
        }

        private fun decreaseQuantity(position: Int) {
            _productItems.update {
                val productItems = it.toMutableList()
                if (productItems[position].quantity > 1) {
                    productItems[position] =
                        productItems[position].copy(
                            quantity = productItems[position].quantity - 1,
                        )
                }
                productItems
            }
            _bottomSheetState.update {
                if (it.quantity > 1) {
                    it.copy(
                        quantity = it.quantity - 1,
                    )
                } else {
                    showBottomSheet()
                    it.copy(
                        quantity = it.quantity,
                    )
                }
            }
        }

        private fun showBottomSheet() {
            _bottomSheetState.update {
                it.copy(
                    isVisible = true,
                )
            }
        }

        private fun changeTitle(title: String) {
            closeBottomSheet()
            if (uiState.value.lists.any {
                    it.name == title
                }
            ) {
                _title.update {
                    it.copy(
                        title = title,
                        isError = true,
                        errorMessage = "Такое название списка уже есть",
                    )
                }
            } else {
                if (title.length > MAX_TITLE_LENGTH) {
                    _title.update {
                        it.copy(
                            title = title.substring(0, MAX_TITLE_LENGTH),
                        )
                    }
                } else {
                    if (title.isEmpty() or title.isBlank()) {
                        _title.update {
                            it.copy(
                                title = "",
                                isError = true,
                                errorMessage = "Вы не ввели название",
                            )
                        }
                    } else {
                        _title.update {
                            it.copy(
                                title = title,
                                isError = false,
                                errorMessage = "",
                            )
                        }
                    }
                }
            }
        }

        private fun closeBottomSheet() {
            _bottomSheetState.update {
                it.copy(
                    isVisible = false,
                )
            }
        }

        private fun redactItem(index: Int) {
            _title.update {
                it.copy(
                    isRedacted = false,
                )
            }
            _productItems.update {
                val productItems =
                    it.toMutableList().mapIndexed { position, item ->
                        if (position == index) {
                            item.copy(
                                label = "",
                                isNameRedacted = true,
                            )
                        } else {
                            if (item.name.isBlank()) {
                                item.copy(
                                    label =
                                        "Продукт ${position + 1}",
                                    isNameRedacted = false,
                                    isNameError = true,
                                    errorMessage = "Вы не ввели название",
                                )
                            } else {
                                item.copy(
                                    label = "",
                                    isNameRedacted = false,
                                )
                            }
                        }
                    }
                productItems
            }
            _bottomSheetState.update {
                it.copy(
                    index = index,
                    isVisible = true,
                    quantity = _productItems.value[index].quantity,
                    quantityType = _productItems.value[index].quantityType,
                )
            }
        }

        private fun addNewItem() {
            _title.update {
                it.copy(
                    isRedacted = false,
                )
            }
            _productItems.update {
                val list =
                    it.map {
                        if (it.name.isBlank()) {
                            it.copy(
                                label =
                                    "Продукт ${_productItems.value.indexOf(it) + 1}",
                                isNameError = true,
                                errorMessage = "Вы не ввели название",
                                isNameRedacted = false,
                            )
                        } else {
                            it.copy(
                                label = "",
                                isNameRedacted = false,
                            )
                        }
                    }
                list +
                    NewListItemUiState(
                        name = "",
                        quantity = 1,
                        quantityType = QuantityType.PACK,
                        isNameError = false,
                        errorMessage = "Вы не ввели название",
                        isNameRedacted = true,
                        index = currentIndex,
                    )
            }
            _bottomSheetState.update {
                it.copy(
                    isVisible = true,
                    index = _productItems.value.lastIndex,
                    quantity = 1,
                    quantityType = QuantityType.PACK,
                )
            }
            currentIndex++
        }

        private fun saveList() {
            if (_title.value.title.isEmpty()) {
                _title.update {
                    it.copy(
                        isError = true,
                        errorMessage = "Вы не ввели название",
                    )
                }
                return
            }
            for (item in _productItems.value) {
                if (item.name.isEmpty()) {
                    val productItems = _productItems.value.toMutableList()
                    productItems[productItems.indexOf(item)] =
                        item.copy(
                            isNameError = true,
                            errorMessage = "Вы не ввели название",
                        )
                    _productItems.update {
                        productItems
                    }
                    return
                }
            }
            viewModelScope.launch {
                val listId =
                    createListUseCase(
                        _title.value.title,
                    )
                _productItems.value.forEachIndexed { index, item ->
                    createTaskUseCase(
                        listId,
                        item.name,
                        item.quantity,
                        item.quantityType,
                        index,
                    )
                }
            }
            _bottomSheetState.update {
                it.copy(
                    isVisible = false,
                )
            }
        }
    }

const val MAX_TITLE_LENGTH = 25
const val MAX_TASK_LENGTH = 20
