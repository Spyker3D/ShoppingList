package com.practicum.spisokpokupok.listdetails.presentation.newlist

import androidx.lifecycle.ViewModel

data class NewListUIState(
    val title: String = "",
    val productItems: List<ProductItemUiState>,
)

data class ProductItemUiState(
    val name: String = "",
    val quantity: String = "",
    val quantityType: String = "",
    val isChecked: Boolean = false,
)

class NewListViewModel() : ViewModel() {

}

