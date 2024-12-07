package com.practicum.spisokpokupok.db.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.practicum.spisokpokupok.db.data.entity.LocalShoppingTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingTaskDao {
    @Query("SELECT * FROM shopping_task")
    fun observeAll(): Flow<List<LocalShoppingTask>>

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
}
