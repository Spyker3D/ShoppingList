package com.practicum.spisokpokupok.lists.presentation.model

data class PurchaseListUi(
    val id: String,
    val name: String,
    val isCompleted: Boolean = false,
    val isAttached: Boolean = false,
)