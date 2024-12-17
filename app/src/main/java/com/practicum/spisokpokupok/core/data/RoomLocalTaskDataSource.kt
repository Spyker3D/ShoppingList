package com.practicum.spisokpokupok.core.data

import com.practicum.spisokpokupok.core.data.roomdb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toExternal
import com.practicum.spisokpokupok.core.data.roomdb.mapper.toLocal
import com.practicum.spisokpokupok.listdetails.data.repository.LocalTaskDataSource
import com.practicum.spisokpokupok.listdetails.domain.model.QuantityType
import com.practicum.spisokpokupok.listdetails.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalTaskDataSource
    @Inject
    constructor(
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

        override fun observeTasks(shoppingListId: String): Flow<List<Task>> =
            shoppingTaskDao.observeAllWithGoods(shoppingListId).map { localShoppingTasks ->
                localShoppingTasks.map { localShoppingTaskWithGood ->
                    localShoppingTaskWithGood.toExternal()
                }
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
            isCompleted: Boolean,
        ) {
            goodDao.upsert(
                LocalGood(
                    name = goodName,
                    id = 0,
                ),
            )
            val goodId = goodDao.getGoodIdByName(goodName)
            shoppingTaskDao.updateTaskGoodId(id, goodId.toString())
            shoppingTaskDao.updateTaskQuantity(id, quantity)
            shoppingTaskDao.updateTaskQuantityType(id, quantityType.name)
            shoppingTaskDao.updateTaskPosition(id, position)
            shoppingTaskDao.updateTaskStatus(id, isCompleted)
        }

        override suspend fun getTaskById(taskId: String): Task {
            shoppingTaskDao.getTaskById(taskId).let {
                return it[0].toExternal()
            }
        }
    }
