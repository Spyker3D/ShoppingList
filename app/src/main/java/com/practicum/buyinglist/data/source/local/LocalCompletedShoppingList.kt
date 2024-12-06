package com.practicum.buyinglist.data.source.local

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
