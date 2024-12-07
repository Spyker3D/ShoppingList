package com.practicum.spisokpokupok.core.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.entity.LocalShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list")
    fun observeAll(): Flow<List<LocalShoppingList>>

    @Upsert(
        entity = LocalShoppingList::class,
    )
    suspend fun upsert(shoppingList: LocalShoppingList)

    @Upsert(
        entity = LocalShoppingList::class,
    )
    suspend fun upsertAll(shoppingLists: List<LocalShoppingList>)

//    @Query("UPDATE shopping_list SET isCompleted = :completed WHERE id = :listId")
//    suspend fun updateCompleted(
//        listId: String,
//        completed: Boolean,
//    )

    @Query("DELETE FROM shopping_list WHERE id = :shoppingListId")
    suspend fun deleteAll(shoppingListId: List<String>)
}
