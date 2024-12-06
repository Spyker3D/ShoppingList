package com.practicum.buyinglist.domain.model

data class ShoppingList(
    val title: String,
    val isCompleted: Boolean,
    val dateTime: String,
    val id: String,
) {
    val titleForList
        get() = title
    val isActive
        get() = isCompleted
}
