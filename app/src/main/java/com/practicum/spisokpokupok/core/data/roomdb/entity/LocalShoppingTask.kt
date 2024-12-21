package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shopping_task",
)
class LocalShoppingTask(
    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: String,
    @ColumnInfo(name = "goodId")
    val goodId: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "quantityType")
    val quantityType: String,
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "position")
    val position: Int,
    @PrimaryKey val id: String,
)
