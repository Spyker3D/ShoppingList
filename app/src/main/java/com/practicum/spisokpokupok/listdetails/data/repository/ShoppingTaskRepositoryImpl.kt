package com.practicum.spisokpokupok.listdetails.data.repository

import com.practicum.spisokpokupok.di.ApplicationScope
import com.practicum.spisokpokupok.di.DefaultDispatcher
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
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

        override suspend fun deleteTask(taskId: String) {
            localTaskDataSource.deleteTask(taskId)
        }

        override suspend fun updateTask(
            taskId: String,
            goodName: String,
            quantity: Int,
            quantityType: QuantityType,
            position: Int,
            completed: Boolean,
        ) {
            localTaskDataSource.updateTask(
                id = taskId,
                goodName = goodName,
                quantity = quantity,
                quantityType = quantityType,
                position = position,
                isCompleted = completed,
            )
        }

        override suspend fun moveTaskToActual(taskId: String) {
            localTaskDataSource.moveTaskToActual(taskId)
        }

        override suspend fun changeItemStatus(taskId: String) {
            val task = localTaskDataSource.getTaskById(taskId)
            if (task.isCompleted) {
                localTaskDataSource.updateTask(
                    id = taskId,
                    goodName = task.goodName,
                    quantity = task.quantity,
                    quantityType = task.quantityType,
                    position = task.position,
                    isCompleted = false,
                )
            } else {
                localTaskDataSource.updateTask(
                    id = taskId,
                    goodName = task.goodName,
                    quantity = task.quantity,
                    quantityType = task.quantityType,
                    position = task.position,
                    isCompleted = true,
                )
            }
        }

        override suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<Task> =
            localTaskDataSource.getShoppingTasksWithGoods(shoppingListId = shoppingListId)

        override suspend fun getCompletedListById(shoppingListId: String): ShoppingList =
            localTaskDataSource.getCompletedListById(shoppingListId)

        override suspend fun deleteCompletedList(completedShoppingListId: String) {
            localTaskDataSource.deleteCompletedList(completedShoppingListId)
        }

        private fun createTaskId(): String = UUID.randomUUID().toString()
    }
