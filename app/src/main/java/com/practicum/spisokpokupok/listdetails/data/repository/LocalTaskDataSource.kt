package com.practicum.spisokpokupok.listdetails.data.repository

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface LocalTaskDataSource {
    suspend fun createTask(
        task: Task,
        shoppingListId: String,
    )

    fun observeTasks(shoppingListId: String): Flow<List<Task>>

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
}
