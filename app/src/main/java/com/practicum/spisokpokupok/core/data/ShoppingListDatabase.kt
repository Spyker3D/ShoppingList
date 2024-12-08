package com.practicum.spisokpokupok.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.spisokpokupok.core.data.roomDb.dao.CompletedListDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomDb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalActualShoppingList
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalCompletedShoppingList
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingList
import com.practicum.spisokpokupok.core.data.roomDb.entity.LocalShoppingTask

@Database(
    entities = [
        LocalActualShoppingList::class,
        LocalShoppingTask::class,
        LocalShoppingList::class,
        LocalCompletedShoppingList::class,
        LocalGood::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun ShoppingTaskDao(): ShoppingTaskDao

    abstract fun CompletedListDao(): CompletedListDao

    abstract fun ShoppingListDao(): ShoppingListDao

    abstract fun GoodDao(): GoodDao
}
