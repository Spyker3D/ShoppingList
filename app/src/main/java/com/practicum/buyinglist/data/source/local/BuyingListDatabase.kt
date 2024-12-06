package com.practicum.buyinglist.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

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
abstract class BuyingListDatabase : RoomDatabase() {
    abstract fun ShoppingTaskDao(): ShoppingTaskDao
}
