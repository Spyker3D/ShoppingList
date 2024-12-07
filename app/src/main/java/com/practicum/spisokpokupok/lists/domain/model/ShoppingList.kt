package com.practicum.spisokpokupok.lists.domain.model

data class ShoppingList(
    val name: String = "",
    val isCompleted: Boolean = false,
    val isFavorite: Boolean = false,
    val id: String,
) {
    val titleForList
        get() = name
    val isActive
        get() = !isCompleted
}
