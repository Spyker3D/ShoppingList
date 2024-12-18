package com.practicum.spisokpokupok.core.data

import com.practicum.spisokpokupok.core.data.roomdb.dao.CompletedListDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toExternal
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toExternalTask
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toLocal
import com.practicum.spisokpokupok.listdetails.data.repository.LocalTaskDataSource
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import com.practicum.spisokpokupok.listdetails.domain.model.quantityTypeToString
import com.practicum.spisokpokupok.lists.domain.model.ShoppingList
import javax.inject.Inject

class RoomLocalTaskDataSource
    @Inject
    constructor(
        private val shoppingTaskDao: ShoppingTaskDao,
        private val goodDao: GoodDao,
        private val completedListDao: CompletedListDao
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

        override suspend fun createTasks(
            tasks: List<Task>,
            shoppingListId: String,
        ) {
            tasks.forEach { task ->
                createTask(task, shoppingListId)
            }
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

        override suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<Task> =
            shoppingTaskDao.getShoppingTasksWithGoods(shoppingListId = shoppingListId).toExternalTask()

        override suspend fun getCompletedListById(shoppingListId: String): ShoppingList =
            completedListDao.getCompletedListById(shoppingListId).toExternal()

        override suspend fun deleteCompletedList(completedShoppingListId: String) {
            completedListDao.deleteList(completedShoppingListId)
        }
    }
