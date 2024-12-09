package com.practicum.spisokpokupok.listdetails.data.repository

import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task

interface LocalTaskDataSource {
    suspend fun createTask(
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    )

    suspend fun updateCompleted(
        taskId: String,
        isCompleted: Boolean,
    )

    suspend fun deleteTask(taskId: String)

    suspend fun addTask(task: Task)

    suspend fun updateTask(task: Task)
}
