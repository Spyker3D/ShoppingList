package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_list",
)
class LocalShoppingList(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo
    @PrimaryKey val id: String,
)
