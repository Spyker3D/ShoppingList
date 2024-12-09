package com.practicum.spisokpokupok.lists.domain.model

data class PurchaseList(
    val id: Int,
    val name: String,
    val isCompleted: Boolean = false,
    val isAttached: Boolean = false
)