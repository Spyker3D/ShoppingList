package com.practicum.spisokpokupok.core.domain.repository

import com.practicum.spisokpokupok.listdetails.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface ShoppingTaskRepository {
    suspend fun getCurrentTasks(listId: String): Flow<List<Task>>

    suspend fun createTask(
        goodName: String,
        quantity: Int,
        quantityType: String,
        position: Int,
    ): String

    suspend fun completeTask(taskId: String)

    suspend fun deleteTask(taskId: String)

    suspend fun addTask(
        goodName: String,
        quantity: Int,
        quantityType: String,
        position: Int,
    )

    suspend fun updateTask(
        taskId: String,
        goodName: String,
        quantity: Int,
        quantityType: String,
        position: Int,
    )

    suspend fun getTaskById(taskId: String): Task
}
