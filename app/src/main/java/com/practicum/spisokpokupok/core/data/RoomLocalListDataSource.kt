package com.practicum.spisokpokupok.core.data

import com.practicum.spisokpokupok.core.data.roomdb.dao.CompletedListDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalCompletedShoppingList
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toExternal
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toExternalList
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toLocal
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
            shoppingListDao.deleteActualList(shoppingListId)
        }

        override suspend fun addListToFavorite(shoppingListId: String) {
            shoppingListDao.updateActualList(shoppingListId, true)
        }

        override suspend fun removeListFromFavorite(shoppingListId: String) {
            shoppingListDao.updateActualList(shoppingListId, false)
        }

        override fun observeActualLists(): Flow<List<ShoppingList>> =
            shoppingListDao.observeActualLists().map {
                it.toExternalList()
            }

        override suspend fun addActualList(listId: String) {
            shoppingListDao.addActualList(listId, false)
        }

        override fun observeCompletedLists(): Flow<List<ShoppingList>> =
            completedListDao.observeAll().map { list ->
                list.map {
                    it.toExternal()
                }
            }

        override suspend fun completeList(listId: String) {
            completedListDao.addCompletedList(LocalCompletedShoppingList(listId, 0))
        }
    }
