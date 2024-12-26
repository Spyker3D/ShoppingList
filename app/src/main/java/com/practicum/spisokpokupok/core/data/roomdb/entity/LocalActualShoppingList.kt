package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "actual_shopping_list",
)
class LocalActualShoppingList(
    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,
    @ColumnInfo
    @PrimaryKey(autoGenerate = true) val id: Int,
)

class LocalActualShoppingListWithName(
    @Embedded
    val localActualShoppingList: LocalActualShoppingList,
    @Relation(
        parentColumn = "shoppingListId",
        entityColumn = "id",
        entity = LocalShoppingList::class,
    )
    val localShoppingList: LocalShoppingList,
)
