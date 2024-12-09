package com.practicum.spisokpokupok.core.data

import com.practicum.spisokpokupok.core.data.roomDb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomDb.mapper.quantityTypeToString
import com.practicum.spisokpokupok.core.data.roomDb.mapper.toLocal
import com.practicum.spisokpokupok.listdetails.data.repository.LocalTaskDataSource
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task

class RoomLocalTaskDataSource(
    private val shoppingTaskDao: ShoppingTaskDao,
    private val goodDao: GoodDao,
) : LocalTaskDataSource {
    override suspend fun createTask(
        task: Task,
        shoppingListId: String,
    ) {
        goodDao.upsert(
            LocalGood(
                name = task.goodName,
                id = 0,
            ),
        )
        val goodId = goodDao.getGoodIdByName(task.goodName)
        shoppingTaskDao.upsert(
            task.toLocal(
                shoppingListId = shoppingListId,
                localGoodId = goodId.toString(),
            ),
        )
    }

    override suspend fun updateCompleted(
        taskId: String,
        isCompleted: Boolean,
    ) {
        shoppingTaskDao.updateCompleted(taskId, isCompleted)
    }

    override suspend fun deleteTask(taskId: String) {
        shoppingTaskDao.deleteTask(taskId)
    }

    override suspend fun updateTask(
        id: String,
        goodName: String,
        quantity: Int,
        quantityType: QuantityType,
        position: Int,
    ) {
        goodDao.upsert(
            LocalGood(
                name = goodName,
                id = 0,
            ),
        )
        shoppingTaskDao.updateTask(
            taskId = id,
            goodId = goodDao.getGoodIdByName(goodName).toString(),
            quantity = quantity,
            quantityType = quantityTypeToString(quantityType),
            position = position,
        )
    }
}
