package com.practicum.spisokpokupok.listdetails.data.repository

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList

interface LocalTaskDataSource {
    suspend fun createTask(
        task: Task,
        shoppingListId: String,
    )

    suspend fun updateCompleted(
        taskId: String,
        isCompleted: Boolean,
    )

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
    )

    suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<Task>

    suspend fun getCompletedListById(shoppingListId: String): ShoppingList

    suspend fun deleteCompletedList(completedShoppingListId: String)
}
