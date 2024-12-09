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

class ShoppingTaskRepositoryImpl(
    private val localTaskDataSource: LocalTaskDataSource,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) : ShoppingTaskRepository {
    override suspend fun getCurrentTasks(listId: String): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun createTask(
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    ): String {
        val taskId =
            withContext(dispatcher) {
                createTaskId()
            }

        localTaskDataSource.createTask(goodName, quantity, quantityType, position)
        return taskId
    }

    override suspend fun completeTask(taskId: String) {
        localTaskDataSource.updateCompleted(taskId, true)
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(
        taskId: String,
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: String): Task {
        TODO("Not yet implemented")
    }

    private fun createTaskId(): String = UUID.randomUUID().toString()
}
