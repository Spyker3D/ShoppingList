package com.practicum.spisokpokupok.listdetails.data.repository

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface LocalTaskDataSource {
    suspend fun createTask(
        task: Task,
        shoppingListId: String,
    )

    fun observeTasks(shoppingListId: String): Flow<List<Task>>

    suspend fun createTasks(
        tasks: List<Task>,
        shoppingListId: String,
    )

    suspend fun deleteTask(taskId: String)

    suspend fun updateTask(
        id: String,
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
        isCompleted: Boolean,
    )

    fun moveTaskToActual(taskId: String) {
    }

    suspend fun getTaskById(taskId: String): Task

    suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<Task>

    suspend fun getCompletedListById(shoppingListId: String): ShoppingList

    suspend fun deleteCompletedList(completedShoppingListId: String)
}
