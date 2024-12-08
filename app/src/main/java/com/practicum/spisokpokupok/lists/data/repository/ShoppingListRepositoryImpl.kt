package com.practicum.spisokpokupok.lists.data.repository

import com.practicum.spisokpokupok.di.ApplicationScope
import com.practicum.spisokpokupok.di.DefaultDispatcher
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class ShoppingListRepositoryImpl
    @Inject
    constructor(
        private val localDataSource: LocalListDataSource,
        @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
        @ApplicationScope private val scope: CoroutineScope,
    ) : ShoppingListRepository {
        override suspend fun getCurrentLists(): Flow<List<ShoppingList>> = localDataSource.observeActualLists()

        override suspend fun completeList(listId: String) {
            scope.launch {
                localDataSource.completeList(listId)
            }
        }

        override suspend fun deleteList(listId: String) {
            TODO("Not yet implemented")
        }

        override suspend fun createList(name: String) {
            val listId =
                withContext(dispatcher) {
                    createListId()
                }
            val list =
                ShoppingList(
                    name = name,
                    id = listId,
                    isCompleted = false,
                    isFavorite = false,
                )
            localDataSource.createList(list)
            localDataSource.addActualList(listId)
        }

        override suspend fun updateName(
            id: String,
            name: String,
        ) {
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

        private fun createListId(): String = UUID.randomUUID().toString()
    }
