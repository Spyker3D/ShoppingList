package com.practicum.spisokpokupok.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_list",
)
data class LocalShoppingList(
    val name: String,
    val dateTime: String,
    @PrimaryKey
    val id: String,
)
