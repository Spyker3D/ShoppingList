package com.practicum.spisokpokupok.listdetails.presentation.newlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.usecases.CreateListUseCase
import com.practicum.spisokpokupok.listdetails.domain.usecases.CreateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewListUIState(
    val titleState: TitleState = TitleState(),
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val productItems: List<NewListItemUiState> = emptyList(),
)

data class BottomSheetState(
    val index: Int = 0,
    val isVisible: Boolean = false,
    val quantity: Int = 1,
    val quantityType: QuantityType = QuantityType.UNKNOWN,
)

data class NewListItemUiState(
    val name: String = "",
    val quantity: Int = 1,
    val quantityType: QuantityType = QuantityType.UNKNOWN,
    val isNameError: Boolean = false,
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
    ) : ViewModel() {
        private val _bottomSheetState = MutableStateFlow(BottomSheetState())
        private val _title = MutableStateFlow(TitleState())
        private val _productItems =
            MutableStateFlow(
                listOf(
                    NewListItemUiState(),
                    NewListItemUiState(),
                ),
            )

        val uiState: StateFlow<NewListUIState> =
            combine(
                _bottomSheetState,
                _title,
                _productItems,
            ) {
                bottomSheetState,
                title,
                productItems,
                ->
                NewListUIState(
                    titleState = title,
                    bottomSheetState = bottomSheetState,
                    productItems =
                        productItems.mapIndexed { index, state ->
                            state.copy(
                                name = "Продукт ${index + 1}",
                                isNameRedacted = false,
                                quantityType = QuantityType.KILOGRAM,
                            )
                        },
                )
            }.stateIn(
                initialValue =
                    NewListUIState(),
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
                is NewListAction.OnTitleChange -> changeTitle(action.title)
                is NewListAction.OnDecreaseClick -> decreaseQuantity(action.position)
                is NewListAction.OnIncreaseClick -> encreaseQuantity(action.position)
                is NewListAction.OnQuantityTypeChange -> updateQuantityType(action.quantityType)
                NewListAction.OnSaveList -> saveList()
                NewListAction.OnSaveTask -> saveTask()
                is NewListAction.OnTaskNameChange -> changeTaskName(action.index, action.title)
                NewListAction.OnAcceptTitleClick -> acceptTitle()
                NewListAction.OnDeleteTitleClick -> emptyTitle()
            }
        }

        private fun emptyTitle() {
            _title.update {
                it.copy(
                    title = "",
                    isError = true,
                )
            }
        }

        private fun redactTitle() {
            _title.update {
                it.copy(
                    isRedacted = true,
                    errorMessage = if (it.title.isEmpty()) "Вы не ввели название" else "",
                    isError = it.title.isEmpty(),
                )
            }
            _bottomSheetState.update {
                it.copy(
                    isVisible = false,
                )
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
            title: String,
        ) {
            _productItems.update {
                it.mapIndexed { position, item ->
                    if (position == index) {
                        item.copy(
                            name = title,
                        )
                    } else {
                        item
                    }
                }
            }
        }

        private fun updateQuantityType(quantityType: QuantityType) {
            _productItems.update {
                it.mapIndexed {
                    position,
                    item,
                    ->
                    if (position == _bottomSheetState.value.index) {
                        item.copy(
                            quantityType = quantityType,
                        )
                    } else {
                        item
                    }
                }
            }
            _bottomSheetState.update {
                it.copy(
                    quantityType = quantityType,
                )
            }
        }

        private fun saveTask() {
            closeBottomSheet()
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
                it.copy(
                    quantity = it.quantity - 1,
                )
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
            _title.update {
                it.copy(
                    title = title,
                )
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
                    errorMessage = if (it.title.isEmpty()) "Вы не ввели название" else "",
                    isError = it.title.isEmpty(),
                )
            }
            _productItems.update {
                val productItems =
                    it.toMutableList().mapIndexed { position, item ->
                        if (position == index) {
                            item.copy(
                                isNameRedacted = true,
                            )
                        } else {
                            item.copy(
                                isNameRedacted = false,
                            )
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
                it +
                    NewListItemUiState(
                        name = "",
                        quantity = 1,
                        quantityType = QuantityType.PACK,
                    )
            }
        }

        private fun saveList() {
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
        }
    }
