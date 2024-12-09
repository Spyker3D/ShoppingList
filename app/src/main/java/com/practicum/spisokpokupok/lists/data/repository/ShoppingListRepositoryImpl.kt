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
        override fun getCurrentLists(): Flow<List<ShoppingList>> = localDataSource.observeActualLists()

        override suspend fun completeList(listId: String) {
            scope.launch {
                localDataSource.completeList(listId)
            }
        }

        override suspend fun deleteList(listId: String) {
            scope.launch {
                localDataSource.deleteList(listId)
            }
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
            localDataSource.updateName(id, name)
        }

        override suspend fun getCompletedLists(): Flow<List<ShoppingList>> = localDataSource.observeCompletedLists()

        override suspend fun addToFavorite(listId: String) {
            localDataSource.addListToFavorite(listId)
        }

        override suspend fun removeFromFavorite(listId: String) {
            localDataSource.removeListFromFavorite(listId)
        }

        private fun createListId(): String = UUID.randomUUID().toString()
    }
