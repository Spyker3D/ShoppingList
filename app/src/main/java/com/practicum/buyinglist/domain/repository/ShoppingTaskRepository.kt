package com.practicum.buyinglist.domain.repository

import com.practicum.buyinglist.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface ShoppingTaskRepository {
    suspend fun getCurrentTasks(listId: String): Flow<List<Task>>

    suspend fun completeTask(taskId: String)

    suspend fun deleteTask(taskId: String)

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun getTaskById(taskId: String): Task
}
