package com.practicum.spisokpokupok.core.data

import android.util.Log
import com.practicum.spisokpokupok.core.data.roomDb.dao.CompletedListDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalActualShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomDb.mapper.toExternal
import com.practicum.spisokpokupok.core.data.roomDb.mapper.toLocal
import com.practicum.spisokpokupok.di.ApplicationScope
import com.practicum.spisokpokupok.di.DefaultDispatcher
import com.practicum.spisokpokupok.lists.data.repository.LocalListDataSource
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalListDataSource
    @Inject
    constructor(
        private val completedListDao: CompletedListDao,
        private val shoppingListDao: ShoppingListDao,
        @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
        @ApplicationScope private val scope: CoroutineScope,
    ) : LocalListDataSource {
        override suspend fun createList(shoppingList: ShoppingList) {
            shoppingListDao.upsert(shoppingList.toLocal())
        }

        override suspend fun updateName(
            name: String,
            shoppingListId: String,
        ) {
            shoppingListDao.updateName(name, shoppingListId)
        }

        override suspend fun deleteList(shoppingListId: String) {
            TODO("Not yet implemented")
        }

        override suspend fun addListToFavorite(shoppingListId: String) {
            TODO("Not yet implemented")
        }

        override fun observeActualLists(): Flow<List<ShoppingList>> =
            shoppingListDao.observeActualLists().map {
                it.map(LocalActualShoppingListWithName::toExternal).also {
                    Log.d("RoomLocalListDataSource", "observeActualLists: $it")
                }
            }

        override suspend fun addActualList(listId: String) {
            shoppingListDao.addActualList(listId, false)
        }

        override suspend fun fetchCompletedLists(): List<ShoppingList> {
            TODO("Not yet implemented")
        }

        override suspend fun completeList(listId: String) {
            completedListDao.addCompletedList(listId)
        }
    }
