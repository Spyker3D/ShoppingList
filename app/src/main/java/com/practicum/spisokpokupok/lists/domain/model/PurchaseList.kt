package com.practicum.spisokpokupok.lists.domain.model

data class PurchaseList(
    val id: String,
    val name: String,
    val isCompleted: Boolean = false,
    var isAttached: Boolean = false
)