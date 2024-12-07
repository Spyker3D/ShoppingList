package com.practicum.spisokpokupok.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "completed_shopping_list",
)
data class LocalCompletedShoppingList(
    val shoppingListId: String,
    @PrimaryKey(autoGenerate = true)
    val id: String,
)
