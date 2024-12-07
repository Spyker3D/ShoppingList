package com.practicum.spisokpokupok.core.domain.repository

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun getCurrentLists(): Flow<List<ShoppingList>>

    suspend fun completeList(listId: String)

    suspend fun deleteList(listId: String)

    suspend fun addList(list: ShoppingList)

    suspend fun updateList(list: ShoppingList)

    suspend fun getCompletedLists(): Flow<List<ShoppingList>>

    suspend fun getFavoriteLists(): Flow<List<ShoppingList>>

    suspend fun getListById(listId: String): ShoppingList

    suspend fun addToFavorite(listId: String)
}
