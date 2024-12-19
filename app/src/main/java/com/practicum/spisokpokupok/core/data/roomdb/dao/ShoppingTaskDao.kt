package com.practicum.spisokpokupok.core.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTask
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTaskWithGood
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingTaskDao {
    @Transaction
    @Query("SELECT * FROM shopping_task")
    fun observeAllWithGoods(): Flow<List<LocalShoppingTaskWithGood>>

    @Transaction
    @Query("SELECT * FROM shopping_task WHERE shoppingListId = :listId")
    fun observeAllWithGoods(listId: String): Flow<List<LocalShoppingTaskWithGood>>

    @Upsert
    suspend fun upsert(task: LocalShoppingTask)

    @Upsert
    suspend fun upsertAll(tasks: List<LocalShoppingTask>)

    @Query("UPDATE shopping_task SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskStatus(
        taskId: String,
        isCompleted: Boolean,
    )

    @Query("UPDATE shopping_task SET position = :position WHERE id = :taskId")
    suspend fun updateTaskPosition(
        taskId: String,
        position: Int,
    )

    @Query("UPDATE shopping_task SET goodId = :goodId WHERE id = :taskId")
    suspend fun updateTaskGoodId(
        taskId: String,
        goodId: String,
    )

    @Query("UPDATE shopping_task SET quantity = :quantity WHERE id = :taskId")
    suspend fun updateTaskQuantity(
        taskId: String,
        quantity: Int,
    )

    @Query("UPDATE shopping_task SET quantityType = :quantityType WHERE id = :taskId")
    suspend fun updateTaskQuantityType(
        taskId: String,
        quantityType: String,
    )

    @Query("DELETE FROM shopping_task WHERE id = :shoppingListId")
    suspend fun deleteAll(shoppingListId: List<String>)

    @Query("DELETE FROM shopping_task WHERE id = :taskId")
    suspend fun deleteTask(taskId: String)

    @Transaction
    @Query("SELECT * FROM shopping_task")
    suspend fun getShoppingTasksWithGoods(): List<LocalShoppingTaskWithGood>

    @Transaction
    @Query("SELECT * FROM shopping_task WHERE shoppingListId = :shoppingListId")
    suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<LocalShoppingTaskWithGood>

    @Transaction
    @Query("SELECT * FROM shopping_task WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): List<LocalShoppingTaskWithGood>
}
