package com.practicum.spisokpokupok.listdetails.presentation.newlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class NewListUIState(
    val title: String = "Новый список",
    val productItems: List<NewListItemUiState> =
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
        _uiState.asStateFlow().stateIn(
            initialValue = NewListUIState(),
            scope = viewModelScope,
            started =
                SharingStarted
                    .WhileSubscribed(5000),
        )

    fun onTitleChange(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun redactItem(index: Int) {
        _uiState.update {
            val productItems = it.productItems.toMutableList()
            productItems[index] =
                productItems[index].copy(
                    isNameRedacted = true,
                )
            it.copy(
                productItems = productItems,
            )
        }
    }

    fun addNewItem() {
        _uiState.update {
            val productItems = it.productItems.toMutableList()
            productItems.add(
                NewListItemUiState(),
            )
            it.copy(
                productItems = productItems,
            )
        }
    }
}
