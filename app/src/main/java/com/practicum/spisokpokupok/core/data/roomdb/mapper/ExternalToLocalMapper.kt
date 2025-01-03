package com.practicum.spisokpokupok.core.data.roomdb.mapper

import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingList
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTask
import com.practicum.spisokpokupok.listdetails.domain.model.Good
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList

fun Good.toLocal() =
    LocalGood(
        id = id.toInt(),
        name = name,
    )

fun Task.toLocal(
    localGoodId: String,
    shoppingListId: String,
) = LocalShoppingTask(
    id = id,
    goodId = localGoodId.toInt(),
    isCompleted = isCompleted,
    shoppingListId = shoppingListId,
    quantity = quantity,
    quantityType = quantityTypeToString(quantityType),
    position = position,
)

fun ShoppingList.toLocal() =
    LocalShoppingList(
        id = id,
        name = name,
    )

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

const val KILOGRAM = "KILOGRAM"
const val LITRE = "LITRE"
const val PACK = "PACK"
const val PIECE = "PIECE"
const val UNKNOWN = "UNKNOWN"
