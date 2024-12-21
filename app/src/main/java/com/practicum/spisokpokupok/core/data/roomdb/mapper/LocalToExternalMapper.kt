package com.practicum.spisokpokupok.core.data.roomdb.mapper

import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalActualShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalCompletedShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTaskWithGood
import com.practicum.spisokpokupok.listdetails.domain.model.Good
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList

fun LocalActualShoppingListWithName.toExternal() =
    ShoppingList(
        id = localShoppingList.id,
        name = localShoppingList.name,
        isFavorite = localActualShoppingList.isFavorite,
        isCompleted = false,
    )

fun List<LocalActualShoppingListWithName>.toExternalList() = map(LocalActualShoppingListWithName::toExternal)

fun LocalShoppingTaskWithGood.toExternal() =
    Task(
        id = localShoppingTask.id,
        goodName = good.name,
        quantity = localShoppingTask.quantity,
        quantityType = localShoppingTask.quantityType.let(::quantityTypeFromString),
        isCompleted = localShoppingTask.isCompleted,
        position = localShoppingTask.position,
    )

fun List<LocalShoppingTaskWithGood>.toExternalTask() = map { it.toExternal() }

fun LocalGood.toExternal() =
    Good(
        id = id.toString(),
        name = name,
    )

fun LocalCompletedShoppingListWithName.toExternal() =
    ShoppingList(
        id = localShoppingList.id,
        name = localShoppingList.name,
        isFavorite = false,
        isCompleted = true,
    )

fun quantityTypeFromString(quantityType: String): QuantityType =
    when (quantityType) {
        KILOGRAM -> QuantityType.KILOGRAM
        LITRE -> QuantityType.LITRE
        PACK -> QuantityType.PACK
        PIECE -> QuantityType.PIECE
        else -> QuantityType.UNKNOWN
    }

fun List<LocalCompletedShoppingListWithName>.toExternalCompletedList() = map(LocalCompletedShoppingListWithName::toExternal)
