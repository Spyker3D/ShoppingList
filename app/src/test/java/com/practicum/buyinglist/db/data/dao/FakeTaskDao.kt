package com.practicum.buyinglist.db.data.dao

import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTask
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTaskWithGood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTaskDao(
    initialTasks: List<LocalShoppingTask>,
) : ShoppingTaskDao {
    private val _tasks = initialTasks.toMutableList()
    private val tasksStream = MutableStateFlow(_tasks.toList())

    override fun observeAllWithGoods(): Flow<List<LocalShoppingTaskWithGood>> {
        TODO("Not yet implemented")
    }

    override fun observeAllWithGoods(listId: String): Flow<List<LocalShoppingTaskWithGood>> {
        TODO("Not yet implemented")
    }

    override suspend fun upsert(task: LocalShoppingTask) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAll(tasks: List<LocalShoppingTask>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskStatus(
        taskId: String,
        isCompleted: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskPosition(
        taskId: String,
        position: Int,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskGoodId(
        taskId: String,
        goodId: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskQuantity(
        taskId: String,
        quantity: Int,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTaskQuantityType(
        taskId: String,
        quantityType: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(
        taskId: String,
        goodId: String,
        quantity: Int,
        quantityType: String,
        position: Int,
        isCompleted: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(shoppingListId: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getShoppingTasksWithGoods(): List<LocalShoppingTaskWithGood> {
        TODO("Not yet implemented")
    }

    override suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<LocalShoppingTaskWithGood> {
        TODO("Not yet implemented")
    }

    override suspend fun getTaskById(taskId: String): List<LocalShoppingTaskWithGood> {
        TODO("Not yet implemented")
    }
}
