package com.practicum.buyinglist.data

import com.practicum.buyinglist.data.source.local.ShoppingListDao
import com.practicum.buyinglist.di.ApplicationScope
import com.practicum.buyinglist.di.DefaultDispatcher
import com.practicum.buyinglist.domain.model.ShoppingList
import com.practicum.buyinglist.domain.repository.ShoppingListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class ShoppingLIstRepositoryImpl(
    private val localDataSource: ShoppingListDao,
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
