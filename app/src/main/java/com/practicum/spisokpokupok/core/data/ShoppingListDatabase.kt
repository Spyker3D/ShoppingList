package com.practicum.spisokpokupok.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.spisokpokupok.core.data.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.core.data.entity.LocalActualShoppingList
import com.practicum.spisokpokupok.core.data.entity.LocalCompletedShoppingList
import com.practicum.spisokpokupok.core.data.entity.LocalGood
import com.practicum.spisokpokupok.core.data.entity.LocalShoppingList
import com.practicum.spisokpokupok.core.data.entity.LocalShoppingTask

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

    abstract fun ShoppingListDao(): ShoppingListDao
}
