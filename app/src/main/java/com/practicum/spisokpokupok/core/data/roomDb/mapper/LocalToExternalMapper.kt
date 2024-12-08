package com.practicum.spisokpokupok.core.data.roomDb.mapper

import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalActualShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTaskWithGood
import com.practicum.spisokpokupok.listdetails.domain.model.Good
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList

fun LocalActualShoppingListWithName.toExternal() =
    ShoppingList(
        id = localShoppingList.id,
        name = localShoppingList.name,
        isFavorite = localActualShoppingList.isFavorite,
        isCompleted = false,
    )

fun List<LocalShoppingTaskWithGood>.toExternal() = map(LocalShoppingTaskWithGood::toExternal)

fun LocalShoppingTaskWithGood.toExternal() =
    Task(
        id = localShoppingTask.id.toString(),
        goodName = good.name,
        quantity = localShoppingTask.quantity,
        quantityType = localShoppingTask.quantityType,
        isCompleted = localShoppingTask.isCompleted,
        position = localShoppingTask.position,
    )

fun LocalGood.toExternal() =
    Good(
        id = id.toString(),
        name = name,
    )
