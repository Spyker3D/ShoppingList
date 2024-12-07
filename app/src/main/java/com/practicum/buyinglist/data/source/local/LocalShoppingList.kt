package com.practicum.buyinglist.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_list",
)
data class LocalShoppingList(
    val name: String,
    val isCompleted: Boolean,
    val dateTime: String,
    @PrimaryKey
    val id: String,
)
