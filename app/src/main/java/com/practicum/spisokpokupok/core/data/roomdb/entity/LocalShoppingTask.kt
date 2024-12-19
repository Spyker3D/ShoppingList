package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_task",
)
data class LocalShoppingTask(
    val shoppingListId: String,
    val goodId: Int,
    val quantity: Int,
    val quantityType: String,
    val isCompleted: Boolean = false,
    val position: Int,
    @PrimaryKey val id: String,
)