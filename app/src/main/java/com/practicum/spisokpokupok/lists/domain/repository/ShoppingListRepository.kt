package com.practicum.spisokpokupok.lists.domain.repository

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    suspend fun getCurrentLists(): Flow<List<ShoppingList>>

    suspend fun completeList(listId: String)

    suspend fun deleteList(listId: String)

    suspend fun createList(name: String)

    suspend fun updateName(
        id: String,
        name: String,
    )

    suspend fun getCompletedLists(): Flow<List<ShoppingList>>

    suspend fun getFavoriteLists(): Flow<List<ShoppingList>>

    suspend fun getListById(listId: String): ShoppingList

    suspend fun addToFavorite(listId: String)
}
