package com.practicum.spisokpokupok.lists.domain.repository

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {
    fun getCurrentLists(): Flow<List<ShoppingList>>

    suspend fun completeList(listId: String)

    suspend fun deleteList(listId: String)

    suspend fun createList(name: String): String

    suspend fun updateName(
        id: String,
        name: String,
    )

    suspend fun moveToActualLists(listId: String)

    fun getCompletedLists(): Flow<List<ShoppingList>>

    suspend fun addToFavorite(listId: String)

    suspend fun removeFromFavorite(listId: String)
}
