package com.practicum.spisokpokupok.di

import com.practicum.spisokpokupok.core.data.RoomLocalListDataSource
import com.practicum.spisokpokupok.core.data.RoomLocalTaskDataSource
import com.practicum.spisokpokupok.listdetails.data.repository.LocalTaskDataSource
import com.practicum.spisokpokupok.lists.data.repository.LocalListDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindLocalListDataSource(localDataSource: RoomLocalListDataSource): LocalListDataSource

    @Binds
    @Singleton
    abstract fun bindLocalTaskDataSource(localDataSource: RoomLocalTaskDataSource): LocalTaskDataSource
}
