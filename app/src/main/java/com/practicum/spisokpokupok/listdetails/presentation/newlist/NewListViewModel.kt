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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewListUIState(
    val titleState: TitleState = TitleState(),
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val productItems: List<NewListItemUiState> = emptyList(),
    val isConfirmButtonActive: Boolean,
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
    val errorName: String = "",
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
        private val _isConfirmButtonActive =
            MutableStateFlow(
                false,
            )
        val uiState: StateFlow<NewListUIState> =
            combine(
                _bottomSheetState,
                _title,
                _productItems,
                _isConfirmButtonActive,
            ) {
                bottomSheetState,
                title,
                productItems,
                isConfirmButtonActive,
                ->
                NewListUIState(
                    titleState = title,
                    bottomSheetState = bottomSheetState,
                    productItems = productItems,
                    isConfirmButtonActive = isConfirmButtonActive,
                )
            }.onStart {
                _productItems.update {
                    it.mapIndexed { index, item ->
                        item.copy(
                            name = "Продукт ${index + 1}",
                            isNameRedacted = false,
                            quantityType = QuantityType.KILOGRAM,
                        )
                    }
                }
            }.stateIn(
                initialValue =
                    NewListUIState(
                        isConfirmButtonActive = _isConfirmButtonActive.value,
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
                    checkConfirmButton()
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
                    checkConfirmButton()
                }
                is NewListAction.OnTaskNameChange -> {
                    changeTaskName(action.index, action.title)
                    checkConfirmButton()
                }
                NewListAction.OnAcceptTitleClick -> acceptTitle()
                NewListAction.OnDeleteTitleClick -> emptyTitle()
                NewListAction.SaveTitle -> saveTitle()
                is NewListAction.OnClearTaskNameClick -> clearTaskName(action.index)
            }
        }

        private fun checkConfirmButton() =
            _isConfirmButtonActive.update {
                _productItems.value.any {
                    it.name.isNotEmpty()
                } &&
                    _title.value.titleOnTop.isNotEmpty()
            }

        private fun clearTaskName(index: Int) {
            _productItems.update {
                val productItems = it.toMutableList()
                productItems[index] =
                    productItems[index].copy(
                        name = "",
                        isNameRedacted = true,
                        isNameError = true,
                        errorName = "Вы не ввели название",
                    )
                productItems
            }
        }

        private fun saveTitle() {
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
            name: String,
        ) {
            if (name.isEmpty()) {
                _productItems.update {
                    val productItems = it.toMutableList()
                    productItems[index] =
                        productItems[index].copy(
                            name = "",
                            isNameError = true,
                            errorName = "Вы не ввели название",
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
                            errorName = "Вы не ввели название",
                        )
                    productItems
                }
                return
            }
            _productItems.update {
                val productItems = it.toMutableList()
                productItems[index] =
                    productItems[index].copy(
                        isNameRedacted = false,
                    )
                productItems
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
            _title.update {
                it.copy(
                    title = title,
                    isError = title.isEmpty(),
                    errorMessage = if (title.isEmpty()) "Вы не ввели название" else "",
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
                        isNameRedacted = true,
                    )
            }
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
                            errorName = "Вы не ввели название",
                        )
                    _productItems.update {
                        productItems
                    }
                    return
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
    }
