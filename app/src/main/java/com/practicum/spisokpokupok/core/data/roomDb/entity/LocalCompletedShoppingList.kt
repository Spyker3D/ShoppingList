package com.practicum.spisokpokupok.core.data.roomDb.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "completed_shopping_list",
)
data class LocalCompletedShoppingList(
    val shoppingListId: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)

data class LocalCompletedShoppingListWithName(
    @Embedded val localCompletedShoppingList: LocalCompletedShoppingList,
    @Relation(
        parentColumn = "shoppingListId",
        entityColumn = "id",
        entity = LocalShoppingList::class,
    )
    val localShoppingList: LocalShoppingList,
)
