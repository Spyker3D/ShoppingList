package com.practicum.spisokpokupok.lists.presentation.currentlists

import com.practicum.spisokpokupok.lists.presentation.model.PurchaseListUi
import kotlinx.coroutines.flow.StateFlow

data class MviState(
    val isLoading: Boolean = false,
    val shoppingList: List<PurchaseListUi>? = null,
)