package com.practicum.spisokpokupok.listdetails.data.repository

import com.practicum.spisokpokupok.di.ApplicationScope
import com.practicum.spisokpokupok.di.DefaultDispatcher
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

class ShoppingTaskRepositoryImpl
    @Inject
    constructor(
        private val localTaskDataSource: LocalTaskDataSource,
        @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
        @ApplicationScope private val scope: CoroutineScope,
    ) : ShoppingTaskRepository {
        override fun getCurrentTasks(listId: String): Flow<List<Task>> = localTaskDataSource.observeTasks(listId)

        override suspend fun createTask(
            shoppingListId: String,
            goodName: String,
            quantity: Int,
            quantityType: QuantityType,
            position: Int,
        ): String {
            val taskId =
                withContext(dispatcher) {
                    createTaskId()
                }
            val task =
                Task(
                    id = taskId,
                    goodName = goodName,
                    quantity = quantity,
                    quantityType = quantityType,
                    isCompleted = false,
                    position = position,
                )

            localTaskDataSource.createTask(task, shoppingListId)
            return taskId
        }

        override suspend fun completeTask(taskId: String) {
            localTaskDataSource.updateCompleted(taskId, true)
        }

        override suspend fun deleteTask(taskId: String) {
            localTaskDataSource.deleteTask(taskId)
        }

        override suspend fun updateTask(
            taskId: String,
            goodName: String,
            quantity: Int,
            quantityType: QuantityType,
            position: Int,
        ) {
            localTaskDataSource.updateTask(
                id = taskId,
                goodName = goodName,
                quantity = quantity,
                quantityType = quantityType,
                position = position,
            )
        }

        private fun createTaskId(): String = UUID.randomUUID().toString()
    }
