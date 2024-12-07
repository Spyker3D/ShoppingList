package com.practicum.spisokpokupok.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.entity.LocalShoppingTask
import com.practicum.spisokpokupok.core.data.entity.LocalShoppingTaskWithGood
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingTaskDao {
    @Query("SELECT * FROM shopping_task")
    fun observeAllWithGoods(): Flow<List<LocalShoppingTaskWithGood>>

    @Upsert
    suspend fun upsert(task: LocalShoppingTask)

    @Upsert
    suspend fun upsertAll(tasks: List<LocalShoppingTask>)

    @Query("UPDATE shopping_task SET isCompleted = :completed WHERE id = :taskId")
    suspend fun updateCompleted(
        taskId: String,
        completed: Boolean,
    )

    @Query("DELETE FROM shopping_task WHERE id = :shoppingListId")
    suspend fun deleteAll(shoppingListId: List<String>)

    @Transaction
    @Query("SELECT * FROM shopping_task")
    suspend fun getShoppingTasksWithGoods(): List<LocalShoppingTaskWithGood>

    @Transaction
    @Query("SELECT * FROM shopping_task WHERE shoppingListId = :shoppingListId")
    suspend fun getShoppingTasksWithGoods(shoppingListId: String): List<LocalShoppingTaskWithGood>
}
