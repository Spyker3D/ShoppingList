package com.practicum.spisokpokupok.core.data.roomDb.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "actual_shopping_list",
)
data class LocalActualShoppingList(
    val shoppingListId: String,
    val isFavorite: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int,
)

data class LocalActualShoppingListWithName(
    @Embedded val localActualShoppingList: LocalActualShoppingList,
    @Relation(
        parentColumn = "shoppingListId",
        entityColumn = "id",
        entity = LocalShoppingList::class,
    )
    val localShoppingList: LocalShoppingList,
)
