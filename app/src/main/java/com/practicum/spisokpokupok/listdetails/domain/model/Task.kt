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

enum class QuantityType {
    KILOGRAM,
    LITRE,
    PACK,
    PIECE,
    UNKNOWN,
}

fun quantityTypeFromString(quantityType: String): QuantityType =
    when (quantityType) {
        KILOGRAM -> QuantityType.KILOGRAM
        LITRE -> QuantityType.LITRE
        PACK -> QuantityType.PACK
        PIECE -> QuantityType.PIECE
        else -> QuantityType.UNKNOWN
    }

fun quantityTypeToString(quantityType: QuantityType): String =
    when (quantityType) {
        QuantityType.KILOGRAM -> {
            KILOGRAM
        }

        QuantityType.LITRE -> LITRE
        QuantityType.PACK -> PACK
        QuantityType.PIECE -> PIECE
        QuantityType.UNKNOWN -> UNKNOWN
    }

private const val KILOGRAM = "KILOGRAM"
private const val LITRE = "LITRE"
private const val PACK = "PACK"
private const val PIECE = "PIECE"
private const val UNKNOWN = "UNKNOWN"
