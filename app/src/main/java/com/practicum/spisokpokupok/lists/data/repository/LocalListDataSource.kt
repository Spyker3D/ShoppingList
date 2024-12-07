package com.practicum.spisokpokupok.lists.data.repository

import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface LocalListDataSource {
    suspend fun createList(name: String): String

    suspend fun updateName(
        name: String,
        shoppingListId: String,
    )

    suspend fun deleteList(shoppingListId: String)

    suspend fun addListToFavorite(shoppingListId: String)

    fun observeActualLists(): Flow<List<ShoppingList>>

    suspend fun fetchCompletedLists(): List<ShoppingList>
}
