package com.practicum.buyinglist.data.source.local

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
