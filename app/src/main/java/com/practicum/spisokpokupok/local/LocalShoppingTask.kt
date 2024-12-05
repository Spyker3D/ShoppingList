package com.practicum.spisokpokupok.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_task",
)
data class LocalShoppingTask(
    val shoppingListId: String,
    val goodId: String,
    val quantity: Int,
    val quantityType: String,
    val isCompleted: Boolean,
    @PrimaryKey val id: String,
)
