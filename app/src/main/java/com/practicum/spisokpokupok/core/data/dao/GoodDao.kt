package com.practicum.spisokpokupok.core.data.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.practicum.spisokpokupok.core.data.entity.LocalGood

@Dao
interface GoodDao {
    @Upsert
    suspend fun upsert(good: LocalGood): String

    @Upsert
    suspend fun upsertAll(goodList: List<LocalGood>)
}
