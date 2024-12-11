package com.practicum.spisokpokupok.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.spisokpokupok.core.data.roomdb.dao.CompletedListDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalActualShoppingList
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalCompletedShoppingList
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalGood
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingList
import com.practicum.spisokpokupok.core.data.roomdb.entity.LocalShoppingTask

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
