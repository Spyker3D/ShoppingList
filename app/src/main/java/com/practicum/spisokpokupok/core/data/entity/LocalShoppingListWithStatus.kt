package com.practicum.spisokpokupok.core.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class LocalActualShoppingListWithName(
    @Embedded
    val localShoppingList: LocalShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId",
        entity = LocalActualShoppingList::class,
    )
    val localActualShoppingList: LocalActualShoppingList?,
)
