package com.practicum.spisokpokupok.listdetails.domain.repository

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingTaskRepository {
    fun getCurrentTasks(listId: String): Flow<List<Task>>

    suspend fun createTask(
        shoppingListId: String,
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    ): String

    suspend fun deleteTask(taskId: String)

    suspend fun updateTask(
        taskId: String,
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
        completed: Boolean,
    )

    suspend fun moveTaskToActual(taskId: String)

    suspend fun changeItemStatus(taskId: String)

    suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<Task>

    suspend fun getCompletedListById(shoppingListId: String): ShoppingList

    suspend fun deleteCompletedList(completedShoppingListId: String)
}
