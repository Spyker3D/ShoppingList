package com.practicum.spisokpokupok.core.data.entity

import com.practicum.spisokpokupok.core.data.dao.ShoppingListDao
import com.practicum.spisokpokupok.di.ApplicationScope
import com.practicum.spisokpokupok.di.DefaultDispatcher
import com.practicum.spisokpokupok.lists.data.repository.LocalListDataSource
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomLocalListDataSource
    @Inject
    constructor(
        private val shoppingListDao: ShoppingListDao,
        @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
        @ApplicationScope private val scope: CoroutineScope,
    ) : LocalListDataSource {
    override suspend fun createList(name: String): String {
        TODO("Not yet implemented")
        }

    override suspend fun updateName(name: String, shoppingListId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteList(shoppingListId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addListToFavorite(shoppingListId: String) {
        TODO("Not yet implemented")
    }

    override fun observeActualLists(): Flow<List<ShoppingList>> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCompletedLists(): List<ShoppingList> {
        TODO("Not yet implemented")
    }

}

