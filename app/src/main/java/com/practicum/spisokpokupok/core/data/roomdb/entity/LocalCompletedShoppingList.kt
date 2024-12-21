package com.practicum.spisokpokupok.core.data.roomdb.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "completed_shopping_list",
)
class LocalCompletedShoppingList(
    @ColumnInfo(name = "shoppingListId")
    val shoppingListId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)

class LocalCompletedShoppingListWithName(
    @Embedded val localCompletedShoppingList: LocalCompletedShoppingList,
    @Relation(
        parentColumn = "shoppingListId",
        entityColumn = "id",
        entity = LocalShoppingList::class,
    )
    val localShoppingList: LocalShoppingList,
)
