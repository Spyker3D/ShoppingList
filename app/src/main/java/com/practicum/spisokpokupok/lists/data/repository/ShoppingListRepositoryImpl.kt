package com.practicum.spisokpokupok.lists.data.repository

import com.practicum.spisokpokupok.core.domain.repository.ShoppingListRepository
import com.practicum.spisokpokupok.di.ApplicationScope
import com.practicum.spisokpokupok.di.DefaultDispatcher
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ShoppingLIstRepositoryImpl(
    private val localDataSource: LocalListDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ShoppingListRepository {
    override suspend fun getCurrentLists(): Flow<List<ShoppingList>> {
        TODO("Not yet implemented")
    }

    override suspend fun completeList(listId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteList(listId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addList(list: ShoppingList) {
        TODO("Not yet implemented")
    }

    override suspend fun updateList(list: ShoppingList) {
        TODO("Not yet implemented")
    }

    override suspend fun getCompletedLists(): Flow<List<ShoppingList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteLists(): Flow<List<ShoppingList>> {
        TODO("Not yet implemented")
    }

    override suspend fun getListById(listId: String): ShoppingList {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorite(listId: String) {
        TODO("Not yet implemented")
    }
}
