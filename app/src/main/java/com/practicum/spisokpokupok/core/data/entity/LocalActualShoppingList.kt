package com.practicum.spisokpokupok.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "actual_shopping_list",
)
data class LocalActualShoppingList(
    val shoppingListId: String,
    val isFavorite: Boolean,
    @PrimaryKey(autoGenerate = true) val id: String,
)
