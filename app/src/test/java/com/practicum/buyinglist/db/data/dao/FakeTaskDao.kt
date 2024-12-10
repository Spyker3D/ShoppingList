package com.practicum.buyinglist.db.data.dao

import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTask
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTaskWithGood
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

    override suspend fun upsert(task: LocalShoppingTask) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAll(tasks: List<LocalShoppingTask>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCompleted(
        taskId: String,
        completed: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(shoppingListId: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun getShoppingTasksWithGoods(): List<LocalShoppingTaskWithGood> {
        TODO("Not yet implemented")
    }

    override suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<LocalShoppingTaskWithGood> {
        TODO("Not yet implemented")
    }
}
