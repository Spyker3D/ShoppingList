package com.practicum.spisokpokupok.core.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalActualShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalCompletedShoppingList
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalCompletedShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTaskWithGood
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedListDao {
    @Transaction
    @Query("SELECT * FROM completed_shopping_list")
    fun observeAll(): Flow<List<LocalCompletedShoppingListWithName>>

    @Insert(
        entity = LocalCompletedShoppingList::class,
        onConflict = OnConflictStrategy.IGNORE,
    )
    suspend fun addCompletedList(localCompletedShoppingList: LocalCompletedShoppingList)

    @Transaction
    @Query("SELECT * FROM completed_shopping_list WHERE shoppingListId = :shoppingListId")
    suspend fun getCompletedListById(shoppingListId: String): LocalCompletedShoppingListWithName

    @Query("DELETE FROM completed_shopping_list WHERE shoppingListId = :completedShoppingListId")
    suspend fun deleteList(completedShoppingListId: String)
}
