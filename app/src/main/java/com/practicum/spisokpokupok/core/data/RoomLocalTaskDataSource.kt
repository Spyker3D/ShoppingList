package com.practicum.spisokpokupok.core.data

import com.practicum.spisokpokupok.core.data.roomDb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood
import com.practicum.spisokpokupok.listdetails.data.repository.LocalTaskDataSource
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task

class RoomLocalTaskDataSource(
    private val shoppingTaskDao: ShoppingTaskDao,
    private val goodDao: GoodDao,
    private val shoppingListDao: ShoppingListDao,
) : LocalTaskDataSource {
    override suspend fun createTask(
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    ) {
        val goodId =
            goodDao.upsert(
                LocalGood(
                    name = goodName,
                    id = 0,
                ),
            )
    }

    override suspend fun updateCompleted(
        taskId: String,
        isCompleted: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun addTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTask(task: Task) {
        TODO("Not yet implemented")
    }
}
