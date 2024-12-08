package com.practicum.spisokpokupok.core.data.roomDb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_list",
)
data class LocalShoppingList(
    @ColumnInfo(name = "name")
    val name: String,
    @PrimaryKey
    val id: String,
)
