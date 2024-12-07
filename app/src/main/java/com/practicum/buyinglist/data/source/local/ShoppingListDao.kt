package com.practicum.buyinglist.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list")
    fun observeAll(): Flow<List<LocalShoppingList>>

    @Upsert
    suspend fun upsert(shoppingList: LocalShoppingList)

    @Upsert
    suspend fun upsertAll(shoppingLists: List<LocalShoppingList>)

    @Query("UPDATE shopping_list SET isCompleted = :completed WHERE id = :listId")
    suspend fun updateCompleted(
        listId: String,
        completed: Boolean,
    )

    @Query("DELETE FROM shopping_list WHERE id = :shoppingListId")
    suspend fun deleteAll(shoppingListId: List<String>)
}
