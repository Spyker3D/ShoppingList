package com.practicum.spisokpokupok.listdetails.domain.repository

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
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

    suspend fun completeTask(taskId: String)

    suspend fun deleteTask(taskId: String)

    suspend fun updateTask(
        taskId: String,
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    )
}
