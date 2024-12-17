package com.practicum.spisokpokupok.lists.data.repository

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface LocalListDataSource {
    suspend fun createList(shoppingList: ShoppingList)

    suspend fun getListTitle(shoppingListId: String): String

    suspend fun updateName(
        name: String,
        shoppingListId: String,
    )

    suspend fun deleteList(shoppingListId: String)

    suspend fun addListToFavorite(shoppingListId: String)

    suspend fun removeListFromFavorite(shoppingListId: String)

    fun observeActualLists(): Flow<List<ShoppingList>>

    suspend fun addActualList(listId: String)

    fun observeCompletedLists(): Flow<List<ShoppingList>>

    suspend fun completeList(listId: String)
}
