package com.practicum.spisokpokupok.core.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.practicum.spisokpokupok.listdetails.domain.model.Task

data class LocalShoppingTaskWithGood(
    @Embedded val localShoppingTask: LocalShoppingTask,
    @Relation(
        parentColumn = "goodId",
        entityColumn = "id",
    )
    val good: LocalGood,
)

fun List<LocalShoppingTaskWithGood>.toExternal() = map(LocalShoppingTaskWithGood::toExternal)

fun LocalShoppingTaskWithGood.toExternal() =
    Task(
        id = localShoppingTask.id,
        goodName = good.name,
        quantity = localShoppingTask.quantity,
        quantityType = localShoppingTask.quantityType,
        isCompleted = localShoppingTask.isCompleted,
        position = localShoppingTask.position,
    )
