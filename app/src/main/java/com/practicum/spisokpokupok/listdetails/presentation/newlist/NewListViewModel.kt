package com.practicum.spisokpokupok.listdetails.presentation.newlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class NewListUIState(
    val title: String = "Новый список",
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val productItems: List<NewListItemUiState> = emptyList(),
)

data class BottomSheetState(
    val isVisible: Boolean = false,
    val quantity: Int = 1,
    val quantityType: String = "уп",
)

data class NewListItemUiState(
    val name: String = "",
    val quantity: String = "",
    val quantityType: String = "",
    val isNameError: Boolean = false,
    val isNameRedacted: Boolean = false,
)

class NewListViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            NewListUIState(),
        )
    val uiState: StateFlow<NewListUIState> =
        _uiState
            .asStateFlow()
            .onStart {
                _uiState.update {
                    it.copy(
                        productItems =
                            mutableListOf(
                                NewListItemUiState(
                                    name = "Продукт 1",
                                    quantity = "1",
                                    quantityType = "шт",
                                ),
                                NewListItemUiState(
                                    name = "Продукт 2",
                                    quantity = "3",
                                    quantityType = "кг",
                                ),
                            ),
                    )
                }
            }.stateIn(
                initialValue = NewListUIState(),
                scope = viewModelScope,
                started =
                    SharingStarted
                        .WhileSubscribed(5000),
            )

    fun consumeAction(action: NewListAction) {
        when (action) {
            NewListAction.OnTitleClick -> {
                showBottomSheet()
            }

            NewListAction.OnAddNewProduct -> addNewItem()
            is NewListAction.OnTaskClick -> redactItem(action.index)
            is NewListAction.OnTitleChange -> onTitleChange(action.title)
        }
    }

    private fun showBottomSheet() {
        _uiState.update {
            it.copy(
                bottomSheetState =
                    it.bottomSheetState.copy(
                        isVisible = true,
                    ),
            )
        }
    }

    private fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    private fun redactItem(index: Int) {
        _uiState.update {
            val productItems = it.productItems.toMutableList()
            productItems[index] =
                productItems[index].copy(
                    isNameRedacted = true,
                )
            it.copy(
                bottomSheetState =
                    it.bottomSheetState.copy(
                        isVisible = true,
                    ),
                productItems = productItems,
            )
        }
    }

    private fun addNewItem() {
        _uiState.update {
            val productItems = it.productItems.toMutableList()
            productItems.add(NewListItemUiState())
            it.copy(
                productItems = productItems,
            )
        }
    }

    private fun showBottomSheet(show: Boolean) {
        _uiState.update {
            it.copy(
                bottomSheetState =
                    it.bottomSheetState.copy(
                        isVisible = show,
                    ),
            )
        }
    }
}
