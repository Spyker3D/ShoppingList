package com.practicum.spisokpokupok.core.data.roomDb.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalActualShoppingListWithName
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_list")
    fun observeAll(): Flow<List<LocalShoppingList>>

    @Query("SELECT * FROM actual_shopping_list")
    fun observeActualLists(): Flow<List<LocalActualShoppingListWithName>>

    @Upsert(
        entity = LocalShoppingList::class,
    )
    suspend fun upsert(shoppingList: LocalShoppingList)

    @Query(
        "INSERT INTO actual_shopping_list (shoppingListId, isFavorite) VALUES (:listId, :isFavorite)",
    )
    suspend fun addActualList(
        listId: String,
        isFavorite: Boolean,
    )

    @Query("UPDATE actual_shopping_list SET isFavorite = :isFavorite WHERE shoppingListId = :listId")
    suspend fun updateActualList(
        listId: String,
        isFavorite: Boolean,
    )

    @Query("UPDATE shopping_list SET name = :name WHERE id = :id")
    suspend fun updateName(
        id: String,
        name: String,
    )

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

    @Query("DELETE FROM shopping_list WHERE id = :shoppingListId")
    suspend fun deleteList(shoppingListId: String)

    @Query("DELETE FROM actual_shopping_list WHERE shoppingListId = :shoppingListId")
    suspend fun deleteActualList(shoppingListId: String)
}
