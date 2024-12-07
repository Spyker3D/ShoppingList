package com.practicum.buyinglist.domain.model

data class ShoppingList(
    val title: String = "",
    val isCompleted: Boolean = false,
    val isFavorite: Boolean = false,
    val id: String,
) {
    val titleForList
        get() = title
    val isActive
        get() = !isCompleted
}
