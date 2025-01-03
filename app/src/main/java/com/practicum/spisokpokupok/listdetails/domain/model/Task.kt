package com.practicum.spisokpokupok.listdetails.domain.model

data class Task(
    val goodName: String = "",
    val isCompleted: Boolean = false,
    val id: String,
    val quantity: Int,
    val quantityType: QuantityType,
    val position: Int,
) {
    val nameForList: String
        get() = goodName
    val isActive
        get() = !isCompleted
}

enum class QuantityType(
    val abbreviation: String,
) {
    KILOGRAM("кг"),
    LITRE("л"),
    PACK("уп"),
    PIECE("шт"),
    UNKNOWN(""),
}
