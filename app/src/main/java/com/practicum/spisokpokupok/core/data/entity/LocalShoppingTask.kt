package com.practicum.spisokpokupok.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.spisokpokupok.listdetails.domain.model.Task

@Entity(
    tableName = "shopping_task",
)
data class LocalShoppingTask(
    val shoppingListId: String,
    val goodId: String,
    val quantity: Int,
    val quantityType: String,
    val isCompleted: Boolean,
    val position: Int,
    @PrimaryKey val id: String,
)

fun Task.toLocal(
    localGoodId: String,
    shoppingListId: String,
) = LocalShoppingTask(
    id = id,
    goodId = localGoodId,
    isCompleted = isCompleted,
    shoppingListId = shoppingListId,
    quantity = quantity,
    quantityType = quantityType,
    position = position,
)
