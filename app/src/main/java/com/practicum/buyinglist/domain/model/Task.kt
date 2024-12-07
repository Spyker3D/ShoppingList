package com.practicum.buyinglist.domain.model

data class Task(
    val name: String = "",
    val isCompleted: Boolean = false,
    val id: String,
    val quantity: Int,
    val quantityType: String,
) {
    val nameForList: String
        get() = name
    val isActive
        get() = !isCompleted
}
