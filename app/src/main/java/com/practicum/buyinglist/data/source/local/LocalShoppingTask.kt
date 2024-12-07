package com.practicum.buyinglist.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practicum.buyinglist.domain.model.Task

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

// fun List<LocalShoppingTask>.toExternal() = map(LocalShoppingTask::toExternal) // Equivalent to map { it.toExternal() }

fun LocalShoppingTask.toExternal(good: LocalGood) =
    Task(
        id = id,
        name = good.name,
        quantity = quantity,
        quantityType = quantityType,
        isCompleted = isCompleted,
        position = position,
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
