package com.practicum.spisokpokupok.core.data.roomDb.mapper

import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingList
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTask
import com.practicum.spisokpokupok.listdetails.domain.model.Good
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
    id = id.toInt(),
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
