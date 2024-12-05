package com.practicum.spisokpokupok.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_list",
)
data class LocalsShopingList(
    val name: String,
    val isCompleted: Boolean,
    val dateTime: String,
    @PrimaryKey
    val id: String,
)
