package com.practicum.spisokpokupok.db.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practicum.spisokpokupok.db.data.dao.ShoppingTaskDao
import com.practicum.spisokpokupok.db.data.entity.LocalShoppingTask

@Database(entities = [LocalShoppingTask::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {
    abstract fun shoppingTaskDao(): ShoppingTaskDao
}
