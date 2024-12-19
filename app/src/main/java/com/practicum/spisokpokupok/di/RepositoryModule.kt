package com.practicum.spisokpokupok.di

import com.practicum.spisokpokupok.listdetails.data.repository.ShoppingTaskRepositoryImpl
import com.practicum.spisokpokupok.listdetails.domain.repository.ShoppingTaskRepository
import com.practicum.spisokpokupok.lists.data.repository.ShoppingListRepositoryImpl
import com.practicum.spisokpokupok.lists.domain.repository.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindShoppingListRepository(shoppingListRepositoryImpl: ShoppingListRepositoryImpl): ShoppingListRepository

    @Binds
    @Singleton
    abstract fun bindShoppingTaskRepository(shoppingTaskRepositoryImpl: ShoppingTaskRepositoryImpl): ShoppingTaskRepository
}
