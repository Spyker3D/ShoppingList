package com.practicum.spisokpokupok.di

import android.content.Context
import androidx.room.Room
import com.practicum.spisokpokupok.core.data.ShoppingListDatabase
import com.practicum.spisokpokupok.core.data.roomdb.dao.CompletedListDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.GoodDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingListDao
import com.practicum.spisokpokupok.core.data.roomdb.dao.ShoppingTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideShoppingTaskDao(database: ShoppingListDatabase): ShoppingTaskDao = database.ShoppingTaskDao()

    @Provides
    fun provideShoppingListDao(database: ShoppingListDatabase): ShoppingListDao = database.ShoppingListDao()

    @Provides
    fun provideCompletedListDao(database: ShoppingListDatabase): CompletedListDao = database.CompletedListDao()

    @Provides
    fun provideGoodDao(database: ShoppingListDatabase): GoodDao = database.GoodDao()

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context,
    ): ShoppingListDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                ShoppingListDatabase::class.java,
                "ShoppingList.db",
            ).build()
}
