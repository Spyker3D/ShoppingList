package com.practicum.spisokpokupok.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalShoppingTask::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingTaskDao(): ShoppingTaskDao
}
