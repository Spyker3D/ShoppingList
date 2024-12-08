package com.practicum.spisokpokupok.core.data.roomDb.dao

import androidx.room.Dao
import androidx.room.Query
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalCompletedShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedListDao {
    @Query("SELECT * FROM completed_shopping_list")
    fun observeAll(): Flow<List<LocalCompletedShoppingList>>

    @Query("INSERT INTO completed_shopping_list (shoppingListId) VALUES (:shoppingListId)")
    suspend fun addCompletedList(shoppingListId: String)
}
