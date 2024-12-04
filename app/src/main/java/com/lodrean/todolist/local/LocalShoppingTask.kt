package com.lodrean.todolist.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_task",
)
data class LocalShoppingTask(
    val goodName: String,
    val quantity: Int,
    val quantityOption: String,
    val isCompleted: Boolean,
    @PrimaryKey val id: String,
)
